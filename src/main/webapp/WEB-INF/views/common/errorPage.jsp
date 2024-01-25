<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>에러 페이지</title>
	</head>
	<body>
		<h1>에러 메시지</h1>
		<!-- 	
		LoginController에서 데이터가 없거나 예외 발생 시
		request에 메시지를 set 해주므로 그것을 사용
 		예외 중에서 NullPointer Exception 발생하면 msg는 null로
 		넘어오는데 이것을 처리하기 위해서 if 문을 씀
 		jsp에서 if 문을 쓰려면 jstl이 필요하고, jsp 파일 내에
 		talib 구문을 이용하여 core Tag를 사용할 수 있도록 함
 		-->
		<c:if test="${msg eq null }">
			<h2>NullPointerException 발생!</h2>
			<h2>Null인 곳을 찾아주세요!</h2>
		</c:if>
		<h2>${msg }</h2>
	</body>
</html>