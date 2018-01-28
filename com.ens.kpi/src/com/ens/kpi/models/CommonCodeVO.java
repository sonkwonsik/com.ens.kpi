package com.ens.kpi.models;

public class CommonCodeVO extends CommonVO {
	String 	code_id	;
	String 	code_value	;
	String	code_name	;

	
	
	public CommonCodeVO() {
		super();
	}

	public CommonCodeVO(String code_id, String code_value, String code_name) {
		super();
		this.code_id = code_id;
		this.code_value = code_value;
		this.code_name = code_name;
	}
	
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		//this.code_id = code_id;
		firePropertyChange("this.code_id", this.code_id, this.code_id = code_id);
	}
	public String getCode_value() {
		return code_value;
	}
	public void setCode_value(String code_value) {
		//this.code_value = code_value;
		firePropertyChange("this.code_value", this.code_value, this.code_value = code_value);}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		//this.code_name = code_name;
		firePropertyChange("this.code_name", this.code_name, this.code_name = code_name);
	}
	
}
