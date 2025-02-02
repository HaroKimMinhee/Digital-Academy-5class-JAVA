//sql 연동

package Onedaypichi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Onedaypichisql {
	 private static final String URL = "jdbc:mysql://localhost:3306/Onedaypichi"; // 데이터베이스 URL
	    private static final String USER = "root"; // 사용자 이름
	    private static final String PASSWORD = "password"; // 비밀번호

	public static void main(String[] args) {
	    try {
            // JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 데이터베이스 연결
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("데이터베이스에 연결되었습니다.");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("데이터베이스 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}