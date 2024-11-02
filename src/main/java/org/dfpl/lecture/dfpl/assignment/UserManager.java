package org.dfpl.lecture.dfpl.assignment;

import java.sql.*;

public class UserManager
{
    private String dbUser = "root";
    private String dbPassword = "1234";
    private String dbUrl = "jdbc:mariadb://localhost:3306/Computer_Network_DB";

    // 사용자 등록
    public void registerUser(String email, String password, String name) throws SQLException
    {
        String query = "INSERT INTO User (UserEmail, UserPassword, UserName) VALUES (?, ?, ?)";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, email);
        pstmt.setString(2, password); // 비밀번호는 해시화해서 저장하는 것이 좋습니다.
        pstmt.setString(3, name);
        pstmt.executeUpdate();
        System.out.println("User registered successfully!");
    }

    // 사용자 로그인
    public boolean loginUser(String email, String password) throws SQLException
    {
        String query = "SELECT UserID FROM User WHERE UserEmail = ? AND UserPassword = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement pstmt = connection.prepareStatement(query);

        pstmt.setString(1, email);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
        {
            System.out.println("Login successful!");
            return true; // 로그인 성공
        } else
        {
            System.out.println("Invalid email or password.");
            return false; // 로그인 실패
        }
    }

    // 사용자 정보 조회
    public void getUserInfo(int userId) throws SQLException
    {
        String query = "SELECT * FROM User WHERE UserID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement pstmt = connection.prepareStatement(query);

        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
        {
            System.out.println("UserID: " + rs.getInt("UserID"));
            System.out.println("Email: " + rs.getString("UserEmail"));
            System.out.println("Name: " + rs.getString("UserName"));
        } else
        {
            System.out.println("User not found.");
        }
    }

    // 사용자 정보 업데이트
    public void updateUser(int userId, String email, String password, String name) throws SQLException
    {
        String query = "UPDATE User SET UserEmail = ?, UserPassword = ?, UserName = ? WHERE UserID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        pstmt.setString(3, name);
        pstmt.setInt(4, userId);
        pstmt.executeUpdate();
        System.out.println("User updated successfully!");
    }

    // 사용자 삭제
    public void deleteUser(int userId) throws SQLException
    {
        String query = "DELETE FROM User WHERE UserID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();
        System.out.println("User deleted successfully!");
    }
}
