package com.ens.kpi.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CollectionUtils {
	public List getRsToList(ResultSet rs) {
	     List lst =  null;
	  
	  try {
	   int rs_row = rs.getRow();
	   if(rs_row > -1) {
	
	    ResultSetMetaData mrs = rs.getMetaData();
	    int COL_TYPE;
	    String COL_NAME;
	    String inputVar = null;
	    Hashtable rowhash =  new Hashtable();;
	    lst = new ArrayList();
	
	    //컬럼의 수를 저장한다.
	    int colcnt = mrs.getColumnCount();
	
	    
	    // ResultSet의 한 Row 를 읽는다.
	    while(rs.next()) {
	    
	     for(int cnt = 1; cnt <= colcnt; cnt++) {
	      COL_NAME = mrs.getColumnName(cnt).toUpperCase();
	      inputVar = rs.getString(cnt);
	
	      rowhash.put(COL_NAME, inputVar);
	     }
	     
	     lst.add(rowhash);
	    }
	   }
	
	  } catch (SQLException sqle) {
	   sqle.printStackTrace();
	  } catch(Exception e) {
	   e.printStackTrace();
	  } 
	  
	  return lst;
	 }
}

