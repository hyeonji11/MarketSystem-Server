<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ms.domain.Item"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
List<Item> list = (List<Item>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사이트 - 게시물 리스트</title>
</head>
<body>
	<h1>게시물 리스트</h1>
	<h3>${ itemList }</h3>
	<h3>
		<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ itemList.Date }" />
	</h3>
</body>
</html>