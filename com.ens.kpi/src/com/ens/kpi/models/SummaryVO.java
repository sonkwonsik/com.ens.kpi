package com.ens.kpi.models;

import java.util.ArrayList;
import java.util.List;

public class SummaryVO extends CommonVO{
	String	TEAM_CD;
	String	TEAM_NAME;
	String	EMP_ID;
	String	EMP_NAME;
	String	REGISTRATION_DATE;
	String	PERSPECTIVE;
	String 	PERSPECTIVE_NAME;
	String	STRATEGIC_SUBJECT;
	String	STRATEGIC_SUBJECT_NAME;
	String	TASK;
	String	TASK_NAME;
	String	SCORE;
	String	HTEAM_NAME;
	String	HEMP_NAME;
	String 	HPERSPECTIVE_NAME;
	String	HSTRATEGIC_SUBJECT_NAME;
	String	HTASK_NAME;
	public  SummaryVO parent;
	public  List<SummaryVO> children = new ArrayList<>();

	public SummaryVO(String tEAM_CD, String tEAM_NAME, String eMP_ID, String eMP_NAME, String rEGISTRATION_DATE,
			String pERSPECTIVE, String pERSPECTIVE_NAME, String sTRATEGIC_SUBJECT, String sTRATEGIC_SUBJECT_NAME,
			String tASK, String tASK_NAME, String sCORE, String hTEAM_NAME, String hEMP_NAME, String hPERSPECTIVE_NAME,
			String hSTRATEGIC_SUBJECT_NAME, String hTASK_NAME, SummaryVO parent) {
		super();
		TEAM_CD = tEAM_CD;
		TEAM_NAME = tEAM_NAME;
		EMP_ID = eMP_ID;
		EMP_NAME = eMP_NAME;
		REGISTRATION_DATE = rEGISTRATION_DATE;
		PERSPECTIVE = pERSPECTIVE;
		PERSPECTIVE_NAME = pERSPECTIVE_NAME;
		STRATEGIC_SUBJECT = sTRATEGIC_SUBJECT;
		STRATEGIC_SUBJECT_NAME = sTRATEGIC_SUBJECT_NAME;
		TASK = tASK;
		TASK_NAME = tASK_NAME;
		SCORE = sCORE;
		HTEAM_NAME = hTEAM_NAME;
		HEMP_NAME = hEMP_NAME;
		HPERSPECTIVE_NAME = hPERSPECTIVE_NAME;
		HSTRATEGIC_SUBJECT_NAME = hSTRATEGIC_SUBJECT_NAME;
		HTASK_NAME = hTASK_NAME;
		this.parent = parent;
	}
	public SummaryVO getParent() {
		return parent;
	}
	public void setParent(SummaryVO parent) {
		this.parent = parent;
	}
	public Object[] getChildren() {
		return (Object[]) children.toArray(new SummaryVO[children.size()]);
	}
	public Object[] setChildren(List<SummaryVO> children) {
		return (Object[]) children.toArray(new SummaryVO[children.size()]);
	}
	public boolean hasChildren() {
		return children.size()>0;
	}	
	public String getTEAM_CD() {
		return TEAM_CD;
	}
	public void setTEAM_ID_(String tEAM_CD) {
		//TEAM_ID_ = tEAM_ID_;
		firePropertyChange("TEAM_ID_", this.TEAM_CD, this.TEAM_CD = tEAM_CD);
	}
	public String getTEAM_NAME() {
		return TEAM_NAME;
	}
	public void setTEAM_NAME(String tEAM_NAME) {
		//TEAM_NAME = tEAM_NAME;
		firePropertyChange("TEAM_NAME", this.TEAM_NAME, this.TEAM_NAME = tEAM_NAME);
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
	public String getSCORE() {
		return SCORE;
	}
	public void setSCORE(String sCORE) {
		//SCORE = sCORE;
		firePropertyChange("SCORE", this.SCORE, this.SCORE = sCORE);
	}
	public String getHTEAM_NAME() {
		return HTEAM_NAME;
	}
	public void setHTEAM_NAME(String hTEAM_NAME) {
		//HTEAM_NAME = hTEAM_NAME;
		firePropertyChange("HTEAM_NAME", this.HTEAM_NAME, this.HTEAM_NAME = hTEAM_NAME);
	}
	public String getHEMP_NAME() {
		return HEMP_NAME;
	}
	public void setHEMP_NAME(String hEMP_NAME) {
		//HEMP_NAME = hEMP_NAME;
		firePropertyChange("HEMP_NAME", this.HEMP_NAME, this.HEMP_NAME = hEMP_NAME);
	}
	public String getHPERSPECTIVE_NAME() {
		return HPERSPECTIVE_NAME;
	}
	public void setHPERSPECTIVE_NAME(String hPERSPECTIVE_NAME) {
		//HPERSPECTIVE_NAME = hPERSPECTIVE_NAME;
		firePropertyChange("HPERSPECTIVE_NAME", this.HPERSPECTIVE_NAME, this.HPERSPECTIVE_NAME = hPERSPECTIVE_NAME);
	}
	public String getHSTRATEGIC_SUBJECT_NAME() {
		return HSTRATEGIC_SUBJECT_NAME;
	}
	public void setHSTRATEGIC_SUBJECT_NAME(String hSTRATEGIC_SUBJECT_NAME) {
		//HSTRATEGIC_SUBJECT_NAME = hSTRATEGIC_SUBJECT_NAME;
		firePropertyChange("HSTRATEGIC_SUBJECT_NAME", this.HSTRATEGIC_SUBJECT_NAME,
				this.HSTRATEGIC_SUBJECT_NAME = hSTRATEGIC_SUBJECT_NAME);
	}
	public String getHTASK_NAME() {
		return HTASK_NAME;
	}
	public void setHTASK_NAME(String hTASK_NAME) {
		//HTASK_NAME = hTASK_NAME;
		firePropertyChange("HTASK_NAME", this.HTASK_NAME, this.HTASK_NAME = hTASK_NAME);
	}
}
