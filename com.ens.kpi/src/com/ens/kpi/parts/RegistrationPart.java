package com.ens.kpi.parts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.ens.kpi.connection.DBConnection;
import com.ens.kpi.daos.AchieveDAO;
import com.ens.kpi.daos.CommonCodeDAO;
import com.ens.kpi.daos.EvidenceDAO;
import com.ens.kpi.daos.EvidenceTypeDAO;
import com.ens.kpi.models.CommonCodeVO;
import com.ens.kpi.models.EvidenceMasterVO;
import com.ens.kpi.models.EvidenceTypeVO;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Button;
public class RegistrationPart {
	@Inject
	private MDirtyable dirty;

	@Inject
    private IEventBroker eventBroker; 
    private static final String MESSAGESTATUSBAR ="MessageStatusBar";
    private static final String USERSTATUSBAR ="UserStatusBar";
    
	boolean isModified	=	true;
	
	private Text 		txtEmployee;
	private ComboViewer cboViewerTeam ;
	private Combo 		cboTeam ;
	private ComboViewer cboViewerPerspective ;
	private Combo 		cboPerspective;
	private ComboViewer cboViewerStrategicSubject ;
	private Combo 		cboStrategicSubject ;
	private ComboViewer cboViewerTask;
	private Combo 		cboTask;
	private ComboViewer cboViewerEvidenceType;
	private Combo 		cboEvidenceType;
	private Text 		txtRegistrationDate;
	private Text 		txtEvidenceDate;
	private Text 		txtEvidenceName;
	private Text 		txtImplementationTimes;
	private Text 		txtScore;
	
	private TableViewer tblViewerEmployee;
	private Table 		tblEmployee;
	
	private TableViewer tblViewerEvidence;
	private Table 		tblEvidence;
	private Connection	con;
	private DBConnection	dbCon;
	private CommonCodeDAO 	commonCodeDAO	=	new CommonCodeDAO();
	private EvidenceTypeDAO evidenceTypeDAO =	new EvidenceTypeDAO();
	private EvidenceMasterVO evidenceMasterVO	=	new	EvidenceMasterVO();
	private EvidenceDAO evidenceDAO	=	new	EvidenceDAO();
	private AchieveDAO achieveDAO	=	new	AchieveDAO();
	
//	private final class dirtyListener implements ModifyListener {
//		public void modifyText(ModifyEvent e) {
//			dirty.setDirty(true);
//		}
//	}
//	private final class changeListener implements IChangeListener {
//		@Override
//		public void handleChange(ChangeEvent event) {
//			dirty.setDirty(true);
//		}
//	}
	
	ModifyListener modifyListener =	new	ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			updateModel();
			dirty.setDirty(true);
		}
		
	};
	private Table table;
