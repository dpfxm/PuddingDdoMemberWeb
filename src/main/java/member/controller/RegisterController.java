package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.vo.Member;
import member.service.MemberService;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/member/register.do")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * index.jsp에서 a 태그를 통해 get 방식으로!
		 * 호출하면 RegisterController()의 doGet() 메소드가 동작해서
		 * register.jsp를 보여주도록 함
		 * WEB-INF 아래 있는 jsp들은 RequestDispatcher를 통해서만 페이지 이동 가능
		 */
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/member/register.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		/*
		 * register.jsp 페이지를 보면 폼태그 action에 url(/member/register.do)라고 적혀있고 
		 * 폼태그 method 요청방식이 post라고 적혀있음 -> form 태그에 있는 
		 * submit 버튼(가입하기)을 누르면 RegisterController의 doPost() 메소드가 동작한다는 의미
		 * 그래서 지금 여기에서 입력받은 정보를 가지고 DB에 insert 할 수 있도록
		 * 코드를 작성해야 함(서버 내린 상태에서)
		 * Controller에서는 Service 클래스와 연관관계를 맺고 Service 메소드를 호출해야 함
		 * -> MemberService 타입의 mService 변수를 선언
		 * MemberService 생성자를 이용하여 객체를 생성 DML의 경우 성공하면 0보다 큰 수,
		 * 실패하면 아닌 수가 넘어오므로 int로 선언한 변수 필요
		 */
		try {
			// 코드 옮기기 -> try ~ catch 로 감싸주면 예외가 발생했을 때 예외 메시지를 가져올 수 있음
			String memberId = request.getParameter("member-id");
			String memberPw = request.getParameter("member-pw");
			String memberName = request.getParameter("member-name");
			String memberAge = request.getParameter("member-age");
			String memberGender = request.getParameter("member-gender");
			String memberEmail = request.getParameter("member-email");
			String memberPhone = request.getParameter("member-phone");
			String memberAddress = request.getParameter("member-address");
			String memberHobby = request.getParameter("member-hobby");
			/*
			 * form 태그에 입력한 데이터를 name에 적은 키값으로 매핑되어 쿼리스트링의 형태로 서버에 넘어오면
			 * Member 형의 member 변수에 입력한 값 모두 저장하고 생성자를 통해서 
			 * setter() 메소드 없이 초기화를 해줌 (Member(String, String, String, ...)과 같은 
			 * 매개변수 있는 생성자 만들기 필수) Member VO의 멤버변수 중 String이 아닌 것은 타입에 맞게
			 * 바꿔줘야 함 ex) memberGender.charAt(0), Integer.parseInt(MemberAge), ...
			 */
			Member member = new Member(memberId, memberPw, memberName, Integer.parseInt(memberAge), memberGender,
					memberEmail, memberPhone, memberAddress, memberHobby);
			System.out.println(member.toString());
			MemberService mService = new MemberService();
			// result는 성공하면 0보다 크고 아니면 그 반대
			int result = mService.insertMember(member);
			// 0보다 큰 수가 반환되는 이유는 쿼리문을 실행하고 성공한 행의 개수가 반환되기 때문
			if (result > 0) {
				// 성공하면 메인페이지
				response.sendRedirect("/index.jsp");
			}
		} catch (Exception e) {
			// 실패하면 errorPage, 작성 시 대소문자 오타 주의!
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp");
			view.forward(request, response);
		}
	}

}
