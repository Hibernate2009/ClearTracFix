package com.bssys.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.bssys.bo.DirectoryBO;


public class TracServiceImpl implements TracService {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public List<String> getAllShemas() {
		// TODO Auto-generated method stub

		List<String> projects = jdbcTemplate.query("select nspname from pg_namespace where nspowner = '16392' order by nspname", new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("nspname");
			}
		});
		return projects;
	}

	@Override
	public List<String> getUsers(String shema) {
		// TODO Auto-generated method stub
		List<String> employees = jdbcTemplate.query("select sid from "+shema+".session where last_visit != '0' order by sid", new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("sid");
			}
		});
		return employees;
	}
	
	@Override
	public List<DirectoryBO> getDirectories(final String shema, Integer ticket, String author, Date mainDate) {
		// TODO Auto-generated method stub
		String field = "hours";
		
		String project = shema+".ticket_change";
		
		System.out.println(shema+" "+ticket + " "+ author+ " "+mainDate);

		long startDate = 0;
		long endDate = 0;
		startDate = mainDate.getTime()*1000;

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(mainDate);
		calendar.add(Calendar.HOUR, 23);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);
		endDate = calendar.getTime().getTime()*1000;
		
		System.out.println(shema+" "+ticket + " "+ author+ " "+mainDate+" "+startDate + " "+endDate);

		List<DirectoryBO> res = jdbcTemplate.query("select ticket, time, author, newvalue from " + project + " where ticket=? and field=? and author=? and time>? and time <?", 
				new Object[] { new Integer(ticket), (String) field, (String) author, new Long(startDate), new Long(endDate) }, new RowMapper<DirectoryBO>() {

			@Override
			public DirectoryBO mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer ticket = rs.getInt("ticket");
				Long time = rs.getLong("time");
				String author = rs.getString("author");
				String hour = rs.getString("newvalue");
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date(time/1000);
				String timeStr= dateFormat.format(date);
				DirectoryBO directory = new DirectoryBO();
				directory.setShema(shema);
				directory.setUnixtime(time);
				directory.setTicket(ticket);
				directory.setTime(timeStr);
				directory.setAuthor(author);
				directory.setHour(hour);
				return directory;
			}

		});
		System.out.println(res);
		return res;
	}

	@Override
	public void updateTime(String shema, Integer ticket, Long lastTime, Long newTime) {
		// TODO Auto-generated method stub
		System.out.println(lastTime + " "+newTime);
		
		String project = shema + ".ticket_change";
		jdbcTemplate.update("update " + project + " SET time = ? WHERE ticket=? and time=?", new Object[] { newTime, ticket, lastTime });
	}

	@Override
	public String generateScript(List<DirectoryBO> list) {
		// TODO Auto-generated method stub
		String script ="$(function() {";
		script = script+"$('#maindp').datepicker({format : 'yyyy-mm-dd'	});";
		if (list!=null){
			for (int i=0;i<list.size();i++){
				script = script+"$('#dp"+i+"').datepicker({format : 'yyyy-mm-dd'});";
			}
		}
		script = script+"});";
		return script;
	}

	
}
