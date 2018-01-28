package com.ens.kpi.daos;

import java.lang.reflect.GenericArrayType;
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

public class CommonCodeDAO implements IDao {
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
	public ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList,
			HashSet<String> orderbyList) {
		ResultSet	rs	=	null;
		if(whereList.size()<1) return null;
		
		int	i	=	0;
		sql="select * from common_code where ";

		for (Entry<String, String> entry : whereList.entrySet()) {
			sql += entry.getKey() +"='" + whereList.get(entry.getKey()) +"'";
			
			i++;
			if (i<whereList.size()) {
				sql	+=" and " ;
			}
        }
		
		i=0;
		while ( groupbyList.iterator().hasNext()) {
			if (i==0)
				sql += " GROUP BY " + groupbyList.iterator().next().toString();
			else
				sql += "," + groupbyList.iterator().next().toString();
			
			i++;
        }
		
		i=0;
		while ( orderbyList.iterator().hasNext()) {
			if (i==0)
				sql += " ORDER BY " + orderbyList.iterator().next().toString();
			else
				sql += "," + orderbyList.iterator().next().toString();
			
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
	public ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList) {
		ResultSet	rs	=	null;
		
		if(whereList.size()<1) return null;
		
		int	i	=	0;
		
		sql="select * from common_code where ";

		for (Entry<String, String> entry : whereList.entrySet()) {
			sql += entry.getKey() +"='" + whereList.get(entry.getKey()) +"'";
			
			i++;
			if (i<whereList.size()) {
				sql	+=" and " ;
			}
        }

		i=0;
		while ( groupbyList.iterator().hasNext()) {
			if (i==0)
				sql += " GROUP BY " + groupbyList.iterator().next().toString();
			else
				sql += "," + groupbyList.iterator().next().toString();
			
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
	public ResultSet select(HashMap<String, String> whereList) {
		ResultSet	rs	=	null;

		if(whereList.size()<1) return null;
		
		int	i	=	0;
		sql="select * from common_code where ";

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
	
	@Override
	public List<CommonCodeVO> getList(HashMap<String, String> whereList){
		ResultSet	rs		=	null;
		List<CommonCodeVO> commonCodeList = new ArrayList<CommonCodeVO>();
		rs=select(whereList);
		try {
			while (rs.next()) {
				CommonCodeVO	commonCodeVO	=	new	CommonCodeVO(rs.getString("CODE_ID"),rs.getString("CODE_VALUE"),rs.getString("CODE_NAME"));
			    commonCodeList.add(commonCodeVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commonCodeList;
		
	}
	
	@Override
	public void delete(HashMap<String, String> whereList) {
		// TODO Auto-generated method stub

	}

}
