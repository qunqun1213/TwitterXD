
<%@page import="bean.Info"%>
<%@page import="java.util.Iterator"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	// 接收参数
	// id
	String userId = request.getParameter("userId");
	// page
	String headimage = request.getParameter("headimage");

	// 执行　
	InfoBean bean = new InfoBean();
	int result = bean.updateimage(userId, headimage);
	System.out.println("result = " + result);

	out.println(result);
%>
