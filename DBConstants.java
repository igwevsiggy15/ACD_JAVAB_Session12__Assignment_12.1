package db;

/**
 * Database constants to connect to database depending on database type.
 * @author Igwe
 *
 */
public class DBConstants {
	/**
	 * URL string for jar. Used on driver loading.
	 */
	static String jar = "jar:file:///{{driverPath}}!/";
	/**
	 * connection string for mysql
	 */
	static String mySqlUrl = "jdbc:mysql://{{database}}";
	/**
	 * driver class for mysql
	 */
	static String mySqlDriver = "com.mysql.cj.jdbc.Driver";
	/**
	 * connection string for oracle
	 */
	static String oracleUrl = "jdbc:oracle:thin:@{{database}}";
	/**
	 * driver class for oracle
	 */
	static String oracleDriver = "oracle.jdbc.driver.OracleDriver";
	/**
	 * connection string for postgre
	 */
	static String postUrl = "jdbc:postgresql://{{database}}";
	/**
	 * driver class for postgre
	 */
	static String postDriver = "org.postgresql.Driver";
	/**
	 * connection string for microsoft
	 */
	static String msUrl = "jdbc:sqlserver://{{address}};databaseName= {{database}}";
	/**
	 * driver class for microsoft
	 */
	static String msDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
}
