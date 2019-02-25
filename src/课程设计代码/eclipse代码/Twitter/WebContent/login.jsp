<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="bean.Info"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	//String id = "qunqu";
	//String pass = "12345678";
	InfoBean user = new InfoBean();
	List<Info> data = user.login(id, pass);

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

		out.println("\"fan\":\"" + info.getFan() + "\",");

		out.println("\"follow\":\"" + info.getFollow() + "\"");

		out.println("}");
		if (i < data.size()) {
			out.println(",");
		}
	}
	out.println("]");
%>