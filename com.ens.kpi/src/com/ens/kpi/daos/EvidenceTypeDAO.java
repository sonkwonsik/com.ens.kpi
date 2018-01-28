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
	public void create(HashMap<String, String> valueList, HashMap<String, String> whereList) {
		// TODO Auto-generated method stub
		
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
	public void delete(HashMap<String, String> whereList) {
		// TODO Auto-generated method stub
		
	}


}