//	IChangeListener changeListener = new IChangeListener() {
//		@Override
//		public void handleChange(ChangeEvent event) {
//			
//			dirty.setDirty(true);
////			if (dirty != null) {
////				if (isModified==true)
////					isModified=false;
////				else
////					dirty.setDirty(true);
////			}
//		}
//	};
	
	public RegistrationPart() {
		DBConnection dbCon	=	new DBConnection();
		con	= 	dbCon.getConnection();
		commonCodeDAO.setConnection(con);
		evidenceTypeDAO.setConnection(con);
		evidenceDAO.setConnection(con);
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(9, false));
		
		Label lblEmployee = new Label(parent, SWT.NONE);
		lblEmployee.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmployee.setText("Employee");
		
		txtEmployee = new Text(parent, SWT.BORDER);
		GridData gd_txtEmployee = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_txtEmployee.widthHint = 316;
		txtEmployee.setLayoutData(gd_txtEmployee);
		txtEmployee.addModifyListener(modifyListener);
		
		Label label = new Label(parent, SWT.SEPARATOR | SWT.VERTICAL);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 1, 10));
		
		Label lblEvidence = new Label(parent, SWT.NONE);
		lblEvidence.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 5));
		lblEvidence.setText("Evidence");
		
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		GridData gd_toolBar = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_toolBar.widthHint = 144;
		toolBar.setLayoutData(gd_toolBar);
		
		ToolItem tltmAdd = new ToolItem(toolBar, SWT.NONE);
		tltmAdd.setImage(ResourceManager.getPluginImage("com.ens.kpi", "icons/if_199_CircledPlus_183316.png"));
		tltmAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(e.display.getActiveShell());
				dialog.open();
			}
		});
		tltmAdd.setText("add");
		
		ToolItem tltmRemove = new ToolItem(toolBar, SWT.NONE);
		tltmRemove.setImage(ResourceManager.getPluginImage("com.ens.kpi", "icons/if_200_CircledMinus_183317.png"));
		tltmRemove.setText("remove");
		
		Label lblTeam = new Label(parent, SWT.NONE);
		lblTeam.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTeam.setText("Team");
		
		cboViewerTeam = new ComboViewer(parent, SWT.NONE);
		cboViewerTeam.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerTeam.setLabelProvider(new CommonCodeLabelProvider());
		cboViewerTeam.addSelectionChangedListener(new CommonCodeSelectionChangedListener());
		cboTeam = cboViewerTeam.getCombo();
		GridData gd_cboTeam = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_cboTeam.widthHint = 309;
		cboTeam.setLayoutData(gd_cboTeam);
		cboTeam.addModifyListener(modifyListener);
		////////////////////////////////

		///////////////////////////////////////////////
		tblViewerEvidence = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tblEvidence = tblViewerEvidence.getTable();
		GridData gd_tblEvidence = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 4);
		gd_tblEvidence.heightHint = 65;
		tblEvidence.setLayoutData(gd_tblEvidence);
		tblEvidence.setLinesVisible(true);
		tblEvidence.setHeaderVisible(true);
		
        TableColumn colFileName	=	new	TableColumn(tblEvidence, SWT.NONE);
        colFileName.setAlignment(SWT.LEFT);
        colFileName.setWidth(300);
        colFileName.setText("파일명");
        
        TableColumn colFileSize	=	new	TableColumn(tblEvidence, SWT.NONE);
        colFileSize.setAlignment(SWT.RIGHT);
        colFileSize.setWidth(50);
        colFileSize.setText("크기");
		
//		cboTeam.addModifyListener(new dirtyListener());
		
		Label lblRegistrationDate = new Label(parent, SWT.NONE);
		lblRegistrationDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRegistrationDate.setText("Registration Date");
		
		txtRegistrationDate = new Text(parent, SWT.BORDER);
		GridData gd_txtRegistrationDate = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_txtRegistrationDate.widthHint = 308;
		txtRegistrationDate.setLayoutData(gd_txtRegistrationDate);
		txtRegistrationDate.setTextLimit(10);
		
		Label lblPerspective = new Label(parent, SWT.NONE);
		lblPerspective.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPerspective.setText("Perspective");
		
		cboViewerPerspective = new ComboViewer(parent, SWT.NONE);
		cboViewerPerspective.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerPerspective.setLabelProvider(new CommonCodeLabelProvider());
		cboViewerPerspective.addSelectionChangedListener(new CommonCodeSelectionChangedListener());
		cboPerspective = cboViewerPerspective.getCombo();
		GridData gd_cboPerspective = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_cboPerspective.widthHint = 325;
		cboPerspective.setLayoutData(gd_cboPerspective);
		cboPerspective.addModifyListener(modifyListener);
//		cboPerspective.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				dirty.setDirty(true);
//			}
//		});
		
		
		Label lblStrategicSubject = new Label(parent, SWT.NONE);
		lblStrategicSubject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStrategicSubject.setText("Strategic Subject");
		
		cboViewerStrategicSubject = new ComboViewer(parent, SWT.NONE);
		cboViewerStrategicSubject.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerStrategicSubject.setLabelProvider(new CommonCodeLabelProvider());
		cboViewerStrategicSubject.addSelectionChangedListener(new CommonCodeSelectionChangedListener());
		cboStrategicSubject = cboViewerStrategicSubject.getCombo();
		GridData gd_cboStrategicSubject = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_cboStrategicSubject.widthHint = 307;
		cboStrategicSubject.setLayoutData(gd_cboStrategicSubject);
