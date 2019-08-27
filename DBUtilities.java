package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility methods for executing database queries
 * @author Igwe
 *
 */
public class DBUtilities {
	/**
	 * Executes database update.
	 * @param con the database connection
	 * @param query the query
	 * @return 1 if success, 0 otherwise.
	 * @throws DBExceptions 
	 * @throws SQLException 
	 */
	public static int executeUpdate(Connection con, String query) throws DBExceptions, SQLException {
		int rs;
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeUpdate(query);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static ResultSet executeQuery(Connection con, String query) throws DBExceptions, SQLException{
		ResultSet rs;
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	} 
	
	public static boolean execute(Connection con, String query) throws DBExceptions, SQLException{
		boolean rs;
		Statement stmt;
		try {
			stmt = con.createStatement();
			rs = stmt.execute(query);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public static String printEntireRS(ResultSet rs, String brk){
		String res = "";
		String strB;
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				res += (rsmd.getColumnName(i)+" ");
			}
			res += brk;
			while(rs.next()){
				strB = "";
				for(int i=1;i<=rsmd.getColumnCount();i++){
					strB=strB+" "+(rs.getString(i));
				}
				res += strB + brk;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	public static String getEntireRSTable(ResultSet rs){
		String res = "<table border=\"1\">";
		String strB;
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			res+= "<tr>";
			for(int i=1;i<=rsmd.getColumnCount();i++){
				res += "<th>" +(rsmd.getColumnName(i)+"</th>");
			}
			res += "</tr>";
			while(rs.next()){
				strB = "<tr>";
				for(int i=1;i<=rsmd.getColumnCount();i++){
					strB=strB+"<td>"+(rs.getString(i)) + "</td>";
				}
				res += strB + "</tr>";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
}
