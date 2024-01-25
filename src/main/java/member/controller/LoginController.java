package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;
import member.service.MemberService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/member/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 오류가 나도 오류 메시지가 없는걸 보완하기 위해
		 * 예외가 발생하면 오류메시지를 errorPage.jsp에 출력
		 * try ~ catch 문으로 감싼 후, 메세지를 보내기
		 * try ~catch 준비, 직접 쓰거나 ctrl + space로 자동완성도 가능
		 * 작성한 코드를 모두 try 문에 넣음
		 * 컨트롤러는 jsp와 연관이 깊고 input 태그의 name 값이 키값이 되어
		 * 사용자가 입력한 값을 request를 통해 가져올 수 있음
		 * */
		try {
			String memberId = request.getParameter("memberId");
			String memberPw = request.getParameter("memberPw");
			// 입력한 값으로 데이터가 존재하면 member != null, 없으면 == null
			Member member = new Member(memberId, memberPw);
			MemberService mService = new MemberService();
			Member mOne = mService.selectLoginCheck(member);
			
			if (mOne != null) {
				/*
				 * 성공하면 메인페이지로 이동해야하지만 로그인 성공한 정보를 session에 저장해주어야 함
				 * 그래서 request에서 세션을 가져와 세션에서 민감하지 않은 정보 몇 가지만 적어줌
				 * 주로 Id 값이나 no값을 저장함
				 * session에는 setAttribute()메소드를 이용해서 저장
				 * 저장할 수 있는 값이 여러 개니까 키값으로 구분해둠
				 * 해당 키값은 jsp에서 사용되어 value 값을 가져올 때 필요함
				 * memberId라는 키값으로 로그인한 아이디 값을 세션에,
				 * memberName이라는 키값으로 로그인한 이름 정보를 세션에 넣어둠
				 * 키 값은 jsp에서 사용되기 때문에 이를 사용하려면 index.jsp로 이동
				 */
				HttpSession session = request.getSession();
				session.setAttribute("memberId", mOne.getMemberId());
				session.setAttribute("memberName", mOne.getMemberName());
				response.sendRedirect("/member/myPage.do?memberId=" + memberId);
			} else {
				request.setAttribute("msg", "No Data Found!");
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp");
				view.forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp");
			view.forward(request, response);
		}
		
	}

}