//		cboStrategicSubject.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				dirty.setDirty(true);
//			}
//		});
		
		Label lblTask = new Label(parent, SWT.NONE);
		lblTask.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTask.setText("Task");
		
		cboViewerTask = new ComboViewer(parent, SWT.NONE);
		cboViewerTask.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerTask.setLabelProvider(new CommonCodeLabelProvider());
		cboViewerTask.addSelectionChangedListener(new CommonCodeSelectionChangedListener());
		cboTask = cboViewerTask.getCombo();
		GridData gd_cboTask = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_cboTask.widthHint = 306;
		cboTask.setLayoutData(gd_cboTask);
		
		Label lblSharedAchieve = new Label(parent, SWT.NONE);
		lblSharedAchieve.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 5));
		lblSharedAchieve.setText("Score Sharing");
		
		ToolBar toolBar_1 = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		toolBar_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		ToolItem tltmAdd_1 = new ToolItem(toolBar_1, SWT.NONE);
		tltmAdd_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		tltmAdd_1.setImage(ResourceManager.getPluginImage("com.ens.kpi", "icons/if_199_CircledPlus_183316.png"));
		tltmAdd_1.setText("add");
		
		ToolItem tltmRemove_1 = new ToolItem(toolBar_1, SWT.NONE);
		tltmRemove_1.setImage(ResourceManager.getPluginImage("com.ens.kpi", "icons/if_200_CircledMinus_183317.png"));
		tltmRemove_1.setText("remove");
		
		Label lblEvidenceName = new Label(parent, SWT.NONE);
		lblEvidenceName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEvidenceName.setText("Evidence Name");
		
		txtEvidenceName = new Text(parent, SWT.BORDER);
		GridData gd_txtEvidenceName = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_txtEvidenceName.widthHint = 291;
		txtEvidenceName.setLayoutData(gd_txtEvidenceName);
		
		
		
		////////////////////////////////
		tblViewerEmployee = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tblEmployee = tblViewerEmployee.getTable();
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 4);
		gd_table.widthHint = 418;
		gd_table.heightHint = 56;
		tblEmployee.setLayoutData(gd_table);
		tblEmployee.setLinesVisible(true);
		tblEmployee.setHeaderVisible(true);
		
		TableColumn colEmployee	=	new	TableColumn(tblEmployee, SWT.NONE);
		colEmployee.setAlignment(SWT.LEFT);
		colEmployee.setWidth(100);
		colEmployee.setText("사원번호");
		
		TableColumn colEmployeeName	=	new	TableColumn(tblEmployee, SWT.NONE);
		colEmployeeName.setAlignment(SWT.LEFT);
		colEmployeeName.setWidth(100);
		colEmployeeName.setText("이름");
		
		TableColumn colSharePercent	=	new	TableColumn(tblEmployee, SWT.NONE);
		colSharePercent.setAlignment(SWT.RIGHT);
		colSharePercent.setWidth(100);
		colSharePercent.setText("할당율(%)");
		
		TableColumn colScore	=	new	TableColumn(tblEmployee, SWT.NONE);
		colScore.setAlignment(SWT.RIGHT);
		colScore.setWidth(100);
		colScore.setText("획득점수");
		
		Label lblEvidenceType = new Label(parent, SWT.NONE);
		lblEvidenceType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEvidenceType.setText("Evidence Type");
		
		cboViewerEvidenceType = new ComboViewer(parent, SWT.NONE);
		cboViewerEvidenceType.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerEvidenceType.setLabelProvider(new EvidenceTypeLabelProvider());
		cboViewerEvidenceType.addSelectionChangedListener(new EvidenceTypeSelectionChangedListener());
		cboEvidenceType = cboViewerEvidenceType.getCombo();
		GridData gd_cboEvidenceType = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_cboEvidenceType.widthHint = 325;
		cboEvidenceType.setLayoutData(gd_cboEvidenceType);
		
		Label lblApprovalDate = new Label(parent, SWT.NONE);
		lblApprovalDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblApprovalDate.setText("Evidence Date");
		
		txtEvidenceDate = new Text(parent, SWT.BORDER);
		GridData gd_txtEvidenceDate = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_txtEvidenceDate.widthHint = 303;
		txtEvidenceDate.setLayoutData(gd_txtEvidenceDate);
		
		Label lblImplementationTimes = new Label(parent, SWT.NONE);
		lblImplementationTimes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblImplementationTimes.setText("Implement Times");
		
		txtImplementationTimes = new Text(parent, SWT.BORDER | SWT.RIGHT);
		txtImplementationTimes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		
		Label lblScore = new Label(parent, SWT.NONE);
		lblScore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblScore.setText("Total Score");
		
		txtScore = new Text(parent, SWT.BORDER | SWT.RIGHT);
		txtScore.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(parent, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Remain(%)");
		
		Spinner spinner = new Spinner(parent, SWT.BORDER);
		spinner.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		spinner.setMinimum(5);
		spinner.setSelection(100);
		spinner.setIncrement(5);
		
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		initControls();
	}
	
	private void initControls() {
		// Check if Ui is available
		HashMap<String, String>	w	=	new	HashMap<>();
		w.put("CODE_ID", "A0000001");
		cboViewerTeam.setInput(commonCodeDAO.getList(w));
		
		w.clear();
		w.put("CODE_ID", "B0000001");
		cboViewerPerspective.setInput(commonCodeDAO.getList(w));
		
		w.clear();
		w.put("CODE_ID", "B0000002");
		cboViewerStrategicSubject.setInput(commonCodeDAO.getList(w));
		
		w.clear();
		w.put("CODE_ID", "B0000003");
		cboViewerTask.setInput(commonCodeDAO.getList(w));
		
		w.clear();
		w.put("'1'", "1");
		cboViewerEvidenceType.setInput(evidenceTypeDAO.getList(w));
		
		dirty.setDirty(false);
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO	Set the focus to control
	}
	
	@Persist
	public void save(MPart part) {
//		public void save(MPart part, ITodoService todoService) {
	    // save changes via ITodoService for example
//	    todoService.saveTodo(todo);
	    // save was successful
//		m_bindingContext.updateModels();
		System.out.println("===========");
		System.out.println(evidenceMasterVO.getEMP_ID());
		System.out.println(evidenceMasterVO.getTEAM_CD());
		System.out.println(evidenceMasterVO.getTEAM_NAME());
//		System.out.println(evidenceMasterVO.getEVIDENCE_DATE());
//		System.out.println(evidenceMasterVO.getEVIDENCE_TYPE());
//		System.out.println(evidenceMasterVO.getEVIDENCE_TYPE_NAME());
		System.out.println(evidenceMasterVO.getPERSPECTIVE());
		System.out.println(evidenceMasterVO.getPERSPECTIVE_NAME());
//		System.out.println(evidenceMasterVO.getSTRATEGIC_SUBJECT());
//		System.out.println(evidenceMasterVO.getSTRATEGIC_SUBJECT_NAME());
//		System.out.println(evidenceMasterVO.getTASK());
//		System.out.println(evidenceMasterVO.getTASK_NAME());
//		System.out.println(evidenceMasterVO.getIMPLEMENTATION_TIMES());
		
		HashMap<String, String> v = new HashMap(); 
		v.put("evidence_seq", evidenceMasterVO.getEVIDENCE_SEQ());
		v.put("employee", evidenceMasterVO.getEMP_ID());
		v.put("perspective", evidenceMasterVO.getPERSPECTIVE());
		boolean flag=evidenceDAO.create(v,null);	//증빙마스타 생성
		flag=achieveDAO.create(v, null);			//성취마스타 생성

		if (flag) {
		    part.setDirty(false);
		    
		    HashMap<String, String> msg =	new	HashMap<>();;
		    msg.put("message", "Save Completed");
		    msg.put("user", "손권식");
		    
		    eventBroker.send(MESSAGESTATUSBAR, msg);
		}	    
//	    eventBroker.send(MESSAGESTATUSBAR, "Save Completed");
//	    eventBroker.send(USERSTATUSBAR, "손권식");
	    
	}
	
	private void updateModel() { 
		evidenceMasterVO.setEMP_ID(txtEmployee.getText());
		StructuredSelection sel = (StructuredSelection) cboViewerTeam.getSelection();
		CommonCodeVO c = (CommonCodeVO) sel.getFirstElement();
		if (c!=null) { 
			evidenceMasterVO.setTEAM_CD(c.getCode_value());
			evidenceMasterVO.setTEAM_NAME(c.getCode_name());
		}
		
		sel = (StructuredSelection) cboViewerPerspective.getSelection();
		c = (CommonCodeVO) sel.getFirstElement();
		if (c!=null) {
			evidenceMasterVO.setPERSPECTIVE(c.getCode_value());
			evidenceMasterVO.setPERSPECTIVE_NAME(c.getCode_name());
		}
	}
	public void clearModel(@Optional String clearLevel){
		evidenceMasterVO =	new	EvidenceMasterVO();
		txtEmployee.setText("");
		cboTeam.setText("");
		
		if (clearLevel.equals("after_delete")){
			
			HashMap<String, String> msg =	new	HashMap<>();;
			msg.put("message", "Ready to add");
			msg.put("user", "손권식");
			
			eventBroker.send(MESSAGESTATUSBAR, msg);
		}
	    System.out.println("cleared");
	}
	
	public void deleteModel(@Optional String dummy){
		HashMap<String, String> w = new HashMap(); 
		w.put("evidence_seq", evidenceMasterVO.getEVIDENCE_SEQ());
		int i=evidenceDAO.delete(w);
		System.out.println("deleted");

		if (i>0) {
			HashMap<String, String> msg =	new	HashMap<>();;
		    msg.put("message", "Delete Completed");
		    msg.put("user", "손권식");
		    
		    eventBroker.send(MESSAGESTATUSBAR, msg);
		    
		    clearModel("after_delete");
		}
		
	}
	
	@Inject
	public void setEvidenceMaster(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) EvidenceMasterVO evidenceMaster) {
	    if(evidenceMaster != null ) {
	    	evidenceMasterVO=evidenceMaster;
	    	try {
	    		txtEmployee.setText(evidenceMaster.getEMP_ID());
//	    		lblEmployeeName.setText(evidenceMaster.getEMP_NAME());
	    		txtRegistrationDate.setText(evidenceMaster.getREGISTRATION_DATE());
	    		
	    		CommonCodeVO team  = selectCommonCode(cboViewerTeam.getInput(), evidenceMaster.getTEAM_CD());
	    		cboViewerTeam.setSelection(new StructuredSelection(team));
	    		
	    		CommonCodeVO perspective  = selectCommonCode(cboViewerPerspective.getInput(), evidenceMaster.getPERSPECTIVE());
	    		cboViewerPerspective.setSelection(new StructuredSelection(perspective));
	    		
	    		CommonCodeVO StrategicSubject  = selectCommonCode(cboViewerStrategicSubject.getInput(), evidenceMaster.getSTRATEGIC_SUBJECT());
	    		cboViewerStrategicSubject.setSelection(new StructuredSelection(StrategicSubject));
	    		
	    		CommonCodeVO Task  = selectCommonCode(cboViewerTask.getInput(), evidenceMaster.getTASK());
	    		cboViewerTask.setSelection(new StructuredSelection(Task));
	    		
	    		txtEvidenceName.setText(evidenceMaster.getEVIDENCE_NAME());

	    		EvidenceTypeVO evidenceTypeVO  = selectEvidenceType(cboViewerEvidenceType.getInput(), evidenceMaster.getEVIDENCE_TYPE());
	    		cboViewerEvidenceType.setSelection(new StructuredSelection(evidenceTypeVO));

	    		
	    		txtEvidenceDate.setText(evidenceMaster.getEVIDENCE_DATE());
	    		txtImplementationTimes.setText(evidenceMaster.getIMPLEMENTATION_TIMES());
	    		
	    		txtScore.setText(evidenceMaster.getSCORE());
	    		
	    		setEvidenceDetail(evidenceMaster);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	dirty.setDirty(false);
	    }
	}
	
	private CommonCodeVO selectCommonCode(Object obj, String selectedData){
		List<CommonCodeVO> commonCodeList = (List<CommonCodeVO>) obj ;
		CommonCodeVO c	=	new CommonCodeVO();
		for (int i=0; i<commonCodeList.size();i++) {
			if (commonCodeList.get(i).getCode_value().equals(selectedData)){
				c= 	commonCodeList.get(i);
				break;
			}
		}
		return c;
	}
	
	private EvidenceTypeVO selectEvidenceType(Object obj, String selectedData){
		List<EvidenceTypeVO> list = (List<EvidenceTypeVO>) obj ;
		EvidenceTypeVO c	=	new EvidenceTypeVO();
		for (int i=0; i<list.size();i++) {
			if (list.get(i).getEVIDENCE_TYPE().equals(selectedData)){
				c= 	list.get(i);
				break;
			}
		}
		return c;
	}

	public void setEvidenceDetail(EvidenceMasterVO e) throws SQLException {
//		ResultSet	rs	=	null;
		
//		EvidenceMasterVO evidenceMasterVO	=	null;
		
//		evidenceDetailDAO.setConnection(con);
//		rs=evidenceDetailDAO.selectEvidenceDetail(e.getEVIDENCE_SEQ());
//		
//		while(rs.next()) {
//			evidenceMasterVO	=	new EvidenceMasterVO();
//			evidenceMasterVO.setEVIDENCE_SEQ(rs.getString("evidence_seq"));
//			evidenceMasterVO.setEMP_ID(rs.getString("emp_id"));
//			evidenceMasterVO.setEMP_NAME(rs.getString("emp_name"));
//			evidenceMasterVO.setTEAM_CD(rs.getString("team_cd"));
//			evidenceMasterVO.setTEAM_NAME(rs.getString("team_name"));
//			evidenceMasterVO.setPERSPECTIVE(rs.getString("perspective"));
//			evidenceMasterVO.setPERSPECTIVE_NAME(rs.getString("perspective_name"));
//			evidenceMasterVO.setSTRATEGIC_SUBJECT(rs.getString("strategic_subject"));
//			evidenceMasterVO.setSTRATEGIC_SUBJECT_NAME(rs.getString("strategic_subject_name"));
//			evidenceMasterVO.setTASK(rs.getString("task"));
//			evidenceMasterVO.setTASK_NAME(rs.getString("task_name"));
//			evidenceMasterVO.setEVIDENCE_DATE(rs.getString("evidence_date"));
//			evidenceMasterVO.setEVIDENCE_TYPE(rs.getString("evidence_type"));
//			evidenceMasterVO.setEVIDENCE_TYPE_NAME(rs.getString("evidence_type_name"));
//			evidenceMasterVO.setEVIDENCE_NAME(rs.getString("evidence_name"));
//			evidenceMasterVO.setIMPLEMENTATION_TIMES(rs.getString("implementation_times"));
//			evidenceMasterVO.setSCORE(rs.getString("score"));
//		}
	}
	
//	protected DataBindingContext initDataBindings() {
//		DataBindingContext bindingContext = new DataBindingContext();
////		IObservableValue observeTextTxtEmployeeObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtEmployee);
////		IObservableValue eMP_IDEvidenceMasterVOObserveValue = BeanProperties.value("EMP_ID").observe(evidenceMasterVO);
//		IObservableValue<Text> observeTextTxtEmployeeObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtEmployee);
//		IObservableValue<EvidenceMasterVO> eMP_IDEvidenceMasterVOObserveValue = PojoProperties.value("EMP_ID").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextTxtEmployeeObserveWidget, eMP_IDEvidenceMasterVOObserveValue);
//        observeTextTxtEmployeeObserveWidget.addChangeListener(new changeListener());
//		
//		IObservableValue observeTextCboTeamObserveWidget = WidgetProperties.text().observe(cboTeam);
//		IObservableValue tEAM_CDEvidenceMasterVOObserveValue = BeanProperties.value("TEAM_CD").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextCboTeamObserveWidget, tEAM_CDEvidenceMasterVOObserveValue, null, null);
//		observeTextCboTeamObserveWidget.addChangeListener(new changeListener());
//		//
//		IObservableValue observeTextTxtRegistrationDateObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtRegistrationDate);
//		IObservableValue rEGISTRATION_DATEEvidenceMasterVOObserveValue = BeanProperties.value("REGISTRATION_DATE").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextTxtRegistrationDateObserveWidget, rEGISTRATION_DATEEvidenceMasterVOObserveValue, null, null);
//		//
//		IObservableValue observeTextCboStrategicSubjectObserveWidget = WidgetProperties.text().observe(cboStrategicSubject);
//		IObservableValue sTRATEGIC_SUBJECTEvidenceMasterVOObserveValue = BeanProperties.value("STRATEGIC_SUBJECT").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextCboStrategicSubjectObserveWidget, sTRATEGIC_SUBJECTEvidenceMasterVOObserveValue, null, null);
//		//
//		IObservableValue observeTextCboTaskObserveWidget = WidgetProperties.text().observe(cboTask);
//		IObservableValue tASKEvidenceMasterVOObserveValue = BeanProperties.value("TASK").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextCboTaskObserveWidget, tASKEvidenceMasterVOObserveValue, null, null);
//		//
//		IObservableValue observeTextTxtEvidenceNameObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtEvidenceName);
//		IObservableValue eVIDENCE_NAMEEvidenceMasterVOObserveValue = BeanProperties.value("EVIDENCE_NAME").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextTxtEvidenceNameObserveWidget, eVIDENCE_NAMEEvidenceMasterVOObserveValue, null, null);
//		//
//		IObservableValue observeTextCboEvidenceTypeObserveWidget = WidgetProperties.text().observe(cboEvidenceType);
//		IObservableValue eVIDENCE_TYPEEvidenceMasterVOObserveValue = BeanProperties.value("EVIDENCE_TYPE").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextCboEvidenceTypeObserveWidget, eVIDENCE_TYPEEvidenceMasterVOObserveValue, null, null);
//		//
//		IObservableValue observeTextTxtEvidenceDateObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtEvidenceDate);
//		IObservableValue eVIDENCE_DATEEvidenceMasterVOObserveValue = BeanProperties.value("EVIDENCE_DATE").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextTxtEvidenceDateObserveWidget, eVIDENCE_DATEEvidenceMasterVOObserveValue, null, null);
//		//
//		IObservableValue observeTextTxtImplementationTimesObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtImplementationTimes);
//		IObservableValue iMPLEMENTATION_TIMESEvidenceMasterVOObserveValue = BeanProperties.value("IMPLEMENTATION_TIMES").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextTxtImplementationTimesObserveWidget, iMPLEMENTATION_TIMESEvidenceMasterVOObserveValue, null, null);
//		//
//		IObservableValue observeTextTxtScoreObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtScore);
//		IObservableValue sCOREEvidenceMasterVOObserveValue = BeanProperties.value("SCORE").observe(evidenceMasterVO);
//		bindingContext.bindValue(observeTextTxtScoreObserveWidget, sCOREEvidenceMasterVOObserveValue, null, null);
//		//
//		return bindingContext;
//	}

	
	class CommonCodeLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof CommonCodeVO) {
				CommonCodeVO o = (CommonCodeVO) element;
				return o.getCode_name();
			}
			return super.getText(element);
		}
	}
	
	class CommonCodeSelectionChangedListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			IStructuredSelection selection = (IStructuredSelection) event
		            .getSelection();
		        if (selection.size() > 0){
		        	CommonCodeVO o =(CommonCodeVO) selection.getFirstElement();
//		            System.out.println(o.getCode_name());
		        }
		}

	}
	
	class EvidenceTypeLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof EvidenceTypeVO) {
				EvidenceTypeVO o = (EvidenceTypeVO) element;
				return o.getEVIDENCE_TYPE_NAME();
			}
			return super.getText(element);
		}
	}
	
	class EvidenceTypeSelectionChangedListener implements ISelectionChangedListener {
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			IStructuredSelection selection = (IStructuredSelection) event
		            .getSelection();
		        if (selection.size() > 0){
		        	EvidenceTypeVO o =(EvidenceTypeVO) selection.getFirstElement();
//		            System.out.println(o.getEVIDENCE_TYPE());
		        }
		}

	}
}
