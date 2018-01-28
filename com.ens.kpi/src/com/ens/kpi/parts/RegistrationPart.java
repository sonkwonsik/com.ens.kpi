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
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.ens.kpi.connection.DBConnection;
import com.ens.kpi.daos.CommonCodeDAO;
import com.ens.kpi.daos.EvidenceTypeDAO;
import com.ens.kpi.models.CommonCodeVO;
import com.ens.kpi.models.EvidenceMasterVO;
import com.ens.kpi.models.EvidenceTypeVO;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
public class RegistrationPart {
	@Inject
	private MDirtyable dirty;
	boolean isModified	=	true;
	
	private Text txtEmployee;
	private ComboViewer cboViewerTeam ;
	private Combo cboTeam ;
	private ComboViewer cboViewerPerspective ;
	private Combo cboPerspective;
	private ComboViewer cboViewerStrategicSubject ;
	private Combo cboStrategicSubject ;
	private ComboViewer cboViewerTask;
	private Combo cboTask;
	private ComboViewer cboViewerEvidenceType;
	private Combo cboEvidenceType;
	private Text txtRegistrationDate;
	private Text txtEvidenceDate;
	private Text txtEvidenceName;
	private Text txtImplementationTimes;
	private Text txtScore;
	private TableViewer tblViewerEvidence;
	private Table tblEvidence;
	Connection		con;
	DBConnection	dbCon;
	CommonCodeDAO commonCodeDAO	=	new CommonCodeDAO();
	EvidenceTypeDAO evidenceTypeDAO =	new EvidenceTypeDAO();
	
//	CollectionUtils	collectionUtils	=	new	CollectionUtils();
	public RegistrationPart() {
		DBConnection dbCon	=	new DBConnection();
		con	= 	dbCon.getConnection();
		commonCodeDAO.setConnection(con);
		evidenceTypeDAO.setConnection(con);
	}

	
	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		
		Label lblEmployee = new Label(parent, SWT.NONE);
		lblEmployee.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmployee.setText("Employee");
		
