package com.bssys;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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

import com.bssys.bo.DirectoryBO;
import com.bssys.form.ShemaForm;
import com.bssys.form.UserForm;
import com.bssys.model.DirectoryModel;
import com.bssys.model.ErrorModel;
import com.bssys.model.ShemaModel;
import com.bssys.model.UserModel;
import com.bssys.service.TracService;

/**
 * Servlet implementation class ProcessServlet
 */
public class ProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("clean.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserForm userForm = new UserForm();
		Map userModelMap = request.getParameterMap();
		BeanUtilsBean beanUtilsBean = new BeanUtilsBean();
		
		WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		TracService tracservice =  (TracService)context.getBean("tracService");
		
		try {
			beanUtilsBean.populate(userForm, userModelMap);
			HttpSession session = request.getSession();
			
			ShemaModel shemaModel = (ShemaModel)session.getAttribute("shemaModel");
			String shema = shemaModel.getShemaForm().getShema();
			Integer ticket = userForm.getTicket();
			String dateStr = userForm.getDate();
			String user = userForm.getUser();
			
			DirectoryModel directoryModel = new DirectoryModel();
			session.setAttribute("directoryModel", directoryModel);
			ErrorModel errorModel = new ErrorModel();
			session.setAttribute("errorModel", errorModel);
			if (ticket==0){
				UserModel userModel = (UserModel)session.getAttribute("userModel");
				userModel.setUserForm(userForm);
				errorModel.setTicket(true);
				response.sendRedirect("clean.jsp");
				return;
			}else{
				errorModel.setTicket(false);
			}
			if (user==null || (user!=null && "".equals(user))){
				UserModel userModel = (UserModel)session.getAttribute("userModel");
				userModel.setUserForm(userForm);
				errorModel.setUser(true);
				response.sendRedirect("clean.jsp");
				return;
			}else{
				errorModel.setUser(false);
			}
			if (dateStr==null || (dateStr!=null && "".equals(dateStr))){
				UserModel userModel = (UserModel)session.getAttribute("userModel");
				userModel.setUserForm(userForm);
				errorModel.setDate(true);
				response.sendRedirect("clean.jsp");
				return;
			}else{
				errorModel.setDate(false);
			}
			
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(dateStr);
			
			
			UserModel userModel = (UserModel)session.getAttribute("userModel");
			userModel.setUserForm(userForm);
			
			List<DirectoryBO> directories = tracservice.getDirectories(shema, ticket, user, date);
			DirectoryModel dirModel = new DirectoryModel();
			dirModel.setDirectories(directories);
			
			
			session.setAttribute("directoryModel", dirModel);
			response.sendRedirect("clean.jsp");
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
