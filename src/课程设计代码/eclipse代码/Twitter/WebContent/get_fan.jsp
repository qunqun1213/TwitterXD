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
	List<Info> data = bean.fan(userId);
	//封装成json
	// ,  {"id":"3", "name":"三国志",  "author":"陈寿","price":"80", "pages": "650","press":"浙江出版社"}]
	out.println("[");
	Iterator<Info> iter = data.iterator();
	int i = 0;
	while (iter.hasNext()) {
		++i;
		Info info = iter.next();
		out.println("{");
		out.println("\"_imghead\":\"" + info.get_imghead() + "\",");
		out.println("\"_name\":\"" + info.get_name() + "\",");
		out.println("\"_idnumber\":\"" + info.get_idnumber() + "\",");
		out.println("\"type\":\"" + info.getType() + "\",");
		out.println("\"fan\":\"" + info.getFan() + "\",");
		out.println("\"follow\":\"" + info.getFollow() + "\"");

		out.println("}");
		if (i < data.size()) {
			out.println(",");
		}

	}
	out.println("]");
%>