		txtEmployee = new Text(parent, SWT.BORDER);
		txtEmployee.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtEmployee.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				
				dirty.setDirty(true);
			}
		});
		
		HashMap<String, String>	w	=	new	HashMap<>();
		w.put("CODE_ID", "A0000001");
		
		Label lblTeam = new Label(parent, SWT.NONE);
		lblTeam.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTeam.setText("Team");
		
		cboViewerTeam = new ComboViewer(parent, SWT.NONE);
		cboTeam = cboViewerTeam.getCombo();
		cboTeam.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cboTeam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dirty.setDirty(true);
			}
		});
		
		cboViewerTeam.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerTeam.setLabelProvider(new CommonCodeLabelProvider());
		cboViewerTeam.addSelectionChangedListener(new CommonCodeSelectionChangedListener());
		
		cboViewerTeam.setInput(commonCodeDAO.getList(w));
		
		
		Label lblRegistrationDate = new Label(parent, SWT.NONE);
		lblRegistrationDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRegistrationDate.setText("Registration Date");
		
		txtRegistrationDate = new Text(parent, SWT.BORDER);
		txtRegistrationDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtRegistrationDate.setTextLimit(10);
		
		Label lblPerspective = new Label(parent, SWT.NONE);
		lblPerspective.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPerspective.setText("Perspective");
		
		cboViewerPerspective = new ComboViewer(parent, SWT.NONE);
		cboPerspective = cboViewerPerspective.getCombo();
		cboPerspective.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cboPerspective.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dirty.setDirty(true);
			}
		});
		
		cboViewerPerspective.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerPerspective.setLabelProvider(new CommonCodeLabelProvider());
		cboViewerPerspective.addSelectionChangedListener(new CommonCodeSelectionChangedListener());
		
		w	=	new	HashMap<>();
		w.put("CODE_ID", "B0000001");
		
		cboViewerPerspective.setInput(commonCodeDAO.getList(w));
		
		w	=	new	HashMap<>();
		w.put("CODE_ID", "B0000002");
		
		w	=	new	HashMap<>();
		w.put("CODE_ID", "B0000003");
		
		Label lblStrategicSubject = new Label(parent, SWT.NONE);
		lblStrategicSubject.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStrategicSubject.setText("Strategic Subject");
		
		cboViewerStrategicSubject = new ComboViewer(parent, SWT.NONE);
		cboStrategicSubject = cboViewerStrategicSubject.getCombo();
		cboStrategicSubject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cboStrategicSubject.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dirty.setDirty(true);
			}
		});
		
		cboViewerStrategicSubject.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerStrategicSubject.setLabelProvider(new CommonCodeLabelProvider());
		cboViewerStrategicSubject.addSelectionChangedListener(new CommonCodeSelectionChangedListener());
		
		cboViewerStrategicSubject.setInput(commonCodeDAO.getList(w));
		
		Label lblTask = new Label(parent, SWT.NONE);
		lblTask.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTask.setText("Task");
		
		cboViewerTask = new ComboViewer(parent, SWT.NONE);
		cboTask = cboViewerTask.getCombo();
		cboTask.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		cboViewerTask.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerTask.setLabelProvider(new CommonCodeLabelProvider());
		cboViewerTask.addSelectionChangedListener(new CommonCodeSelectionChangedListener());
		
		cboViewerTask.setInput(commonCodeDAO.getList(w));
		
		Label lblEvidenceName = new Label(parent, SWT.NONE);
		lblEvidenceName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEvidenceName.setText("Evidence Name");
		
		txtEvidenceName = new Text(parent, SWT.BORDER);
		txtEvidenceName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		w	=	new	HashMap<>();
		w.put("'1'", "1");
		
		Label lblEvidenceType = new Label(parent, SWT.NONE);
		lblEvidenceType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEvidenceType.setText("Evidence Type");
		
		cboViewerEvidenceType = new ComboViewer(parent, SWT.NONE);
		cboEvidenceType = cboViewerEvidenceType.getCombo();
		cboEvidenceType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		cboViewerEvidenceType.setContentProvider(ArrayContentProvider.getInstance());
		cboViewerEvidenceType.setLabelProvider(new EvidenceTypeLabelProvider());
		cboViewerEvidenceType.addSelectionChangedListener(new EvidenceTypeSelectionChangedListener());
		cboViewerEvidenceType.setInput(evidenceTypeDAO.getList(w));
		
		
		Label lblApprovalDate = new Label(parent, SWT.NONE);
		lblApprovalDate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblApprovalDate.setText("Evidence Date");
		
		txtEvidenceDate = new Text(parent, SWT.BORDER);
		txtEvidenceDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblImplementationTimes = new Label(parent, SWT.NONE);
		lblImplementationTimes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblImplementationTimes.setText("Implement Times");
		
		txtImplementationTimes = new Text(parent, SWT.BORDER);
		txtImplementationTimes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblScore = new Label(parent, SWT.NONE);
		lblScore.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblScore.setText("Score");
		
		txtScore = new Text(parent, SWT.BORDER);
		txtScore.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(parent, SWT.NONE);
		
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT);
		toolBar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.setText("Add");
		
		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_1.setText("Remove");
		
		Label lblEvidence = new Label(parent, SWT.NONE);
		lblEvidence.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		lblEvidence.setText("Evidence");
		
		
		
		tblViewerEvidence = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		tblEvidence = tblViewerEvidence.getTable();
		tblEvidence.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO	Set the focus to control
	}
	@Inject
	public void setEvidenceMaster(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) EvidenceMasterVO evidenceMaster) {
	    if(evidenceMaster != null ) {
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
//	    	refresh();
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
//	private int SelectCombo(Combo cbo, String val) {
//		for (int i=0;i<cbo.getItemCount();i++) {
//			if (cbo.getData(val) 
//				
//			}
//		}
//		
//		return 0;
//		
//	}
	public void setEvidenceDetail(EvidenceMasterVO e) throws SQLException {
		ResultSet	rs	=	null;
		
		EvidenceMasterVO evidenceMasterVO	=	null;
		
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
		            System.out.println(o.getCode_name());
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
		            System.out.println(o.getEVIDENCE_TYPE());
		        }
		}

	}
}
