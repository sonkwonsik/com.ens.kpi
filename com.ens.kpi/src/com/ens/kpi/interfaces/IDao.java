package com.ens.kpi.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface IDao {
	
    void setConnection(Connection con);

    boolean create(HashMap<String, String> valueList, HashMap<String, String> whereList);
    
    boolean insert(HashMap<String, String> valueList);

    boolean update(HashMap<String, String> valueList, HashMap<String, String> whereList);
    
    ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList, HashSet<String> orderbyList);
    
    ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList);
    
    ResultSet select(HashMap<String, String> whereList);

    List<?> getList(HashMap<String, String> whereList);
    
    int delete(HashMap<String, String> whereList);

}