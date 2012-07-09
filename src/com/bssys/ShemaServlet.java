package com.bssys;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.bssys.form.ShemaForm;
import com.bssys.form.UserForm;
import com.bssys.model.DirectoryModel;
import com.bssys.model.ErrorModel;
import com.bssys.model.ShemaModel;
import com.bssys.model.UserModel;
import com.bssys.service.TracService;

/**
 * Servlet implementation class ShemaServlet
 */
public class ShemaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShemaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("clear.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ShemaForm shemaForm = new ShemaForm();
		Map shemaModelMap = request.getParameterMap();
		BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
		
		WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		TracService tracservice =  (TracService)context.getBean("tracService");
		
		try {
			beanUtilsBean.populate(shemaForm, shemaModelMap);
			String shema = shemaForm.getShema();
			List<String> users = tracservice.getUsers(shema);
			UserModel userModel = new UserModel();
			UserForm userForm = new UserForm();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			userForm.setDate(dateFormat.format(new Date()));
			userModel.setUserForm(userForm);
			userModel.setUsers(users);
			
			HttpSession session = request.getSession();
			ShemaModel shemaModel = (ShemaModel)session.getAttribute("shemaModel");
			shemaModel.setShemaForm(shemaForm);
			session.setAttribute("userModel", userModel);
			DirectoryModel directoryModel = new DirectoryModel();
			session.setAttribute("directoryModel", directoryModel);
			ErrorModel errorModel = new ErrorModel();
			session.setAttribute("errorModel", errorModel);
			response.sendRedirect("clean.jsp");
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
