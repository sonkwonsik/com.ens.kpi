package com.ens.kpi.parts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import com.ens.kpi.connection.DBConnection;
import com.ens.kpi.daos.SummaryDAO;
import com.ens.kpi.models.SummaryVO;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;

public class SummaryPart {
	@Inject
	ESelectionService selectionService;
	@Inject
	EPartService 	partService;
	
	TreeViewer		treeViewer;
	Tree 			tree;
	Connection 		con		=	null;
	Statement		stmt	=	null;
	String			sql		=	"";
	String 			approval_date	="";
	Connection		conn;
	DBConnection	dbCon;
	SummaryDAO	summaryDAO	=	new	SummaryDAO();
	ResultSet		rs 				= 	null;
	ResultSetMetaData rsmd =null;
	
	public SummaryPart() {
		DBConnection dbCon	=	new DBConnection();
		con	= 	dbCon.getConnection();
		summaryDAO.setConnection(con);
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		Text txtApproval_date;
		parent.setLayout(new GridLayout(3, false));
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("KPI승인일 : ");
		
		txtApproval_date = new Text(parent, SWT.BORDER);
		txtApproval_date.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		// create the decoration for the text component
		final ControlDecoration deco = new ControlDecoration(txtApproval_date, SWT.TOP | SWT.LEFT);

		// use an existing image
		Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION)
		.getImage();

		// set description and image
		deco.setDescriptionText("Use CTRL + SPACE to see possible values");
		deco.setImage(image);

		// always show decoration
		deco.setShowOnlyOnFocus(false);

		// hide the decoration if the text component has content
		txtApproval_date.addModifyListener(e -> {
		    Text source = (Text) e.getSource();
		    if (!source.getText().isEmpty()) {
		    	deco.hide();
		    } else {
		    	deco.show();
		    }
		    approval_date=source.getText();
		});

		// help the user with the possible inputs
		// "." and " " activate the content proposals
		char[] autoActivationCharacters = new char[] { '.', ' ' };
		KeyStroke keyStroke;
		//
		try {
			rs=summaryDAO.selectApprovalDate();

			rsmd=rs.getMetaData();

			int rowCnt = rsmd.getColumnCount();

		    keyStroke = KeyStroke.getInstance("Ctrl+Space");
		    String Approval_Date[] = new String[rowCnt];
		    int i=0;
		    while(rs.next()) {
		    	if (i==0) {
		    		approval_date	=	rs.getString("approval_date");
		    		txtApproval_date.setText(approval_date);
		    	}
		    	Approval_Date[i]=rs.getString("approval_date");
		    	 i++;
		     }
		    
		    new ContentProposalAdapter(txtApproval_date, new TextContentAdapter(),
		    new SimpleContentProposalProvider(Approval_Date),
		    keyStroke, autoActivationCharacters);
		    
		} catch (ParseException | SQLException e1) {
		    e1.printStackTrace();
		}
		
		Button btnRefresh = new Button(parent, SWT.NONE);
		btnRefresh.setText("재조회");
		btnRefresh.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				refresh();
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		btnRefresh.addMouseListener(new MouseListener()
		{

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				refresh();
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

		});
		//--------------
		
		
		
