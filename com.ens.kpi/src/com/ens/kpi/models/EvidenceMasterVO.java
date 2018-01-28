package com.ens.kpi.models;

public class EvidenceMasterVO extends CommonVO {
	String	EVIDENCE_SEQ			=	"";
	String	EMP_ID					=	"";
	String	EMP_NAME				=	"";
	String	TEAM_CD             	=	"";
	String	TEAM_NAME             	=	"";
	String	REGISTRATION_DATE   	=	"";
	String	PERSPECTIVE         	=	"";
	String	PERSPECTIVE_NAME       	=	"";
	String	STRATEGIC_SUBJECT   	=	"";
	String	STRATEGIC_SUBJECT_NAME 	=	"";
	String	TASK                	=	"";
	String	TASK_NAME              	=	"";
	String	EVIDENCE_DATE       	=	"";
	String	EVIDENCE_TYPE       	=	"";
	String	EVIDENCE_TYPE_NAME     	=	"";
	String	EVIDENCE_NAME       	=	"";
	String	IMPLEMENTATION_TIMES	=	"";
	String	SCORE               	=	"";
	
	
	
	public EvidenceMasterVO() {
		super();
	}

	public String getEVIDENCE_SEQ() {
		return EVIDENCE_SEQ;
	}

	public void setEVIDENCE_SEQ(String eVIDENCE_SEQ) {
		//EVIDENCE_SEQ = eVIDENCE_SEQ;
		firePropertyChange("EVIDENCE_SEQ", this.EVIDENCE_SEQ, this.EVIDENCE_SEQ = eVIDENCE_SEQ);
	}

	public String getEMP_ID() {
		return EMP_ID;
	}

	public void setEMP_ID(String eMP_ID) {
		//EMP_ID = eMP_ID;
		firePropertyChange("EMP_ID", this.EMP_ID, this.EMP_ID = eMP_ID);
	}

	public String getEMP_NAME() {
		return EMP_NAME;
	}

	public void setEMP_NAME(String eMP_NAME) {
		//EMP_NAME = eMP_NAME;
		firePropertyChange("EMP_NAME", this.EMP_NAME, this.EMP_NAME = eMP_NAME);
	}

	public String getTEAM_CD() {
		return TEAM_CD;
	}

	public void setTEAM_CD(String tEAM_CD) {
		//TEAM_CD = tEAM_CD;
		firePropertyChange("TEAM_CD", this.TEAM_CD, this.TEAM_CD = tEAM_CD);
	}

	public String getTEAM_NAME() {
		return TEAM_NAME;
	}

	public void setTEAM_NAME(String tEAM_NAME) {
		//TEAM_NAME = tEAM_NAME;
		firePropertyChange("TEAM_NAME", this.TEAM_NAME, this.TEAM_NAME = tEAM_NAME);
	}

	public String getREGISTRATION_DATE() {
		return REGISTRATION_DATE;
	}

	public void setREGISTRATION_DATE(String rEGISTRATION_DATE) {
		//REGISTRATION_DATE = rEGISTRATION_DATE;
		firePropertyChange("REGISTRATION_DATE", this.REGISTRATION_DATE, this.REGISTRATION_DATE = rEGISTRATION_DATE);
	}

	public String getPERSPECTIVE() {
		return PERSPECTIVE;
	}

	public void setPERSPECTIVE(String pERSPECTIVE) {
		//PERSPECTIVE = pERSPECTIVE;
		firePropertyChange("PERSPECTIVE", this.PERSPECTIVE, this.PERSPECTIVE = pERSPECTIVE);
	}

	public String getPERSPECTIVE_NAME() {
		return PERSPECTIVE_NAME;
	}

	public void setPERSPECTIVE_NAME(String pERSPECTIVE_NAME) {
		//PERSPECTIVE_NAME = pERSPECTIVE_NAME;
		firePropertyChange("PERSPECTIVE_NAME", this.PERSPECTIVE_NAME, this.PERSPECTIVE_NAME = pERSPECTIVE_NAME);
	}

