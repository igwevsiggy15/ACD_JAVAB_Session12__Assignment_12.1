package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DB;
import db.DBExceptions;
import db.DBUtilities;

/**
 * Servlet implementation class BookShopController
 */
@WebServlet("")
public class BookShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookShopController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    DB db;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//load books from data base
		System.out.println("at home.");
		db = new DB("C:\\Users\\syste\\Documents\\BookShopping\\app.properties");
		try {
			db.connect();
		} catch (DBExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("Closing.");
		try {
			db.closeConnection();
		} catch (DBExceptions e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String tablePrint() {
		String toPrint = "";
		try {
			toPrint = DBUtilities.getEntireRSTable(db.getData("books"));
		} catch (DBExceptions | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toPrint;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		RequestDispatcher requestDispatcher; 
		requestDispatcher = request.getRequestDispatcher("/WEB-INF/index.jsp");
		request.setAttribute("table", tablePrint());
		requestDispatcher.forward(request, response);
//		HashMap<String, String> hm = new HashMap<String, String>();
//		hm.put("title", "booktitle");
//		hm.put("authors", "bookauthor");
//		hm.put("publisher", "bookpub");
//		hm.put("publication_year", "2014");
//		hm.put("price", "2.0");
//		try {
//			db.saveData("books", hm);
//			db.saveData("books", "book_id", 1);
//			db.saveData("books", hm, "book_id", 2);
//		} catch (DBExceptions e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
