package com.ens.kpi.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import com.ens.kpi.interfaces.IDao;
import com.ens.kpi.models.CommonCodeVO;
import com.ens.kpi.models.EvidenceTypeVO;

public class EvidenceTypeDAO implements IDao {

	private Connection 	cn	=	null;
	private String		sql	=	"";
	
	@Override
	public void setConnection(Connection con) {
		cn=con;
	}

	@Override
	public boolean create(HashMap<String, String> valueList, HashMap<String, String> whereList) {
		int	i	=	0;
		long	affectedCnt	=	0;
		String	column_clause	="";
		String	value_clause	="";
		
		sql="insert into EVIDENCE_TYPE ( " ;
		
		for (Entry<String, String> entry : valueList.entrySet()) {
			column_clause	+= entry.getKey();
			value_clause	+= valueList.get(entry.getKey());
			i++;
			if (i==valueList.size()) {
				column_clause	+=") values (" ;
				value_clause	+=") " ;
			} else {
				column_clause	+=", " ;
				value_clause	+=", " ;
				
			}
			
			System.out.println(entry.getKey()+" "+ valueList.get(entry.getKey()));
			System.out.println(sql);
        }
		
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			affectedCnt=pstmt.executeLargeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (affectedCnt==0) {
				return false;
			} else { 
				return true;
			}
		}
		
	}
	
	@Override
	public boolean insert(HashMap<String, String> valueList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(HashMap<String, String> valueList, HashMap<String, String> whereList) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList,
			HashSet<String> orderbyList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet select(HashMap<String, String> whereList) {
		ResultSet	rs	=	null;

//		if(whereList.size()<1) return null;
		
		int	i	=	0;
		sql="select * from EVIDENCE_TYPE ";

		for (Entry<String, String> entry : whereList.entrySet()) {
			if (i==0)
				sql += " WHERE " + entry.getKey() +"='" + whereList.get(entry.getKey()) +"'";
			else
				sql += " AND " +entry.getKey() +"='" + whereList.get(entry.getKey()) +"'";
			
			i++;
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

	@Override
	public List<EvidenceTypeVO> getList(HashMap<String, String> whereList) {
		ResultSet	rs		=	null;
		List<EvidenceTypeVO> list = new ArrayList<EvidenceTypeVO>();
		rs=select(whereList);
		try {
			while (rs.next()) {
				EvidenceTypeVO	evidenceTypeVO	=	new	EvidenceTypeVO(rs.getString("EVIDENCE_TYPE"),rs.getString("EVIDENCE_TYPE_NAME"),rs.getString("DATA_PATH"));
			    list.add(evidenceTypeVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int delete(HashMap<String, String> whereList) {
		return 0;
	}


}
