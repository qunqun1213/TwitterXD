<%@page import="bean.Info"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String _idnumber = request.getParameter("_idnumber");
	String _content = request.getParameter("searchText");

	
	InfoBean user = new InfoBean();
//	int rs = user.addHistroy(info);
	int rs = user.addHistroy(_idnumber, _content);
	out.println(rs);
%>