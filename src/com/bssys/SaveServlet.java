package com.bssys;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.bssys.form.DirectoryForm;
import com.bssys.form.ShemaForm;
import com.bssys.model.DirectoryModel;
import com.bssys.model.ErrorModel;
import com.bssys.model.ShemaModel;
import com.bssys.model.UserModel;
import com.bssys.service.TracService;

/**
 * Servlet implementation class SaveServlet
 */
public class SaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("clean.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DirectoryForm directoryForm = new DirectoryForm();
		Map directoryModelMap = request.getParameterMap();
		BeanUtilsBean beanUtilsBean = new BeanUtilsBean();

		WebApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		TracService tracservice = (TracService) context.getBean("tracService");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			beanUtilsBean.populate(directoryForm, directoryModelMap);
			
			
			HttpSession session = request.getSession();
			ShemaModel shemaModel = (ShemaModel)session.getAttribute("shemaModel");
			UserModel userModel = (UserModel)session.getAttribute("userModel");
			String shema = shemaModel.getShemaForm().getShema();
			Integer ticket = userModel.getUserForm().getTicket();
			String user = userModel.getUserForm().getUser();
			String dateStr = userModel.getUserForm().getDate();
			ErrorModel errorModel = (ErrorModel)session.getAttribute("errorModel");

			Date date = null;
			try {
				date = dateFormat.parse(dateStr);
			} catch (ParseException e1) {
				errorModel.setChangetime(true);
				response.sendRedirect("clean.jsp");
				return;
			}
			Date newDate = null;
			try{
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				newDate = dateFormat.parse(directoryForm.getChangetime());
			}
			catch(ParseException e){
				errorModel.setChangetime(true);
				response.sendRedirect("clean.jsp");
				return;
			}
			errorModel.setChangetime(false);
			
			
			//Получаем часы минуты и секунды из времени сохраненного в траке
			long lastTime = directoryForm.getUnixtime();
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(lastTime);
			int hour = calendar.get(GregorianCalendar.HOUR);
			int minute = calendar.get(GregorianCalendar.MINUTE);
			int second =  calendar.get(GregorianCalendar.SECOND);
			
			//Переносим часы минуты и секунды в новую дату
			calendar = new GregorianCalendar();
			calendar.setTime(newDate);
			calendar.set(GregorianCalendar.HOUR, hour);
			calendar.set(GregorianCalendar.MINUTE, minute);
			calendar.set(GregorianCalendar.SECOND, second);
			long changeTimeMillis = calendar.getTimeInMillis()*1000;
			
			System.out.println(lastTime+" "+changeTimeMillis);
			tracservice.updateTime(shema, ticket, lastTime, changeTimeMillis);
			
			//Обновляем модель
			
			List<DirectoryBO> directories = tracservice.getDirectories(shema, ticket, user, date);
			DirectoryModel dirModel = new DirectoryModel();
			dirModel.setDirectories(directories);
			dirModel.setDirectoryForm(directoryForm);
			session.setAttribute("directoryModel", dirModel);
			
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
