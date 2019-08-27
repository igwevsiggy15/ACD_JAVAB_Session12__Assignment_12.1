package controller;

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

/**
 * Servlet implementation class BookUpdaterController
 */
@WebServlet("/BookUpdate")
public class BookUpdaterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookUpdaterController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    DB db;
    String table = "books";
    String idCol = "book_id";
    String titleCol = "title";
    String authorCol = "author";
    String pubCol = "publisher";
    String yearCol = "publication_year";
    String priceCol = "price";
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("bID");
		String title = request.getParameter("bTitle");
		String author = request.getParameter("bAuthor");
		String pub = request.getParameter("bPub");
		String year = request.getParameter("bYear");
		String price = request.getParameter("bPrice");
		String rType = request.getParameter("type");
		HashMap<String, String> hm = new HashMap<String,String>();
		if (id != null && !id.isEmpty()) {
			hm.put(idCol, id);
		}
		hm.put(titleCol, title);
		hm.put(authorCol, author);
		hm.put(pubCol, pub);
		hm.put(yearCol, year);
		hm.put(priceCol, price);
		String message = "";
		if (rType.equals("delete")) {
			try {
				 message = db.saveData(table, idCol, id);
				
			} catch (DBExceptions e) {
				// TODO Auto-generated catch block
				message = "Something went wrong.";
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				message ="Something went wrong.";
				e.printStackTrace();
			}
		} else if (rType.equals("add")) {
			try {
				message = db.saveData(table, hm);
			} catch (DBExceptions e) {
				// TODO Auto-generated catch block
				message = "Something went wrong.";
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				message = "Something went wrong.";
				e.printStackTrace();
			}
		} else if (rType.equals("update")) {
			try {
				hm.remove(idCol);
				message = db.saveData(table, hm, idCol, id);
			} catch (DBExceptions e) {
				message = "Something went wrong.";
				e.printStackTrace();
			} catch (SQLException e) {
				message = "Something went wrong.";
				e.printStackTrace();
			}
		}
		response.getWriter().append(message);
		response.setHeader("Refresh", "3; URL=/BookShopping/");
	}

}
