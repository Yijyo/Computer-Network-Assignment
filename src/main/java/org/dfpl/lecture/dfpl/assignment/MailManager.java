package org.dfpl.lecture.dfpl.assignment;

import java.sql.*;

public class MailManager
{
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/Computer_Network_DB";

    // 송신할 메일 정보를 데이터베이스에 저장
    public static void saveSentMail(int senderId, String recipient, String subject, String body) throws SQLException
    {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmtMailbox = connection.prepareStatement("INSERT INTO Mailbox (SenderID, Subject) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        PreparedStatement pstmtContent = connection.prepareStatement("INSERT INTO Mail_Content (MailID, Body) VALUES (?, ?)");

        // Mailbox에 메일 정보 삽입
        pstmtMailbox.setInt(1, senderId);
        pstmtMailbox.setString(2, subject);
        pstmtMailbox.executeUpdate();

        // 방금 삽입한 MailID 가져오기
        ResultSet generatedKeys = pstmtMailbox.getGeneratedKeys();
        if (generatedKeys.next())
        {
            int mailId = generatedKeys.getInt(1);
            // Mail_Content에 본문 삽입
            pstmtContent.setInt(1, mailId);
            pstmtContent.setString(2, body);
            pstmtContent.executeUpdate();
        }

        System.out.println("Sent mail saved to database successfully!");
    }

    // 수신된 메일 정보를 데이터베이스에 저장
    public static void saveReceivedMail(int senderId, String subject, String body) throws SQLException
    {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement pstmtMailbox = connection.prepareStatement("INSERT INTO Mailbox (SenderID, Subject) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        PreparedStatement pstmtContent = connection.prepareStatement("INSERT INTO Mail_Content (MailID, Body) VALUES (?, ?)");


        // Mailbox에 메일 정보 삽입
        pstmtMailbox.setInt(1, senderId);
        pstmtMailbox.setString(2, subject);
        pstmtMailbox.executeUpdate();

        // 방금 삽입한 MailID 가져오기
        ResultSet generatedKeys = pstmtMailbox.getGeneratedKeys();
        if (generatedKeys.next())
        {
            int mailId = generatedKeys.getInt(1);
            // Mail_Content에 본문 삽입
            pstmtContent.setInt(1, mailId);
            pstmtContent.setString(2, body);
            pstmtContent.executeUpdate();
        }

        System.out.println("Received mail saved to database successfully!");
    }
}
