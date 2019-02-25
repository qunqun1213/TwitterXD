<%@page import="java.util.List"%>
<%@page import="bean.Info"%>
<%@page import="java.util.Iterator"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
	String userId = request.getParameter("_idnumber");
	InfoBean bean = new InfoBean();
	List<Info> data = bean.get_Recent("lmmiang");
	//封装成json
	out.println("[");
	Iterator<Info> iter = data.iterator();
	int i = 0;
	while (iter.hasNext()) {
		++i;
		Info info = iter.next();
		out.println("{");
		out.println("\"articleId\":\"" + info.getArticleId() + "\",");
		out.println("\"_imghead\":\"" + info.get_imghead() + "\",");

		out.println("\"_name\":\"" + info.get_name() + "\",");

		out.println("\"_content\":\"" + info.get_content() + "\"");

		out.println("}");
		if (i < data.size()) {
			out.println(",");
		}

	}
	out.println("]");
%>
