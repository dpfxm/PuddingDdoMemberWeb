package member.controller;

import java.io.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.vo.Member;
import member.service.MemberService;

/**
 * Servlet implementation class ModifyController
 */
@WebServlet("/member/modify.do")
public class ModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String memberId = request.getParameter("memberId");
			MemberService mService = new MemberService();
			Member member = mService.selectOneById(memberId);
			if (member != null) {
				request.setAttribute("member", member);
				request.getRequestDispatcher("/WEB-INF/views/member/modify.jsp").forward(request, response);
			} else {
				request.setAttribute("msg", "No Data Found");
				request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			/*
			 * e.printStackTrace(); -> 콘솔 창에 빨간 글씨로 오류 출력해 주는 메소드
			 * 오류 메시지를 오류 페이지를 통해서 볼 수 있도록 
			 * request에 setAttribute하고 errorPage.jsp에서는 ${msg }로 사용
			 */
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// UPDATE MEMBER_TBL SET MEMBER_PW = ?, MEMBER_EMAIL = ?, MEMBER_PHONE = ?,
		// MEMBER_ADDRESS = ?, MEMBER_HOBBY = ? WHERE MEMBER_ID = ?
		try {
			String memberId = request.getParameter("member-id");
			String memberPw = request.getParameter("member-pw");
			String memberEmail = request.getParameter("member-email");
			String memberPhone = request.getParameter("member-phone");
			String memberAddress = request.getParameter("member-address");
			String memberHobby = request.getParameter("member-hobby");
			Member member = new Member(memberId, memberPw, memberEmail, memberPhone, memberAddress, memberHobby);
			MemberService mService = new MemberService();
			int result = mService.updateMember(member);
			if (result > 0) {
				// 마이페이지 이동
				response.sendRedirect("/member/myPage.do?memberId=" + memberId);
			} else {
				// 에러페이지 이동
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp");
				view.forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(request, response);
		}

	}

}
