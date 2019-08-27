package db;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

/**
 * App to test DB. 
 * @author Igwe
 *
 */
public class DBApp {
	public static void main(String[] args) {		
		DB db = new DB("app.properties");				//create db and connect
		System.out.println("Connecting to database");
		try {
			System.out.println(db.connect());
		} catch (DBExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		HashMap<String, String> hm = new HashMap<String, String>(); //make artist hashmap
		hm.put("artist_name", "blah");
		hm.put("nationality", "USA");
		hm.put("artist_type", "band");
		
		System.out.println("Inserting artist");
		try {
			System.out.println(db.saveData("artists", hm));
		} catch (DBExceptions e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Insert artist to albums. Should fail.");
		try {
			System.out.println(db.saveData("albums", hm));
		} catch (DBExceptions e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Closing database connection.");
		try {
			System.out.println(db.closeConnection());
		} catch (DBExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //close db connection
	}

}
