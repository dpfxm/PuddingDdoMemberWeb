package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import member.model.vo.Member;

public class MemberDAO {
	// conn 연결도 이미 되어 있고, member 데이터도 있기 때문에 쿼리만 실행하면 됨
	public int insertMember(Connection conn, Member member) throws SQLException {
		String query = "INSERT INTO MEMBER_TBL VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT, 'Y')";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, member.getMemberId());
		pstmt.setString(2, member.getMemberPw());
		pstmt.setString(3, member.getMemberName());
		pstmt.setInt(4, member.getMemberAge());
		pstmt.setString(5, member.getMemberGender());
		pstmt.setString(6, member.getMemberEmail());
		pstmt.setString(7, member.getMemberPhone());
		pstmt.setString(8, member.getMemberAddress());
		pstmt.setString(9, member.getMemberHobby());
		int result = pstmt.executeUpdate();
		pstmt.close();
		return result;
	}
	
	public int updateMember(Connection conn, Member member) throws SQLException {
		String query = "UPDATE MEMBER_TBL SET MEMBER_PW = ?, MEMBER_EMAIL = ?, MEMBER_PHONE = ?, MEMBER_ADDRESS = ?, MEMBER_HOBBY = ? WHERE MEMBER_ID = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, member.getMemberPw());
		pstmt.setString(2, member.getMemberEmail());
		pstmt.setString(3, member.getMemberPhone());
		pstmt.setString(4, member.getMemberAddress());
		pstmt.setString(5, member.getMemberHobby());
		pstmt.setString(6, member.getMemberId());
		int result = pstmt.executeUpdate();
		pstmt.close();
		return result;
	}

	public int deleteMember(Connection conn, String memberId) throws SQLException {
		String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, memberId);
		int result = pstmt.executeUpdate();
		pstmt.close();
		return result;
	}

	public Member selectLoginCheck(Connection conn, Member member) throws SQLException {
		/*
		 * ID와 PW를 입력 받았고 해당 데이터가 있는지 확인하는
		 * query 문을 작성해줌(SELECT 사용)
		 * 물음표 = 위치홀더, member 데이터를 넣는 부분
		 * ID와 PW 두 개를 이용하여 SELECT을 할 수 있도록 WHERE 조건절 이용
		 */
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?";
		/*
		 * prepareStatement() 메소드를 통해서 pstmt 객체를 만들 때에는 쿼리문이 필요함
		 * prepareStatement() 메소드는  Checked Exception으로 반드시 예외처리를 해줘야 함
		 * 예외처리 방법은 2가지로
		 * 첫 번째, throw를 호출하여 그 곳에서 처리하도록 하는 방법
		 * 두 번째, try ~ catch를 사용해서 그 자리에서 처리하는 방법
		 * 여기서는 throw의 SQLException 사용
		 */
		PreparedStatement pstmt = conn.prepareStatement(query);
		/*
		 * 위치호덜가 2개이고 여기에 값을 세팅해주어야 입력한 값이
		 * WHERE 조건절에 들어가서 쿼리문이 실행
		 * 첫 번째 위치홀더는 ID이고 member에서 getter 메소드를 꺼내 세팅
		 * 두 번째 위치홀더는 PW이고 member에서 getter 메소드를 꺼내 세팅
		 * 메소드를 사용해야 하고 전달값을 전달하지 않음
		 * 실행한 결과값은 ResultSet Interface를 통해서 받아줌
		 * rSet은 그대로 못쓰고 rSet을 member로 바꿔주는 작업이 필요한데
		 * 이는 반복되기 때문에 모듈화를 통해 재사용 가능 (public void rSetToMember(Resultset rSet) 이용
		 */
		pstmt.setString(1, member.getMemberId());
		pstmt.setString(2, member.getMemberPw());
		/*
		 * rSet을 통해서 Member를 리턴해야 하기 때문에 void -> Member로
		 * 바꿔주고 이러한 리턴타입을 Member로 바꾼다고 지칭
		 * rSet에 객체가 담기면 next() 메소드를 통해서 다음 데이터가 있는지 체크
		 * 다음 데이터가 있으면 true, 없으면 false를 리턴
		 * 1개만 가져오면 if, 여러 개를 가져올 경우에는 while문 사용
		 * rSetToMember() 메소드가 리턴하는 멤버 인스턴스를 받기 위해
		 * Member 타입의 mOne 변수를 선언하고 null로 초기화
		 * null로 초기화를 해놓으면 데이터가 없을 때 null이 리턴되어서 null 체크를 할 수 있음
		 * 다 끝난 후에는 자원 해체를 해줌(순서 상관 없음)
		 */
		ResultSet rSet = pstmt.executeQuery();
		Member mOne = null;
		if (rSet.next()) {
			mOne = this.rSetToMember(rSet);
		}
		rSet.close();
		pstmt.close();
		return mOne;
	}
	
	public Member selectOneById(Connection conn, String memberId) throws SQLException {
		/*
		 * DML(INSERT, UPDATE, DELETE)의 경우에는 executeUpdate() 사용
		 * 쿼리문이 성공해서 데이터가 있으면 rSetToMember()
		 * 메소드로 rSet을 member 객체로 변환해줌
		 * 변환하는 이유 : Oracle 시스템, Java 시스템처럼 다른 시스템들이 만나서
		 * 데이터를 주고 받기 때문
		 * 자원해재 한 후, member 리턴
		 */
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, memberId);
		Member member = null;
		ResultSet rSet = pstmt.executeQuery();
		if (rSet.next()) {
			member = this.rSetToMember(rSet);
		}
		rSet.close();
		pstmt.close();
		return member;
	}

	public Member rSetToMember(ResultSet rSet) throws SQLException {
		/*
		 * rSet에 있는 값을 담을 member 객체를 선언해주고 rSet.getStiring()등의 메소드를 이용하여
		 * member에 값을 넣어줌. 값을 넣을 땐 setter 메소드를 이용하여 member에 값을 넣어줌
		 * rSEt.getString() 메소드는 전달값으로 컬러명을 적어줘야하는데 이 때 오타 조심
		 * getString 메소드는 CheckedException으로 예외처리 필수
		 * rSet에서 getChar은 없기 때문에 charAt(0)
		 */
		Member member = new Member();
		member.setMemberId(rSet.getString("MEMBER_ID"));
		member.setMemberPw(rSet.getString("MEMBER_PW"));
		member.setMemberName(rSet.getString("MEMBER_NAME"));
		member.setMemberGender(rSet.getString("MEMBER_GENDER"));
		member.setMemberAge(rSet.getInt("MEMBER_AGE"));
		member.setMemberEmail(rSet.getString("MEMBER_EMAIL"));
		member.setMemberPhone(rSet.getString("MEMBER_PHONE"));
		member.setMemberAddress(rSet.getString("MEMBER_ADDRESS"));
		member.setMemberHobby(rSet.getString("MEMBER_HOBBY"));
		member.setMemberDate(rSet.getDate("MEMBER_DATE"));
		member.setUpdateDate(rSet.getTimestamp("UPDATE_DATE"));
		member.setMemberYn(rSet.getString("MEMBER_YN"));
		return member;
		
	}
	
}
