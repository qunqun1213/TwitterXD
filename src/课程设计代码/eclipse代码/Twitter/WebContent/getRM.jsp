<%@page import="java.util.List"%>
<%@page import="bean.Info"%>
<%@page import="java.util.Iterator"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
	String searchText = request.getParameter("searchText");
	InfoBean bean = new InfoBean();
	//List< Info> data =  bean.getRM("R");
	List<Info> data = bean.getRM(searchText);
	//封装成json

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

		out.println("\"_wordTime\":\"" + info.get_wordTime() + "\",");

		out.println("\"_content\":\"" + info.get_content() + "\",");
		out.println("\"_ctm\":\"" + info.get_ctm() + "\",");

		out.println("\"_tn\":\"" + info.get_tn() + "\",");

		out.println("\"_lk\":\"" + info.get_lk() + "\",");

		out.println("\"_name2\":\"" + info.get_name2() + "\",");

		out.println("\"_idnumber2\":\"" + info.get_idnumber2() + "\",");

		out.println("\"_wordTime2\":\"" + info.get_wordTime2() + "\",");
		out.println("\"_content2\":\"" + info.get_content2() + "\",");

		out.println("\"type\":\"" + info.getType() + "\",");

		out.println("\"articleId\":\"" + info.getArticleId() + "\"");

		out.println("}");
		if (i < data.size()) {
			out.println(",");
		}

	}
	out.println("]");
%>
