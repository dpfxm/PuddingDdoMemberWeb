package member.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.vo.Member;
import member.service.MemberService;

/**
 * Servlet implementation class myPageController
 */
@WebServlet("/member/myPage.do")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyPageController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * myPage.jsp에서 출력해야 하는 정보는 로그인한 사람의 정보를 출력 
		 * 위치홀더(?)에 넘겨줄 값이 로그인한 사용자의 아이디여야 함 
		 * Controller에서 해당 값을 받아 Service로 넘겨주어야 DAO가 쿼리 실행 가능! 
		 * myPage를 호출하는 URL은 --/member/myPage.do?memberId=admin 같은 형태의 쿼리스트링 필요!
		 * 쿼리스트링으로 보낸 데이터를 받기 위해서는 
		 * request의 getParameter() 메소드 키값 memberId로 일치시키기
		 */
		try {
			// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? 
			String memberId = request.getParameter("memberId");
			// Service에 로그인한 사용자의 아이디를 넘겨주고 실행 결과로 member 객체 받음
			MemberService mService = new MemberService();
			Member member = mService.selectOneById(memberId);
			if (member != null) {
				request.setAttribute("member", member);
				// 변수 안 쓰고 이동할 페이지 지정 및 이동(forward() 호출)
				// 메소드 채이닝 방식, 점으로 계속 연결하기
				request.getRequestDispatcher("/WEB-INF/views/member/myPage.jsp").forward(request, response);
			} else {
				// 데이터가 존재하지 않을 경우
				request.setAttribute("msg", "No Data Found");
				request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			/*
			 * e.printStackTrace(); -> 콘솔창에 빨간 글씨로 오류 출력해주는 메소드 
			 * 오류 메시지를 오류 페이지를 통해서 볼 수 있도록
			 * request에 setAttribute하고 errorPage.jsp에서는 ${msg }로 사용
			 */
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(request, response);
		}
	}
}
