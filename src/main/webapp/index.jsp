<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>푸딩 또 멤버웹</title>
	</head>
	<body>
		<h1>푸딩 또 멤버웹</h1>
		<h2>방문을 진심으로 환영합니다.</h2>
<!-- 		
			session에 담긴 memberId 값이 있으면 로그인이 된 것이고 없으면 안 된 것!
			session에서 memberId 값이 있는지 없는지 체크하기 위해서는 if 문이 필요
	 		jstl은 jsp에서 for, if 문과 같은 제어문을 사용하게 해주는 라이브러리
	 		라이브러리를 사용하기 위해서는 jar 파일이 필요하며, 다이나믹 웹 프로젝트에서는
	 		jar 파일을 WEB-INF/lib 폴더에 위치시켜야 함
	 		(JDBC 사용할 때에도 필요한 드라이버인 ojdbc8.jar 파일도 lib에 위치시키기!)
	 		jstl의 경우 jar 파일만 있다고 쓸 수 있는 것이 아니라 taglib을 작성해주어야 함 
-->
		<c:if test="${sessionScope.memberId ne null }">
			${sessionScope.memberName } 님, 어서오세요!
			<a href="javascript:void(0)" onclick="checkLogout();">로그아웃</a>
		</c:if>
<!-- 		
			jstl에는 다양한 종류의 태그가 있는데 그 중 제어문을 담당하는 core태그를 사용하는 것
	 		그 태그를 c라는 문자를 이용해서 쓰겠다는 내용을 isp에 써주는 것
	 		c if 이용해서 로그인 여부에 따라 다르게 화면 출력하기
	 		특이하게 test다음에 EL인 $를 써서 session에 저장하는 값을 불러 오는데
	 		sessionScope는 session에 접근하기 위해 작성해주는 내장 객체이며 생략가능
	 		하지만 request에도 member Id가 있다면 둘을 구별하기 위해서 꼭 써줘야 함 -> 키값이 중복되지 않는다면 생략해도 됨
	 		memberId == null이면 로그인 폼을 보여주고 memberId != null이면(Not Equal) 로그인을 했다는 것
	 		-> 로그인 한 사용자의 이름을 세션에서 가져와서 출력
	 		-> 로그아웃 url은 /member/logout.do로 a 링크를 통해 호출되므로 get 방식 호출이 됨
-->
		<c:if test="${sessionScope.memberId eq null }">
			<fieldset>
				<legend>로그인</legend>
				<!-- 쿼리스트링이 보이지 않는 post 메소드 선택 -->
				<form action="/member/login.do" method="post">
					<input type="text" name= "memberId" placeholder="아이디를 입력해주세요"><br>
					<input type="password" name= "memberPw" placeholder="비밀번호를 입력해주세요"><br>
					<div>
						<input type="submit" value=로그인>
						<a href="/member/register.do">회원가입</a>
					</div>
				</form>
			</fieldset>
		</c:if>
		<script type="text/javascript">
			function checkLogout() {
				if(confirm("로그아웃 하시겠습니까?")) {
					 location.href="/member/logout.do";
				}
			}
		</script>
	</body>
</html>