		treeViewer = new TreeViewer(parent, SWT.BORDER_SOLID | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		tree = treeViewer.getTree();
		tree.setTouchEnabled(true);
		tree.setLinesVisible(true);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		
		TreeViewerColumn colTeam = new TreeViewerColumn(treeViewer, SWT.NONE);
		colTeam.getColumn().setWidth(120);
		colTeam.getColumn().setText("팀");
		colTeam.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof SummaryVO) {
                    return ((SummaryVO) element).getTEAM_NAME();
                }
                return ""; //$NON-NLS-1$
            }
            @Override
        	public Color getBackground(Object element) {
            	if (element instanceof SummaryVO) {
            		if (!((SummaryVO) element).getEMP_NAME().equals("")) 
            			return new Color(Display.getCurrent(), 240, 240, 240);
            	}
                return null; //$NON-NLS-1$
        	}
        });
		
		TreeViewerColumn colEmp = new TreeViewerColumn(treeViewer, SWT.COLOR_GRAY );
		colEmp.getColumn().setWidth(120);
		colEmp.getColumn().setText("이름");
		colEmp.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof SummaryVO) {
                    return ((SummaryVO) element).getEMP_NAME();
                }
                return ""; //$NON-NLS-1$
            }
            @Override
        	public Color getBackground(Object element) {
            	if (element instanceof SummaryVO) {
            		if (!((SummaryVO) element).getEMP_NAME().equals("")) 
            			return new Color(Display.getCurrent(), 240, 240, 240);
            	}
                return null; //$NON-NLS-1$
        	}
        });
		
		TreeViewerColumn colPerspective = new TreeViewerColumn(treeViewer, SWT.NONE);
		colPerspective.getColumn().setWidth(80);
		colPerspective.getColumn().setText("관점");;
		colPerspective.getColumn().setAlignment(SWT.LEFT);
		colPerspective.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
               if (element instanceof SummaryVO) {
                    return ((SummaryVO) element).getPERSPECTIVE_NAME();
                }
                return ""; //$NON-NLS-1$
            }
            @Override
        	public Color getBackground(Object element) {
            	if (element instanceof SummaryVO) {
            		if (!((SummaryVO) element).getEMP_NAME().equals("")) 
            			return new Color(Display.getCurrent(), 240, 240, 240);
            	}
                return null; //$NON-NLS-1$
        	}
        });
        
        TreeViewerColumn colStrategic_subject = new TreeViewerColumn(treeViewer, SWT.NONE);
        colStrategic_subject.getColumn().setWidth(100);
        colStrategic_subject.getColumn().setText("전략과제");;
        colStrategic_subject.getColumn().setAlignment(SWT.LEFT);
        colStrategic_subject.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
               if (element instanceof SummaryVO) {
                    return ((SummaryVO) element).getSTRATEGIC_SUBJECT_NAME();
                }
                return ""; //$NON-NLS-1$
            }
            @Override
        	public Color getBackground(Object element) {
            	if (element instanceof SummaryVO) {
            		if (!((SummaryVO) element).getEMP_NAME().equals("")) 
            			return new Color(Display.getCurrent(), 240, 240, 240);
            	}
                return null; //$NON-NLS-1$
        	}
        });
        
        TreeViewerColumn colTask = new TreeViewerColumn(treeViewer, SWT.NONE);
        colTask.getColumn().setWidth(250);
        colTask.getColumn().setText("실행과제");;
        colTask.getColumn().setAlignment(SWT.LEFT);
        colTask.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
               if (element instanceof SummaryVO) {
                    return ((SummaryVO) element).getTASK_NAME();
                }
                return ""; //$NON-NLS-1$
            }
            @Override
        	public Color getBackground(Object element) {
            	if (element instanceof SummaryVO) {
            		if (!((SummaryVO) element).getEMP_NAME().equals("")) 
            			return new Color(Display.getCurrent(), 240, 240, 240);
            	}
                return null; //$NON-NLS-1$
        	}
        });
        
        TreeViewerColumn colScore = new TreeViewerColumn(treeViewer, SWT.NONE);
        colScore.getColumn().setWidth(50);
        colScore.getColumn().setText("점수");;
        colScore.getColumn().setAlignment(SWT.RIGHT);
        colScore.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
               if (element instanceof SummaryVO) {
                    return ((SummaryVO) element).getSCORE();
                }
                return ""; //$NON-NLS-1$
            }
            @Override
        	public Color getBackground(Object element) {
            	if (element instanceof SummaryVO) {
            		if (!((SummaryVO) element).getEMP_NAME().equals("")) 
            			return new Color(Display.getCurrent(), 240, 240, 240);
            	}
                return null; //$NON-NLS-1$
        	}
        });
        
		tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		tree.setHeaderVisible(true);
