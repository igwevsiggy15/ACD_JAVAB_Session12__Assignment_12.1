package db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * This class connects and inserts to a database given properties.
 * @author Igwe
 * @since 1.0
 */
public class DB {
	/**
	 * database connection.
	 */
	private Connection con;
	/**
	 * Properties file of database connection
	 */
	private String propFile;
	
	/**
	 * Constuctor. Initiates the database properties using properties instance prop.
	 * @param prop <p>This is the properties instance. Must have "driver_type", "driver_path", "database_address", "database_name", "user", and  "pass"
	 * For example: 	driver_type=mysql driver_path=C:\\path\\to\\Driver.jar database_address=127.0.0.1:3306 database_name=databaseName user=username	pass=password
	 * </p>
	 */
	public DB(String prop) {
		this.propFile = prop;
	}
	
	/**
	 * Attempts to connect to database using properties.
	 * @return true if connection is successful, false otherwise.
	 * @throws DBExceptions throws if there is an error.
	 */
	public boolean connect() throws DBExceptions {
		Properties prop = getProperties(propFile);
		con = DBConnection.getDBInstance(prop);
		return con != null;
	}
	
	/**
	 * Sees if connection is open
	 * @return true if open false if closed
	 * @throws DBExceptions if a database error occurs.
	 */
	public boolean isOpen() throws DBExceptions {
		try {
			return !(con.isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBExceptions("Database error.");
		} catch (Exception e) {
			throw new DBExceptions(e.getMessage());
		}
	}
	/**
	 * if connection is open, closes the connection
	 * @return returns true;
	 * @throws DBExceptions if there is a database error
	 */
	public boolean closeConnection() throws DBExceptions {
		if (isOpen()) {
			try {
				DBConnection.closeConnection();
			} catch (DBExceptions e) {
				// TODO Auto-generated catch block
				throw new DBExceptions("Cannot close.");
			} catch (Exception e) {
				throw new DBExceptions("Other error.");
			}
		}
		return true;
	}
	
	/**
	 * Given the tablename and Hashmap of column_name to column_value, creates a query and attempt to insert to database.
	 * @param tableName the name of the table to insert into.
	 * @param hm the hashmap of the column_name:value pair.
	 * @return "Success" if success, "failure" if failed
	 * @throws DBExceptions if query execution error occurs.
	 * @throws SQLException 
	 */
	public String saveData(String tableName,HashMap<String,String> hm) throws DBExceptions, SQLException {
		String query = "INSERT INTO {{table}} ({{columns}}) VALUES ({{values}});";
		query = query.replace("{{table}}", tableName);
		StringBuilder columns = new StringBuilder("");
		StringBuilder values = new StringBuilder("");
		Set<String> keys = hm.keySet();
		for (String string : keys) {
			if(columns.length() == 0) {
				columns.append("`"+string+"`");
				values.append("\'"+ hm.get(string)+"\'");
			}else {
				columns.append(", `"+ string + "`");
				values.append(", \'"+ hm.get(string)+"\'");
			}
		}
		query = query.replace("{{columns}}", columns.toString());
		query = query.replace("{{values}}", values.toString());
		return saveData(query);
	}
	
	/**
	 * Given the tablename, id column, id value, and Hashmap of column_name to column_value, creates a query and attempt to update entry in database.
	 * @param tableName the name of the table to insert into.
	 * @param hm the hashmap of the column_name:value pair.
	 * @return "Success" if success, "failure" if failed
	 * @throws DBExceptions if query execution error occurs.
	 * @throws SQLException 
	 */
	public String saveData(String tableName, HashMap<String,String> hm, String idColumn, String id) throws DBExceptions, SQLException {
		String query = "UPDATE {{table}} SET {{sets}} where {{column_id}} = {{id}};";
		query = query.replace("{{table}}", tableName);
		query = query.replace("{{column_id}}", idColumn);
		query = query.replace("{{id}}", String.valueOf(id));
		
		String set = "{{column}} = {{value}}";
		StringBuilder sets = new StringBuilder("");
		Set<String> keys = hm.keySet();
		for (String string : keys) {
			if(sets.length() == 0) {
				sets.append("`" + string + "` = \'" + hm.get(string)+ "\'");
			}else {
				sets.append(", `" + string + "` = \'" + hm.get(string) + "\'");
			}
		}
		query = query.replace("{{sets}}", sets);
		return saveData(query);
	}
	
	/**
	 * Deletes from table where id column = id.
	 * @param tableName name of the table
	 * @param columnID name of the column
	 * @param id value of the column
	 * @return success if success, failure if failed
	 * @throws DBExceptions if there is an error
	 * @throws SQLException 
	 */
	public String saveData(String tableName, String columnID, String id) throws DBExceptions, SQLException {
		String query = "DELETE FROM {{table}} WHERE {{column}} = {{value}};";
		query = query.replace("{{table}}", tableName);
		query = query.replace("{{column}}", columnID);
		query = query.replace("{{value}}", String.valueOf(id));
		return saveData(query);
	}
	
	/**
	 * Loads properties from file "config.properties".
	 * @return Properties specified by file.
	 * @throws DBExceptions throws when file cannot be opened.
	 */
	public Properties getProperties(String filename) throws DBExceptions {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filename));
		} catch (Exception e) {
			System.out.println("no properties");
			throw new DBExceptions("No properties found.");
		}
		return prop;
	}
	
	/**
	 * Given a query runs query on the database.
	 * @param query the query
	 * @return "Success" if successful, "failure" if failed.
	 * @throws DBExceptions if query excecution error occurs.
	 * @throws SQLException 
	 */
	public String saveData(String query) throws DBExceptions, SQLException {
		int num = DBUtilities.executeUpdate(con, query);
		if (num == 0) {
		return "failure";
		} else {
			return "Success";
		}
	}
	
	public ResultSet getData(String tableName) throws DBExceptions, SQLException{
		String query = "SELECT * FROM " + tableName;
		return DBUtilities.executeQuery(con, query);
	}
	
	public String switchDatabase(String dbName) throws DBExceptions, SQLException {
		String query  = "use " + dbName;
		if (DBUtilities.execute(con, query)) {
			return "Success";
		}
		return "failure";
	}
}
