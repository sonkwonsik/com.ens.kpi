package com.ens.kpi.daos;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.ens.kpi.interfaces.IDao;

public class EvidenceDAO implements IDao {
	private Connection 	cn	=	null;
	private String		sql	=	"";

	@Override
	public void setConnection(Connection con) {
		cn=con;
	}
	@Override
	public void create(HashMap<String, String> valueList, HashMap<String, String> whereList) {
		
		for (Entry<String, String> entry : valueList.entrySet()) {
			System.out.println(entry.getKey()+" "+valueList.get(entry.getKey()));
        }
		for (Entry<String, String> entry : whereList.entrySet()) {
			System.out.println(entry.getKey()+" "+whereList.get(entry.getKey()));
        }
	}

	@Override
	public ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList, HashSet<String> orderbyList) {
		ResultSet	rs	=	null;
		int	i	=	0;
		sql="select * from EVIDENCE_MST where ";

		for (Entry<String, String> entry : whereList.entrySet()) {
			sql += entry.getKey() +"='" + whereList.get(entry.getKey()) +"'";
			
			i++;
			if (i<whereList.size()) {
				sql	+=" and " ;
			}
        }
		System.out.println(sql);
		
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet selectEvidence(String team_cd, String emp_id, String registration_date, String perspective, String strategic_subject, String task) {
		ResultSet	rs	=	null;

		String sql = "SELECT e.evidence_seq, \n" +
	             "e.emp_id, \n" +
	             "emp.emp_name, \n" +
	             "e.team_cd, \n" +
	             "com.code_name team_name, \n" +
	             "e.registration_date, \n" +
	             "e.perspective, \n" +
	             "com1.code_name as perspective_name, \n" +
	             "e.strategic_subject, \n" +
	             "com2.code_name as strategic_subject_name, \n" +
	             "e.task, \n" +
	             "com3.code_name as task_name, \n" +
	             "e.evidence_date, \n" +
	             "e.evidence_type, \n" +
	             "tp.evidence_type_name, \n" +
	             "e.evidence_name, \n" +
	             "e.implementation_times, \n" +
	             "e.score \n" +
	             "FROM kpi.evidence_mst e \n" +
	             "LEFT OUTER  JOIN kpi.emp_mst AS emp \n" +
	             "ON e.emp_id = emp.emp_id \n" +
	             "LEFT OUTER JOIN common_code com \n" +
	             "ON com.code_id = 'A0000001' \n" +
	             "AND e.team_cd = com.code_value \n" +
	             "INNER JOIN common_code com1 \n" +
	             "ON com1.code_id = 'B0000001' \n" +
	             "AND com1.code_value = e.perspective \n" +
	             "INNER JOIN common_code com2 \n" +
	             "ON com2.code_id = 'B0000002' \n" +
	             "AND com2.code_value = e.strategic_subject \n" +
	             "INNER JOIN common_code com3 \n" +
	             "ON com3.code_id = 'B0000003' \n" +
	             "AND com3.code_value = e.task \n"+
	             "LEFT OUTER JOIN evidence_type tp \n"+
	             "ON e.evidence_type = tp.evidence_type \n"+
	             "WHERE e.EMP_ID ='"+ emp_id +"' \n" + 
	             "AND	e.registration_date='"+ registration_date +"' \n" ;
	             if (!perspective.equals("")) {
	            	 sql +="AND	e.perspective='"+ perspective +"' \n" ;
	             }
	             if (!strategic_subject.equals("")) {
	            	 sql +="AND	e.strategic_subject='"+ strategic_subject +"' \n";
	             }
	             if (!task.equals("")) {
	            	 sql +="AND	e.task='"+ task +"' \n";
	             }
	             sql += "";
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	@Override
	public void delete(HashMap<String, String> keylist) {
		
	}
	@Override
	public ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ResultSet select(HashMap<String, String> whereList) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> getList(HashMap<String, String> whereList) {
		// TODO Auto-generated method stub
		return null;
	}


}