//		tree.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDoubleClick(MouseEvent e) {
//				System.out.println("mouse dobule clicked");
//			}
//		});
		
		treeViewer.setContentProvider(new TreeViewerContentProvider());
		treeViewer.addDoubleClickListener(new  IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				SummaryVO s=(SummaryVO) selection.getFirstElement();
				
				selectionService.setSelection(selection.size() == 1 ? selection.getFirstElement() : selection.toArray());
				
				
				MPart part=partService.findPart("com.ens.kpi.part.EvidencePart");
				String label1="";
				label1=s.getHEMP_NAME();
				if (!s.getHPERSPECTIVE_NAME().equals("")) {
					label1 += "-" + s.getHPERSPECTIVE_NAME();
				}
				if (!s.getHSTRATEGIC_SUBJECT_NAME().equals("")) {
					label1 += "-" + s.getHSTRATEGIC_SUBJECT_NAME();
				}
				if (!s.getHTASK_NAME().equals("")) {
					label1 += "-" + s.getHTASK_NAME();
				}
				part.setLabel(label1);

			}
		});
		
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				//IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				////set the selection to the service => 마우스 클릭만으로 이벤트 발행 => 더블클릭 이벤트로 아래 코드 옮김
				//selectionService.setSelection(selection.size() == 1 ? selection.getFirstElement() : selection.toArray());
			}
		});
		
		refresh();
		
		
		
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO	Set the focus to control
	}
	//Size 조정
	private void refresh() {
		treeViewer.setInput(createTreeModel());
		treeViewer.refresh();
		treeViewer.expandAll();
//		treeViewer.expandToLevel(2);
		tree = treeViewer.getTree();
		for (int i = 0, n = tree.getColumnCount(); i < n; i++) {
			tree.getColumn(i).pack();
		}
		tree.pack();
	}
	
	private SummaryVO createTreeModel() {
		SummaryVO 		team				=	null;
		SummaryVO 		emp					=	null;
		SummaryVO 		perspective			=	null;
		SummaryVO 		strategic_subject	=	null;
		SummaryVO 		task				=	null;
		
		ResultSet		rsTeam 				= 	null;
		ResultSet		rsEmp 				= 	null;
		ResultSet		rsPerspective 		= 	null;
		ResultSet		rsStrategic_subject	= 	null;
		ResultSet		rsTask				=	null;
		
		SummaryVO root = new SummaryVO("0", "", "", "IT사업부", "", "", "", "", "", "","","","","","","","", null);
		try {
			rsTeam=summaryDAO.selectTeam();
			while(rsTeam.next()) {
				team = new SummaryVO(rsTeam.getString("team_cd"), rsTeam.getString("team_name"), "", ""
						, "", "", ""
						, "", ""
						, "", ""
						, ""
						, rsTeam.getString("team_name"), ""
						, "", ""
						, ""
						, root);
				root.children.add(team);
				
				rsEmp=summaryDAO.selectEmp(rsTeam.getString("team_cd"), approval_date);
				while(rsEmp.next()) {
					emp = new SummaryVO(rsEmp.getString("team_cd"), "", rsEmp.getString("emp_id"), rsEmp.getString("emp_name")
							, rsEmp.getString("registration_date") 
							, "", ""
							, "", ""
							, "", ""
							, rsEmp.getString("obtained_score") 
							, rsEmp.getString("team_name"), rsEmp.getString("emp_name")
							, "", ""
							, ""
							, team);
					team.children.add(emp);
					
					rsPerspective=summaryDAO.selectPerspective(rsEmp.getString("team_cd"), approval_date, rsEmp.getString("emp_id"));
					while(rsPerspective.next()) {
						perspective = new SummaryVO(rsPerspective.getString("team_cd"), "", rsPerspective.getString("emp_id"), ""
								, rsPerspective.getString("registration_date") 
								, rsPerspective.getString("perspective"),  rsPerspective.getString("perspective_name")
								, "", ""
								, "", ""
								, rsPerspective.getString("obtained_score") 
								, rsPerspective.getString("team_name"), rsPerspective.getString("emp_name")
								, rsPerspective.getString("perspective_name"), ""
								, ""
								, emp);
						emp.children.add(perspective);
						
						rsStrategic_subject=summaryDAO.selectStrategic_subject(rsPerspective.getString("team_cd"), approval_date, rsPerspective.getString("emp_id"),rsPerspective.getString("perspective") );
						while(rsStrategic_subject.next()) {
							strategic_subject = new SummaryVO(rsStrategic_subject.getString("team_cd"), "", rsStrategic_subject.getString("emp_id"), ""
									, rsStrategic_subject.getString("registration_date") 
									, rsStrategic_subject.getString("perspective"),  ""
									, rsStrategic_subject.getString("strategic_subject"),  rsStrategic_subject.getString("strategic_subject_name")
									, "", ""
									, rsStrategic_subject.getString("obtained_score") 
									, rsStrategic_subject.getString("team_name"), rsStrategic_subject.getString("emp_name")
									, rsStrategic_subject.getString("perspective_name"), rsStrategic_subject.getString("strategic_subject_name")
									, ""
									, perspective);
							perspective.children.add(strategic_subject);
							
							rsTask=summaryDAO.selectTask(rsStrategic_subject.getString("team_cd"), approval_date, rsStrategic_subject.getString("emp_id"), rsStrategic_subject.getString("perspective"), rsStrategic_subject.getString("strategic_subject"));
							while(rsTask.next()) {
								task = new SummaryVO(rsTask.getString("team_cd"), "", rsTask.getString("emp_id"), ""
										, rsTask.getString("registration_date") 
										, rsTask.getString("perspective"),  ""
										, rsTask.getString("strategic_subject"),  ""
										, rsTask.getString("task"),  rsTask.getString("task_name")
										, rsTask.getString("obtained_score") 
										, rsTask.getString("team_name"), rsTask.getString("emp_name")
										, rsTask.getString("perspective_name"), rsTask.getString("strategic_subject_name")
										, rsTask.getString("task_name")
										, strategic_subject);
								strategic_subject.children.add(task);
							}
						}
					}
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return root;
	}
	
	class TreeViewerContentProvider implements IStructuredContentProvider, ITreeContentProvider {

		@Override
		public Object[] getElements(Object parentElement) {
			return getChildren(parentElement);
		}
		
		@Override
		public Object getParent(Object childElement) {
			if (childElement instanceof SummaryVO) 
				return ((SummaryVO)childElement).getParent();
			else 
				return null;
		}
		
		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof SummaryVO) 
				return ((SummaryVO)parentElement).getChildren();
			else
				return new Object[0];
		}
		
		@Override
		public boolean hasChildren(Object parentElement) {
			if (parentElement instanceof SummaryVO)
				return ((SummaryVO)parentElement).hasChildren();
			else
				return false;
		}
	}
}
