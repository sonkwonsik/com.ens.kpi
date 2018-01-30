package com.ens.kpi.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import com.ens.kpi.interfaces.IDao;

public class SummaryDAO implements IDao  {
	private Connection 	cn	=	null;
	private	String		sql	=	"";
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
		
		sql="INSERT INTO target_kpi ( " ;
		
		for (Entry<String, String> entry : valueList.entrySet()) {
			column_clause	+= entry.getKey();
			value_clause	+= valueList.get(entry.getKey());
			i++;
			if (i==valueList.size()) {
				column_clause	+=") VALUES (" ;
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
	public ResultSet select(HashMap<String, String> whereList, HashSet<String> groupbyList, HashSet<String> orderbyList) {
		ResultSet	rs	=	null;
		int	i	=	0;
		
		sql="select * from target_kpi ";

		for (Entry<String, String> entry : whereList.entrySet()) {
			if (i==0) {
				sql	+=" where " ;
			}
			
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

	public ResultSet selectSummary(String sql) {
		ResultSet	rs	=	null;

		System.out.println(sql);
		
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet selectApprovalDate() {
		ResultSet	rs	=	null;

		sql="select distinct approval_date from target_kpi order by approval_date";
		
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet selectTeam() {
		ResultSet	rs	=	null;

		sql="select code_value as team_cd, code_name as team_name from common_code where code_id='A0000001'";
		
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet selectEmp(String team_cd, String approval_date) {
		ResultSet	rs	=	null;

		sql = "select         \n" +
	             "       team_cd,  \n" +
	             "       team_name, \n" +
	             "       emp_id,  \n" +
	             "       emp_name,  \n" +
	             "       registration_date,  \n" +	//승인 kpi 만을 대상으로 하기때문에 최종 한건만 나타남
//	             "       perspective,  \n" +
//	             "       perspective_name,  \n" +
//	             "       strategic_subject,  \n" +
//	             "       strategic_subject_name,     \n" +
//	             "       task,  \n" +
//	             "       task_name,       \n" +
//	             "       score, \n" +
//	             "       weighted_value, \n" +
//	             "       lower_limit_value, \n" +
//	             "       target_value, \n" +
//	             "       upper_limit_value, \n" +
	             "       sum(cast( \n" +
	             "        case when (score)>upper_limit_value then score * 0.13 \n" +
	             "        else  \n" +
	             "          case when (score)>=lower_limit_value then  (cast (score as real) / cast(target_value as real)) * weighted_value \n" +
	             "          else 0 end \n" +
	             "        end as numeric(18,2)\n" +
	             "        )) obtained_score  \n" +
	             "from  \n" +
	             "( \n" +
	             "  SELECT  \n" +
	             "       e.emp_id,  \n" +
	             "       emp.emp_name,  \n" +
	             "       e.team_cd,  \n" +
	             "       com.code_name AS team_name,  \n" +
	             "       e.registration_date,  \n" +		//승인 kpi 만을 대상으로 하기때문에 최종 한건만 나타남
//	             "       e.perspective,  \n" +
//	             "       com1.code_name AS perspective_name,  \n" +
//	             "       e.strategic_subject,  \n" +
//	             "       com2.code_name AS strategic_subject_name,       \n" +
//	             "       e.task,  \n" +
//	             "       com3.code_name AS task_name,       \n" +
	             "       t.weighted_value, \n" +
	             "       t.lower_limit_value, \n" +
	             "       t.target_value, \n" +
	             "       t.upper_limit_value, \n" +
	             "       sum(e.score) score \n" +
	             "  FROM evidence_mst e  \n" +
	             "  INNER JOIN target_kpi t  \n" +
	             "          ON e.emp_id = t.emp_id  \n" +
	             "         AND e.team_cd = t.team_cd  \n" +
	             "         AND e.registration_date = t.registration_date  \n" +
	             "         AND e.perspective = t.perspective  \n" +
	             "         AND e.strategic_subject = t.strategic_subject  \n" +
	             "         AND e.task = t.task  \n" +
//	             "  INNER JOIN approval_kpi a  \n" +		//별,사용자별 1건만 존재 => where 조건으로 이동
//	             "          ON e.emp_id = a.emp_id  \n" +
//	             "         AND e.team_cd = a.team_cd  \n" +
//	             "         AND e.registration_date = a.registration_date  \n" +
	             "  LEFT OUTER JOIN emp_mst emp ON e.emp_id = emp.emp_id  \n" +
	             "  LEFT OUTER JOIN common_code com  \n" +
	             "               ON com.code_id = 'A0000001'  \n" +
	             "              AND e.team_cd = com.code_value  \n" +
//	             "  INNER JOIN common_code com1  \n" +
//	             "          ON com1.code_id = 'B0000001'  \n" +
//	             "         AND com1.code_value = e.PERSPECTIVE  \n" +
//	             "  INNER JOIN common_code com2 \n" +
//	             "          ON com2.code_id = 'B0000002'  \n" +
//	             "         AND com2.code_value = e.STRATEGIC_SUBJECT  \n" +
//	             "  INNER JOIN common_code com3 \n" +
//	             "          ON com3.code_id = 'B0000003'  \n" +
//	             "         AND com3.code_value = e.task  \n" +
				 "  WHERE t.team_cd='"+ team_cd +"' \n" +
				 "  AND   t.approval_date='"+ approval_date +"' \n" +
	             "GROUP BY e.emp_id,  \n" +
	             "         emp.emp_name,  \n" +
	             "         e.team_cd,  \n" +
	             "         com.code_name,  \n" +
	             "         e.registration_date,  \n" +
//	             "         e.perspective,  \n" +
//	             "         com1.code_name, \n" +
//	             "         e.strategic_subject,  \n" +
//	             "         com2.code_name, \n" +
//	             "         e.task,  \n" +
//	             "         com3.code_name, \n" +
	             "         t.weighted_value, \n" +
	             "         t.lower_limit_value, \n" +
	             "         t.target_value, \n" +
	             "         t.upper_limit_value \n" +
	             ") aa \n" +
	             "group by        " +
	             "	team_cd,  \n" + 
	             "  team_name, \n" + 
	             "  emp_id,  \n" + 
	             "  emp_name,  \n" +
	             "  registration_date  \n" +
	             "order by team_cd , emp_id "
//			             + ", perspective, strategic_subject, task" 
	             + "";			
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet selectPerspective(String team_cd, String approval_date, String emp_id) {
		ResultSet	rs	=	null;

		sql = "select         \n" +
	             "       team_cd,  \n" +
	             "       team_name, \n" +
	             "       emp_id,  \n" +
	             "       emp_name,  \n" +
	             "       registration_date,  \n" +
	             "       perspective,  \n" +
	             "       perspective_name,  \n" +
//	             "       strategic_subject,  \n" +
//	             "       strategic_subject_name,     \n" +
//	             "       task,  \n" +
//	             "       task_name,       \n" +
//	             "       score, \n" +
//	             "       weighted_value, \n" +
//	             "       lower_limit_value, \n" +
//	             "       target_value, \n" +
//	             "       upper_limit_value, \n" +
	             "       sum(cast( \n" +
	             "        case when (score)>upper_limit_value then score * 0.13 \n" +
	             "        else  \n" +
	             "          case when (score)>=lower_limit_value then  (cast (score as real) / cast(target_value as real)) * weighted_value \n" +
	             "          else 0 end \n" +
	             "        end as numeric(18,2)\n" +
	             "       )) obtained_score  \n" +
	             "from  \n" +
	             "( \n" +
	             "  SELECT  \n" +
	             "       e.emp_id,  \n" +
	             "       emp.emp_name,  \n" +
	             "       e.team_cd,  \n" +
	             "       com.code_name AS team_name,  \n" +
	             "       e.registration_date,  \n" +
	             "       e.perspective,  \n" +
	             "       com1.code_name AS perspective_name,  \n" +
//	             "       e.strategic_subject,  \n" +
//	             "       com2.code_name AS strategic_subject_name,       \n" +
//	             "       e.task,  \n" +
//	             "       com3.code_name AS task_name,       \n" +
	             "       t.weighted_value, \n" +
	             "       t.lower_limit_value, \n" +
	             "       t.target_value, \n" +
	             "       t.upper_limit_value, \n" +
	             "       sum(e.score) score \n" +
	             "  FROM evidence_mst e  \n" +
	             "  INNER JOIN target_kpi t  \n" +
	             "          ON e.emp_id = t.emp_id  \n" +
	             "         AND e.team_cd = t.team_cd  \n" +
	             "         AND e.registration_date = t.registration_date  \n" +
	             "         AND e.perspective = t.perspective  \n" +
	             "         AND e.strategic_subject = t.strategic_subject  \n" +
	             "         AND e.task = t.task  \n" +
//	             "  INNER JOIN approval_kpi a  \n" +
//	             "          ON e.emp_id = a.emp_id  \n" +
//	             "         AND e.team_cd = a.team_cd  \n" +
//	             "         AND e.registration_date = a.registration_date  \n" +
	             "  LEFT OUTER JOIN emp_mst emp ON e.emp_id = emp.emp_id  \n" +
	             "  LEFT OUTER JOIN common_code com  \n" +
	             "               ON com.code_id = 'A0000001'  \n" +
	             "              AND e.team_cd = com.code_value  \n" +
	             "  INNER JOIN common_code com1  \n" +
	             "          ON com1.code_id = 'B0000001'  \n" +
	             "         AND com1.code_value = e.perspective  \n" +
//	             "  INNER JOIN common_code com2 \n" +
//	             "          ON com2.code_id = 'B0000002'  \n" +
//	             "         AND com2.code_value = e.strategic_subject  \n" +
//	             "  INNER JOIN common_code com3 \n" +
//	             "          ON com3.code_id = 'B0000003'  \n" +
//	             "         AND com3.code_value = e.task  \n" +
				 "  WHERE t.team_cd='"+ team_cd +"' \n" +
				 "  AND   t.emp_id='"+ emp_id +"' \n" +
				 "  AND   t.approval_date='"+ approval_date +"' \n" +
	             "GROUP BY e.emp_id,  \n" +
	             "         emp.emp_name,  \n" +
	             "         e.team_cd,  \n" +
	             "         com.code_name,  \n" +
	             "         e.registration_date,  \n" +
	             "         e.perspective,  \n" +
	             "         com1.code_name, \n" +
//	             "         e.strategic_subject,  \n" +
//	             "         com2.code_name, \n" +
//	             "         e.task,  \n" +
//	             "         com3.code_name, \n" +
	             "         t.weighted_value, \n" +
	             "         t.lower_limit_value, \n" +
	             "         t.target_value, \n" +
	             "         t.upper_limit_value \n" +
	             ") aa \n" +
	             "group by        " +
	             "	team_cd,  \n" + 
	             "  team_name, \n" + 
	             "  emp_id,  \n" + 
	             "  emp_name,  \n" +
	             "	registration_date, \n" +
	             "	perspective, \n" +
	             "	perspective_name \n" +
	             "order by team_cd , emp_id, perspective "
//			             + ", strategic_subject, task" 
	             + "";			
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet selectStrategic_subject(String team_cd, String approval_date, String emp_id, String perspective) {
		ResultSet	rs	=	null;

		sql = "select         \n" +
	             "       team_cd,  \n" +
	             "       team_name, \n" +
	             "       emp_id,  \n" +
	             "       emp_name,  \n" +
	             "       registration_date,  \n" +
	             "       perspective,  \n" +
	             "       perspective_name,  \n" +
	             "       strategic_subject,  \n" +
	             "       strategic_subject_name,     \n" +
//	             "       task,  \n" +
//	             "       task_name,       \n" +
//	             "       score, \n" +
//	             "       weighted_value, \n" +
//	             "       lower_limit_value, \n" +
//	             "       target_value, \n" +
//	             "       upper_limit_value, \n" +
	             "       sum(cast( \n" +
	             "        case when (score)>upper_limit_value then score * 0.13 \n" +
	             "        else  \n" +
	             "          case when (score)>=lower_limit_value then  (cast (score as real) / cast(target_value as real)) * weighted_value \n" +
	             "          else 0 end \n" +
	             "        end as numeric(18,2)\n" +
	             "       )) obtained_score  \n" +
	             "from  \n" +
	             "( \n" +
	             "  SELECT  \n" +
	             "       e.emp_id,  \n" +
	             "       emp.emp_name,  \n" +
	             "       e.team_cd,  \n" +
	             "       com.code_name AS team_name,  \n" +
	             "       e.registration_date,  \n" +
	             "       e.perspective,  \n" +
	             "       com1.code_name AS perspective_name,  \n" +
	             "       e.strategic_subject,  \n" +
	             "       com2.code_name AS strategic_subject_name,       \n" +
//	             "       e.task,  \n" +
//	             "       com3.code_name AS task_name,       \n" +
	             "       t.weighted_value, \n" +
	             "       t.lower_limit_value, \n" +
	             "       t.target_value, \n" +
	             "       t.upper_limit_value, \n" +
	             "       sum(e.score) score \n" +
	             "  FROM evidence_mst e  \n" +
	             "  INNER JOIN target_kpi t  \n" +
	             "          ON e.emp_id = t.emp_id  \n" +
	             "         AND e.team_cd = t.team_cd  \n" +
	             "         AND e.registration_date = t.registration_date  \n" +
	             "         AND e.perspective = t.perspective  \n" +
	             "         AND e.strategic_subject = t.strategic_subject  \n" +
	             "         AND e.task = t.task  \n" +
	             "  INNER JOIN approval_kpi a  \n" +
	             "          ON e.emp_id = a.emp_id  \n" +
	             "         AND e.team_cd = a.team_cd  \n" +
	             "         AND e.registration_date = a.registration_date  \n" +
	             "  LEFT OUTER JOIN emp_mst emp ON e.emp_id = emp.emp_id  \n" +
	             "  LEFT OUTER JOIN common_code com  \n" +
	             "               ON com.code_id = 'A0000001'  \n" +
	             "              AND e.team_cd = com.code_value  \n" +
	             "  INNER JOIN common_code com1  \n" +
	             "          ON com1.code_id = 'B0000001'  \n" +
	             "         AND com1.code_value = e.perspective  \n" +
	             "  INNER JOIN common_code com2 \n" +
	             "          ON com2.code_id = 'B0000002'  \n" +
	             "         AND com2.code_value = e.strategic_subject  \n" +
//	             "  INNER JOIN common_code com3 \n" +
//	             "          ON com3.code_id = 'B0000003'  \n" +
//	             "         AND com3.code_value = e.task  \n" +
				 "  WHERE t.team_cd='"+ team_cd +"' \n" +
				 "  AND   t.approval_date='"+ approval_date +"' \n" +
				 "  AND   t.emp_id='"+ emp_id +"' \n" +
				 "  AND   t.perspective='"+ perspective +"' \n" +
	             "GROUP BY e.emp_id,  \n" +
	             "         emp.emp_name,  \n" +
	             "         e.team_cd,  \n" +
	             "         com.code_name,  \n" +
	             "         e.registration_date,  \n" +
	             "         e.perspective,  \n" +
	             "         com1.code_name, \n" +
	             "         e.strategic_subject,  \n" +
	             "         com2.code_name, \n" +
//	             "         e.task,  \n" +
//	             "         com3.code_name, \n" +
	             "         t.weighted_value, \n" +
	             "         t.lower_limit_value, \n" +
	             "         t.target_value, \n" +
	             "         t.upper_limit_value \n" +
	             ") aa \n" +
	             "group by        " +
	             "	team_cd,  \n" + 
	             "  team_name, \n" + 
	             "  emp_id,  \n" + 
	             "  emp_name,  \n" +
	             "	registration_date, \n" +
	             "	perspective, \n" +
	             "	perspective_name, \n" +
	             "	strategic_subject, \n" +
	             "	strategic_subject_name \n" +
	             "order by team_cd , emp_id, perspective, strategic_subject "
//			             + ", task" 
	             + "";			
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet selectTask(String team_cd,  String approval_date, String emp_id, String perspective, String strategic_subject) {
		ResultSet	rs	=	null;

		sql = "select         \n" +
	             "       team_cd,  \n" +
	             "       team_name, \n" +
	             "       emp_id,  \n" +
	             "       emp_name,  \n" +
	             "       registration_date,  \n" +
	             "       perspective,  \n" +
	             "       perspective_name,  \n" +
	             "       strategic_subject,  \n" +
	             "       strategic_subject_name,     \n" +
	             "       task,  \n" +
	             "       task_name,       \n" +
//	             "       score, \n" +
//	             "       weighted_value, \n" +
//	             "       lower_limit_value, \n" +
//	             "       target_value, \n" +
//	             "       upper_limit_value, \n" +
	             "       sum(cast( \n" +
	             "        case when (score)>upper_limit_value then score * 0.13 \n" +
	             "        else  \n" +
	             "          case when (score)>=lower_limit_value then  (cast (score as real) / cast(target_value as real)) * weighted_value \n" +
	             "          else 0 end \n" +
	             "        end as numeric(18,2)\n" +
	             "       )) obtained_score  \n" +
	             "from  \n" +
	             "( \n" +
	             "  SELECT  \n" +
	             "       e.emp_id,  \n" +
	             "       emp.emp_name,  \n" +
	             "       e.team_cd,  \n" +
	             "       com.code_name AS team_name,  \n" +
	             "       e.registration_date,  \n" +
	             "       e.perspective,  \n" +
	             "       com1.code_name AS perspective_name,  \n" +
	             "       e.strategic_subject,  \n" +
	             "       com2.code_name AS strategic_subject_name,       \n" +
	             "       e.task,  \n" +
	             "       com3.code_name AS task_name,       \n" +
	             "       t.weighted_value, \n" +
	             "       t.lower_limit_value, \n" +
	             "       t.target_value, \n" +
	             "       t.upper_limit_value, \n" +
	             "       sum(e.score) score \n" +
	             "  FROM evidence_mst e  \n" +
	             "  INNER JOIN target_kpi t  \n" +
	             "          ON e.emp_id = t.emp_id  \n" +
	             "         AND e.team_cd = t.team_cd  \n" +
	             "         AND e.registration_date = t.registration_date  \n" +
	             "         AND e.perspective = t.perspective  \n" +
	             "         AND e.strategic_subject = t.strategic_subject  \n" +
	             "         AND e.task = t.task  \n" +
	             "  INNER JOIN approval_kpi a  \n" +
	             "          ON e.emp_id = a.emp_id  \n" +
	             "         AND e.team_cd = a.team_cd  \n" +
	             "         AND e.registration_date = a.registration_date  \n" +
	             "  LEFT OUTER JOIN emp_mst emp ON e.emp_id = emp.emp_id  \n" +
	             "  LEFT OUTER JOIN common_code com  \n" +
	             "               ON com.code_id = 'A0000001'  \n" +
	             "              AND e.team_cd = com.code_value  \n" +
	             "  INNER JOIN common_code com1  \n" +
	             "          ON com1.code_id = 'B0000001'  \n" +
	             "         AND com1.code_value = e.perspective  \n" +
	             "  INNER JOIN common_code com2 \n" +
	             "          ON com2.code_id = 'B0000002'  \n" +
	             "         AND com2.code_value = e.strategic_subject  \n" +
	             "  INNER JOIN common_code com3 \n" +
	             "          ON com3.code_id = 'B0000003'  \n" +
	             "         AND com3.code_value = e.task  \n" +
				 "  WHERE t.team_cd='"+ team_cd +"' \n" +
				 "  AND   t.approval_date='"+ approval_date +"' \n" +
				 "  AND   t.emp_id='"+ emp_id +"' \n" +
				 "  AND   t.perspective='"+ perspective +"' \n" +
				 "  AND   t.strategic_subject='"+ strategic_subject +"' \n" +
	             "GROUP BY e.emp_id,  \n" +
	             "         emp.emp_name,  \n" +
	             "         e.team_cd,  \n" +
	             "         com.code_name,  \n" +
	             "         e.registration_date,  \n" +
	             "         e.perspective,  \n" +
	             "         com1.code_name, \n" +
	             "         e.strategic_subject,  \n" +
	             "         com2.code_name, \n" +
	             "         e.task,  \n" +
	             "         com3.code_name, \n" +
	             "         t.weighted_value, \n" +
	             "         t.lower_limit_value, \n" +
	             "         t.target_value, \n" +
	             "         t.upper_limit_value \n" +
	             ") aa \n" +
	             "group by        " +
	             "	team_cd,  \n" + 
	             "  team_name, \n" + 
	             "  emp_id,  \n" + 
	             "  emp_name,  \n" +
	             "	registration_date, \n" +
	             "	perspective, \n" +
	             "	perspective_name, \n" +
	             "	strategic_subject, \n" +
	             "	strategic_subject_name, \n" +
	             "	task, \n" +
	             "	task_name \n" +
	             "order by team_cd , emp_id, perspective, strategic_subject, task "
	             + "";			
		try {
			PreparedStatement pstmt = cn.prepareStatement(sql);
			rs= pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	@Override
	public int delete(HashMap<String, String> keylist) {
		return 0;
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
