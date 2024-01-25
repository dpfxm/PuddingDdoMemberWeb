package member.controller;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.MemberService;

/**
 * Servlet implementation class deleteController
 */
@WebServlet("/member/delete.do")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			String memberId = request.getParameter("memberId");
			MemberService mService = new MemberService();
			int result = mService.deleteMember(memberId);
			if (result > 0) {
				// 탈퇴 성공 -> 로그아웃 해주기
				response.sendRedirect("/member/logout.do");
			} else {
				// 실패
				request.setAttribute("msg", "No Data Found");
				request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp")
				.forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp")
			.forward(request, response);
		}
	}

}
