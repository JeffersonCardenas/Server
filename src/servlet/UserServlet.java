package servlet;

import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UserServlet.class);
	private static String path;


	public void init(ServletConfig config) throws ServletException{
		String pathImg = config.getServletContext().getRealPath("/");
		setPath(pathImg);
		System.out.println("Log4JInitServlet is initializing log4j");
		String log4jLocation = config.getInitParameter("log4jConfiguration");

		String path = UserServlet.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		int last = path.length();
		String pathProperties = path.substring(0, last-25);

		if (log4jLocation == null) {
			System.err.println("*** No log4j-properties-location init param");
		} else {
			String log4jProp = pathProperties + "config/log4j2.properties";
			File file = new File(log4jProp);
			if (file.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);
				logger.info("Logger Cargado");
			} else {
				System.err.println("*** " + log4jProp + " file not found at "+log4jProp);
			}
		}
		super.init(config);
	}


	/**
	 * @return the path
	 */
	public static String getPath() {
		return path;
	}


	/**
	 * @param path the path to set
	 */
	public static void setPath(String p) {
		path = p;
	}
		

}
