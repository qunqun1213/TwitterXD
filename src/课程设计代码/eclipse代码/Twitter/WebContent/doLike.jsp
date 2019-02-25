<%@page import="bean.Info"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String _idnumber = request.getParameter("_idnumber");
	String articleId = request.getParameter("articleId");
	

	
	InfoBean user = new InfoBean();
	Info info = new Info();
	info.set_idnumber(_idnumber);
	info.setArticleId(Integer.parseInt(articleId));
	int rs = user.doLike(info);
	out.println(rs);
%>