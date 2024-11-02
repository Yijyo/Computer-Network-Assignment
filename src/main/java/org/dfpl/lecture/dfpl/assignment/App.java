package org.dfpl.lecture.dfpl.assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class App
{
    // 본인 DB에 맞게 설정
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:mariadb://localhost:3306";

    public static void main(String[] args) throws Exception
    {
        // 데이터베이스와 테이블 생성
        createDatabaseAndTables();

        UserManager userManager = new UserManager();

        // 사용자 등록
        userManager.registerUser("test@example.com", "password123", "Test User");

        // 사용자 로그인
        boolean isLoggedIn = userManager.loginUser("test@example.com", "password123");

        // 사용자 정보 조회 및 업데이트
        if (isLoggedIn)
        {
            userManager.getUserInfo(1); // UserID 1의 정보 조회
            userManager.updateUser(1, "test@example.com", "newpassword123", "Updated User");
        }

        // 메일 송신
        MailManager.saveSentMail(1, "recipient@example.com", "Test Subject", "This is a test email body.");

        // 수신할 메일 저장
        MailManager.saveReceivedMail(1, "Received Subject", "This is a received email body.");
    }

    private static void createDatabaseAndTables() throws Exception
    {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement statement = connection.createStatement();

        // 데이터베이스 생성
        statement.executeUpdate("CREATE OR REPLACE DATABASE Computer_Network_DB");
        statement.executeUpdate("USE Computer_Network_DB");

        // User 테이블 생성
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS User (" +
                "UserID INT PRIMARY KEY AUTO_INCREMENT, " +
                "UserEmail VARCHAR(255) NOT NULL UNIQUE, " +
                "UserPassword VARCHAR(255) NOT NULL, " +
                "UserName VARCHAR(255) NOT NULL)");

        // Mailbox 테이블 생성
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Mailbox (" +
                "MailID INT AUTO_INCREMENT PRIMARY KEY, " +
                "SenderID INT, " +
                "Subject VARCHAR(255), " +
                "Received_At TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "IsRead BOOLEAN DEFAULT FALSE)");

        // Mail_Content 테이블 생성
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Mail_Content (" +
                "MailID INT PRIMARY KEY, " +
                "Body TEXT)");

        System.out.println("Database and tables created successfully!");
    }
}
