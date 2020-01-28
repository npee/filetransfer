<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>analysis</title>
	</head>
	<body>
		<form action="<c:url value="/socket/upload"/>" method="POST" enctype="multipart/form-data">
			<input type="file" name="file1">
			<input type="submit" value="upload">
		</form>
	</body>
</html>