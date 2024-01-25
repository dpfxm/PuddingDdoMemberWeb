<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원 정보 수정</title>
	</head>
	<body>
		<form action="/member/modify.do" method="post">
			<fieldset>
				<legend>회원 정보 수정</legend>
				<ul>
					<li>
						<label for ="">아이디</label>
						<input type="text" name = "member-id" id = "member-id" value="${member.memberId }" readonly>
					</li>
					<li>
						<label for ="">비밀번호</label>
						<input type="password" name = "member-pw" id = "member-pw"  value="${member.memberPw }">
					</li>
					<li>
						<label for ="">이름</label>
						<input type="text" name = "member-name" id = "member-name"  value="${member.memberName }" readonly>
					</li>
					<li>
						<label for ="">나이</label>
						<input type="text" name = "member-age" id = "member-age" value="${member.memberAge }" readonly>
					</li>
					<li>
						<label for ="">성별</label>
						<c:if test="${member.memberGender eq 'M' }">남</c:if>
						<c:if test="${member.memberGender eq 'F' }">여</c:if>
					</li>
					<li>
						<label for ="">이메일</label>
						<input type="text" name = "member-email" id = "member-email"  value="${member.memberEmail }">
					</li>
					<li>
						<label for ="">전화번호</label>
						<input type="text" name = "member-phone" id = "member-phone" value="${member.memberPhone }">
					</li>
					<li>
						<label for ="">주소</label>
						<input type="text" name = "member-address" id = "member-address" value="${member.memberAddress }">
					</li>
					<li>
						<label for ="">취미</label>
						<input type="text" name = "member-hobby" id = "member-hobby" value="${member.memberHobby }">
					</li>
				</ul>
			</fieldset>
			<input type="submit" value="수정하기">
			<input type="reset" value="초기화">
		</form>
	</body>
</html>