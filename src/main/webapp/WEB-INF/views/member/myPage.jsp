<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>마이 페이지</title>
	</head>
	<body>
		<!-- 		
		DB에서 가져온 데이터를 출력해 주는 페이지로 로그인 한 사람의
 		아이디로 가져온 데이터여야 함. 딱 한 사람의 데이터만 가져옴!
 		마이페이지에서는 정보 수정 버튼과 회원 탈퇴 버튼 필수
 		버튼은 a 태그로 만들며 get 방식 호출! 버튼을 눌렀을 때는
 		탈퇴를 할 건지 물어보는 자바스크립트 코드 작성하기
 		- myPage.jsp에서는 Controller에서 request에 member라는 키값으로
 		member 객체를 setAttribute 해주면 $ member. 를 통해서 받아쓸 수 있음
		- c:if에서 노란 줄이 보이면 jsp 내에서 taglib을 적지 않은 것
 		-->
 		<ul>
			<li>
				<label for ="">아이디</label>
				${member.memberId }
			</li>
			<li>
				<label for ="">비밀번호</label>
				${member.memberPw }
			</li>
			<li>
				<label for ="">이름</label>
				${member.memberName }
			</li>
			<li>
				<label for ="">나이</label>
				${member.memberAge }
			</li>
			<li>
				<label for ="">성별</label>
				<c:if test="${member.memberGender eq 'M'}">남</c:if>
				<c:if test="${member.memberGender eq 'F'}">여</c:if>
			</li>
			<li>
				<label for ="">이메일</label>
				${member.memberEmail }
			</li>
			<li>
				<label for ="">전화번호</label>
				${member.memberPhone }
			</li>
			<li>
				<label for ="">주소</label>
				${member.memberAddress }
			</li>
			<li>
				<label for ="">취미</label>
				${member.memberHobby }
			</li>
		</ul>
		<!-- 
		ModifyController에서 doGet 메소드를 사용하여 정보 수정 페이지로 이동
		a 태그는 url을 get 방식으로 요청하기 때문에 doGet() 메소드 사용!
		doGet() 메소드에서 디비에서 가져온 값을 member로 setAttribute하여
		modify.jsp로 보내주어 화면에 출력되도록 함!
		-->
		<a href="/member/modify.do?memberId=${sessionScope.memberId }">정보수정</a>
		<a href="javascript:void(0)" onclick = "deleteCheck();">회원탈퇴</a>
	</body>
	<script>
		function deleteCheck() {
			if (confirm("탈퇴하시겠습니까?")) {
				var memberId = "${sessionScope.memberId }";
				location.href = "/member/delete.do?memberId=" + memberId;
			}
		}
	</script>
</html>