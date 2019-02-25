<%@page import="java.util.List"%>
<%@page import="bean.Info"%>
<%@page import="java.util.Iterator"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
	String userId = request.getParameter("userId");
	InfoBean bean = new InfoBean();
	String x = "lmmiang";
	List<String> data = bean.getHistory(userId);
	//封装成json

	out.println("[");
	Iterator<String> iter = data.iterator();
	int i = 0;
	while (iter.hasNext()) {

		String info;
		info = iter.next();

		++i;

		out.println("{");

		out.println("\"_content\":\"" + info + "\"");

		out.println("}");
		if (i < data.size()) {
			out.println(",");
		}

	}
	out.println("]");
%>
