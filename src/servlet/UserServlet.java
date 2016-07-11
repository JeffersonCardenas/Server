package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class UserServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		request.setAttribute("result", "This is the result of the servlet call");
		request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
		request.getAttribute("resultado");
		/*User user = new User();
		sendResponse(response, user.toXML(), true);*/
	}

}
