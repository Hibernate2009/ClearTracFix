package com.bssys;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.bssys.model.ErrorModel;
import com.bssys.model.ShemaModel;
import com.bssys.service.TracService;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		TracService tracservice =  (TracService)context.getBean("tracService");
		
		ErrorModel errorModel = new ErrorModel();
		ShemaModel shemaModel = new ShemaModel();
		List<String> shemas = tracservice.getAllShemas();
		shemaModel.setShemas(shemas);
		
		HttpSession session = request.getSession();
		session.setAttribute("shemaModel", shemaModel);
		session.setAttribute("errorModel", errorModel);
		
		response.sendRedirect("clean.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
