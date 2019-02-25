<%@page import="bean.Info"%>
<%@page import="bean.InfoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String _idnumber = request.getParameter("id");
	String _content = request.getParameter("text");
	String _name = request.getParameter("name");
	String _wordTime = request.getParameter("time");
	String _imghead = request.getParameter("img_id");
	String pic = request.getParameter("path");
	int twiid = 0;
	
	InfoBean user = new InfoBean();
	Info info = new Info(_imghead,  _name,  _idnumber,  _wordTime,  _content,
            "0","0", "0");
	info.setPic(pic);
	int rs = user.addTwitter(info);
	out.println(rs);
%>