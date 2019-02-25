<%@page import="java.util.List"%>
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
	String name = request.getParameter("_name");

	// 执行　
	InfoBean bean = new InfoBean();
	boolean result = bean.updatename(userId, name);
	System.out.println("result = " + result);

	// 返回执行结果
	if (result) {
		out.println(1);
	} else {
		out.println(0);
	}
%>
