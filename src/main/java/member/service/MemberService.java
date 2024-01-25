package member.service;

import java.sql.*;

import common.JDBCTemplate;
import member.model.dao.MemberDAO;
import member.model.vo.Member;

public class MemberService {

	// 연결과 데이터를 넘겨주면 쿼리를 실행
	private MemberDAO mDao;
	private Connection conn;

	public MemberService() {
		// mDao는 멤버변수로 그 초기화는 생성자에서 해줌
		mDao = new MemberDAO();
		/*
		 * conn은 멤버 변수라고 하는데 그 초기화는 생성자에서 해줌
		 * getConnection 메소드는 static 메소드라 객체 생성 없이도 호출이 가능
		 * 싱글 톤이 적용되어 conn은 한 번만 생성됨
		 * DBMS 연결 작업은 무거운 연결 작업이므로 최소한으로 하는 것이
		 * 부하가 적게 걸림
		 */
		conn = JDBCTemplate.getConnection();
	}
	
	/*
	 * selectLoginCheck 메소드는 member 객체를 전달받아서
	 * DAO에 전달해 주는 역할
	 * 서비스에서는 DBMS 연결을 만들어서 DAO에 전달
	 * 마지막으로 쿼리 성공 여부에 따라 commit/rollback을 함(DML 경우에만)
	 * DB에서 쿼리문을 실행해서 결과값을 member로 받기 위해
	 * MemberDAO와 관계를 맺고 메소드를 호출해야 함
	 */
	public Member selectLoginCheck(Member member) {
		Member mOne = null;
		/*
		 * conn은 이미 전에 선언해서 연결을 만들어 놨기 때문에 메소드만 DAO에 자동 생성
		 * MemberDAO의 selectLoginCheck() 메소드는 SQLException을 throw
		 * 그래서 호출하는 이곳에서 예외를 처리해줘야하는데
		 * try ~ catch는 이미 controller에서 하고 있기 때문에 service에서도 해당 예외를 throw 해줌
		 * DAO에서 넘어온 예외가 Service에 왔다가 Service에서는 다시 Controller로 넘어감
		 */
		try {
			mOne = mDao.selectLoginCheck(conn, member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mOne;
	}

	public Member selectOneById(String memberId) throws SQLException {
		/*
		 * selectOneById 메소드는 memberId를 전달 받아서 DAO에 전달
		 * 서비스에서는 DBMS 연결을 만들어서 DAO 전달
		 * DB에서 쿼리문을 실행해서 결과값을 member로 받기 위해
		 * MemberDAO와 관계를 맺고 메소드를 호출해야 함
		 */
		Member member = mDao.selectOneById(conn, memberId);
		return member;
	}

	public int insertMember(Member member) throws SQLException {
		// 쿼리문의 성공 여부는 숫자 result에 반환
		int result = mDao.insertMember(conn, member);
		if (result > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}
		return result;
	}

	public int updateMember(Member member) throws SQLException {
		int result = mDao.updateMember(conn, member);
		if (result > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}
		return result;
	}

	public int deleteMember(String memberId) throws SQLException {
		int result = mDao.deleteMember(conn, memberId);
		if (result > 0) {
			conn.commit();
		} else {
			conn.rollback();
		}
		return result;
	}

}
