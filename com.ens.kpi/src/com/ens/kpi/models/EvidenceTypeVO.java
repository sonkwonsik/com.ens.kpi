package com.ens.kpi.models;

public class EvidenceTypeVO extends CommonVO {
	String EVIDENCE_TYPE;       
	String EVIDENCE_TYPE_NAME;  
	String DATA_PATH;
	
	
	public EvidenceTypeVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EvidenceTypeVO(String eVIDENCE_TYPE, String eVIDENCE_TYPE_NAME, String dATA_PATH) {
		super();
		EVIDENCE_TYPE = eVIDENCE_TYPE;
		EVIDENCE_TYPE_NAME = eVIDENCE_TYPE_NAME;
		DATA_PATH = dATA_PATH;
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
	public String getDATA_PATH() {
		return DATA_PATH;
	}
	public void setDATA_PATH(String dATA_PATH) {
		//DATA_PATH = dATA_PATH;
		firePropertyChange("DATA_PATH", this.DATA_PATH, this.DATA_PATH = dATA_PATH);
	}
	
	
}
