package com.ens.kpi.parts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.ens.kpi.connection.DBConnection;
import com.ens.kpi.daos.EvidenceDAO;
import com.ens.kpi.models.EvidenceMasterVO;
import com.ens.kpi.models.SummaryVO;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class EvidencePart {
	@Inject
	ESelectionService selectionService;
	@Inject
	EPartService 	partService;
	private TableViewer tableViewer;
	private Table table;
	private	List<EvidenceMasterVO> evidenceMasterVOList = new ArrayList<EvidenceMasterVO>();
	EvidenceDAO	evidenceDAO	=	new	EvidenceDAO();
	Connection 		con		=	null;
	public EvidencePart() {
		DBConnection dbCon	=	new DBConnection();
		con	= 	dbCon.getConnection();
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
//		String[] COLUMN_HEADER = new String[]{"증빙번호","등록일자","관점","전략과제","목표과제","증빙유형","목표치"};
		parent.setLayout(new GridLayout(1, false));
		tableViewer = new TableViewer(parent, SWT.BORDER|SWT.FULL_SELECTION);
		tableViewer.setUseHashlookup(true);    // table 렌더링 속도 향상
		
		Table table=tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//        table.setLayout(layout);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        
//		  TableLayout layout = new TableLayout();
//        layout.addColumnData(new ColumnWeightData(90, true));
//        layout.addColumnData(new ColumnWeightData(120, true));
//        layout.addColumnData(new ColumnWeightData(120, true));
//        layout.addColumnData(new ColumnWeightData(120, true));
//        layout.addColumnData(new ColumnWeightData(120, true));
//        layout.addColumnData(new ColumnWeightData(120, true));
//        layout.addColumnData(new ColumnWeightData(120, true));
        
        TableColumn colEvidenceSeq	=	new	TableColumn(table, SWT.NONE);
        colEvidenceSeq.setAlignment(SWT.RIGHT);
        colEvidenceSeq.setWidth(100);
        colEvidenceSeq.setText("증빙번호");
        
        TableColumn colRegistration_date	=	new	TableColumn(table, SWT.NONE);
        colRegistration_date.setAlignment(SWT.CENTER);
        colRegistration_date.setWidth(100);
        colRegistration_date.setText("증빙일자");
        
        TableColumn colPerspective	=	new	TableColumn(table, SWT.NONE);
        colPerspective.setAlignment(SWT.LEFT);
        colPerspective.setWidth(100);
        colPerspective.setText("관점");
        
        TableColumn colStrategic_subject	=	new	TableColumn(table, SWT.NONE);
        colStrategic_subject.setAlignment(SWT.LEFT);
        colStrategic_subject.setWidth(100);
        colStrategic_subject.setText("전략과제");
        
        TableColumn colTask	=	new	TableColumn(table, SWT.NONE);
        colTask.setAlignment(SWT.LEFT);
        colTask.setWidth(100);
        colTask.setText("실행과제");
        
        TableColumn colEvidence_type	=	new	TableColumn(table, SWT.NONE);
        colEvidence_type.setAlignment(SWT.LEFT);
        colEvidence_type.setWidth(100);
        colEvidence_type.setText("증빙유형");
        
        TableColumn colEvidence_name	=	new	TableColumn(table, SWT.NONE);
        colEvidence_name.setAlignment(SWT.LEFT);
        colEvidence_name.setWidth(100);
        colEvidence_name.setText("증빙명칭");
        
        TableColumn colScore	=	new	TableColumn(table, SWT.NONE);
        colScore.setAlignment(SWT.RIGHT);
        colScore.setWidth(100);
        colScore.setText("점수");
        
        
        
        /*
		 * Header Column 생성
		 */
//		for(String header : COLUMN_HEADER ) {
//			TableColumn tablecol = new TableColumn(table,SWT.None);
//			tablecol.setText(header);
//		}
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object arg1, Object arg2) {
			}
			
			@Override
			public void dispose() {
			}
	            
			/**
			 * Object배열을 리턴한다.
			 *  - 이 때, 리턴되는 Object배열은 전체 테이블 데이터를 포함하는 배열
			 */
			@Override
			public Object[] getElements(Object input) {
			    List<EvidenceMasterVO> list = (List<EvidenceMasterVO>) input;
				return list.toArray();
			}
		});
		 
	 	/*
         * LabelProvider 지정
         */
        tableViewer.setLabelProvider(new  ITableLabelProvider() {
            @Override
            public void addListener(ILabelProviderListener arg0) {}
            @Override
            public void removeListener(ILabelProviderListener arg0) {}
            @Override
            public void dispose() {}
            @Override
            public boolean isLabelProperty(Object arg0, String arg1) {
                return false;
            }
            
            /*
             * 각 컬럼 idx 별 리턴되는 String 을 리턴한다.
             * (non-Javadoc)
             * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
             */
            @Override
            public String getColumnText(Object element, int idx) {
            	EvidenceMasterVO evidenceMasterVO = (EvidenceMasterVO)element;
                switch (idx) {
                case 0:
                    return evidenceMasterVO.getEVIDENCE_SEQ();
                case 1:
                	return evidenceMasterVO.getEVIDENCE_DATE();
                case 2:
                	return evidenceMasterVO.getPERSPECTIVE_NAME();
                case 3:
                	return evidenceMasterVO.getSTRATEGIC_SUBJECT_NAME();
                case 4:
                	return evidenceMasterVO.getTASK_NAME();
                case 5:
                	return evidenceMasterVO.getEVIDENCE_TYPE_NAME();
                case 6:
                	return evidenceMasterVO.getEVIDENCE_NAME();
                case 7:
                	return evidenceMasterVO.getSCORE();
                	
                }
                return "";
            }
            @Override
            public Image getColumnImage(Object arg0, int idx) {
                return null;
            }
        });
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				EvidenceMasterVO s=(EvidenceMasterVO) selection.getFirstElement();
				
				selectionService.setSelection(selection.size() == 1 ? selection.getFirstElement() : selection.toArray());
				
				
				MPart part=partService.findPart("com.ens.kpi.part.RegistrationPart");
				String label1="";
				label1=s.getEMP_NAME();
				if (!s.getPERSPECTIVE_NAME().equals("")) {
					label1 += "-" + s.getPERSPECTIVE_NAME();
				}
				if (!s.getSTRATEGIC_SUBJECT_NAME().equals("")) {
					label1 += "-" + s.getSTRATEGIC_SUBJECT_NAME();
				}
				if (!s.getTASK_NAME().equals("")) {
					label1 += "-" + s.getTASK_NAME();
				}
				if (!s.getEVIDENCE_SEQ().equals("")) {
					label1 += "-" + s.getEVIDENCE_SEQ();
				}
				part.setLabel(label1);
			}
		});
        
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO	Set the focus to control
	}
	/*
	 * data길이에 맞게 column size 조정
	 */
	private void refresh() {
		tableViewer.setInput(evidenceMasterVOList);
		tableViewer.refresh();
		table = tableViewer.getTable();
		for (int i = 0, n = table.getColumnCount(); i < n; i++) {
			table.getColumn(i).pack();
		}
		table.pack();
	}
	public void setEvidence(SummaryVO s) throws SQLException {
		ResultSet	rs	=	null;
		
		EvidenceMasterVO evidenceMasterVO	=	null;
		evidenceMasterVOList.clear();
		
		evidenceDAO.setConnection(con);
		rs=evidenceDAO.selectEvidence(s.getTEAM_CD(), s.getEMP_ID(), s.getREGISTRATION_DATE(),s.getPERSPECTIVE(), s.getSTRATEGIC_SUBJECT(), s.getTASK());
		
		while(rs.next()) {
			evidenceMasterVO	=	new EvidenceMasterVO();
			evidenceMasterVO.setEVIDENCE_SEQ(rs.getString("evidence_seq"));
			evidenceMasterVO.setEMP_ID(rs.getString("emp_id"));
			evidenceMasterVO.setEMP_NAME(rs.getString("emp_name"));
			evidenceMasterVO.setTEAM_CD(rs.getString("team_cd"));
			evidenceMasterVO.setTEAM_NAME(rs.getString("team_name"));
			evidenceMasterVO.setREGISTRATION_DATE(rs.getString("registration_date"));
			evidenceMasterVO.setPERSPECTIVE(rs.getString("perspective"));
			evidenceMasterVO.setPERSPECTIVE_NAME(rs.getString("perspective_name"));
			evidenceMasterVO.setSTRATEGIC_SUBJECT(rs.getString("strategic_subject"));
			evidenceMasterVO.setSTRATEGIC_SUBJECT_NAME(rs.getString("strategic_subject_name"));
			evidenceMasterVO.setTASK(rs.getString("task"));
			evidenceMasterVO.setTASK_NAME(rs.getString("task_name"));
			evidenceMasterVO.setEVIDENCE_DATE(rs.getString("evidence_date"));
			evidenceMasterVO.setEVIDENCE_TYPE(rs.getString("evidence_type"));
			evidenceMasterVO.setEVIDENCE_TYPE_NAME(rs.getString("evidence_type_name"));
			evidenceMasterVO.setEVIDENCE_NAME(rs.getString("evidence_name"));
			evidenceMasterVO.setIMPLEMENTATION_TIMES(rs.getString("implementation_times"));
			evidenceMasterVO.setSCORE(rs.getString("score"));
			
			evidenceMasterVOList.add(evidenceMasterVO);
		}
	}
	
	@Inject
	public void setSummary(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) SummaryVO summary) {
	    if(summary != null ) {
	    	try {
				setEvidence(summary);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	refresh();
	    }
	}

}
