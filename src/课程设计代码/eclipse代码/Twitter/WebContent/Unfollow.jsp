<%@page import="bean.Info"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String userId = request.getParameter("userId");
	String followId = request.getParameter("followId");
	
	//userId 取关followId
	InfoBean user = new InfoBean();
	int rs = user.unfollow(userId,followId);
	out.println(rs);
%>