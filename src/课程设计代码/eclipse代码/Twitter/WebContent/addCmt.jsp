<%@page import="bean.Info"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String _idnumber = request.getParameter("_idnumber");
	String _content = request.getParameter("cmtContent");
	String _wordTime = request.getParameter("cmtTime");
	String articleId = request.getParameter("articleId");
	

	
	InfoBean user = new InfoBean();
	Info info = new Info();
	info.set_idnumber(_idnumber);
	info.set_content(_content);
	info.set_wordTime(_wordTime);
	info.setArticleId(Integer.parseInt(articleId));
	int rs = user.addCmt(info);
	out.println(rs);
%>