	public String getSTRATEGIC_SUBJECT() {
		return STRATEGIC_SUBJECT;
	}

	public void setSTRATEGIC_SUBJECT(String sTRATEGIC_SUBJECT) {
		//STRATEGIC_SUBJECT = sTRATEGIC_SUBJECT;
		firePropertyChange("STRATEGIC_SUBJECT", this.STRATEGIC_SUBJECT, this.STRATEGIC_SUBJECT = sTRATEGIC_SUBJECT);
	}

	public String getSTRATEGIC_SUBJECT_NAME() {
		return STRATEGIC_SUBJECT_NAME;
	}

	public void setSTRATEGIC_SUBJECT_NAME(String sTRATEGIC_SUBJECT_NAME) {
		//STRATEGIC_SUBJECT_NAME = sTRATEGIC_SUBJECT_NAME;
		firePropertyChange("STRATEGIC_SUBJECT_NAME", this.STRATEGIC_SUBJECT_NAME,
				this.STRATEGIC_SUBJECT_NAME = sTRATEGIC_SUBJECT_NAME);
	}

	public String getTASK() {
		return TASK;
	}

	public void setTASK(String tASK) {
		//TASK = tASK;
		firePropertyChange("TASK", this.TASK, this.TASK = tASK);
	}

	public String getTASK_NAME() {
		return TASK_NAME;
	}

	public void setTASK_NAME(String tASK_NAME) {
		//TASK_NAME = tASK_NAME;
		firePropertyChange("TASK_NAME", this.TASK_NAME, this.TASK_NAME = tASK_NAME);
	}

	public String getEVIDENCE_DATE() {
		return EVIDENCE_DATE;
	}

	public void setEVIDENCE_DATE(String eVIDENCE_DATE) {
		//EVIDENCE_DATE = eVIDENCE_DATE;
		firePropertyChange("EVIDENCE_DATE", this.EVIDENCE_DATE, this.EVIDENCE_DATE = eVIDENCE_DATE);
	}

	public String getEVIDENCE_TYPE() {
		return EVIDENCE_TYPE;
	}

	public void setEVIDENCE_TYPE(String eVIDENCE_TYPE) {
		//EVIDENCE_TYPE = eVIDENCE_TYPE;
		firePropertyChange("EVIDENCE_TYPE", this.EVIDENCE_TYPE, this.EVIDENCE_TYPE = eVIDENCE_TYPE);
	}

	public String getEVIDENCE_TYPE_NAME() {
		return EVIDENCE_TYPE_NAME;
	}

	public void setEVIDENCE_TYPE_NAME(String eVIDENCE_TYPE_NAME) {
		//EVIDENCE_TYPE_NAME = eVIDENCE_TYPE_NAME;
		firePropertyChange("EVIDENCE_TYPE_NAME", this.EVIDENCE_TYPE_NAME, this.EVIDENCE_TYPE_NAME = eVIDENCE_TYPE_NAME);
	}

	public String getEVIDENCE_NAME() {
		return EVIDENCE_NAME;
	}

	public void setEVIDENCE_NAME(String eVIDENCE_NAME) {
		//EVIDENCE_NAME = eVIDENCE_NAME;
		firePropertyChange("EVIDENCE_NAME", this.EVIDENCE_NAME, this.EVIDENCE_NAME = eVIDENCE_NAME);
	}

	public String getIMPLEMENTATION_TIMES() {
		return IMPLEMENTATION_TIMES;
	}

	public void setIMPLEMENTATION_TIMES(String iMPLEMENTATION_TIMES) {
		//IMPLEMENTATION_TIMES = iMPLEMENTATION_TIMES;
		firePropertyChange("IMPLEMENTATION_TIMES", this.IMPLEMENTATION_TIMES,
				this.IMPLEMENTATION_TIMES = iMPLEMENTATION_TIMES);
	}

	public String getSCORE() {
		return SCORE;
	}

	public void setSCORE(String sCORE) {
		//SCORE = sCORE;
		firePropertyChange("SCORE", this.SCORE, this.SCORE = sCORE);
	}
}
