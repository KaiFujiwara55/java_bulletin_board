package dao;

import java.util.*;
import java.sql.*;
import model.User;
import util.DBconnection;

public class UserDAO {
    // ユーザー情報を取得するメソッド
    public static ArrayList<User> getAllUser() {
        Connection conn = DBconnection.getConn();
        
        String sql = "SELECT * FROM users WHERE is_deleted = 0";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("crypted_password"), rs.getString("profile"));
                users.add(user);
            }
            DBconnection.closeConn(conn);
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
            return null;
        }
    }

    // 特定のユーザー情報を取得するメソッド
    public static User getUser(String name, String crypted_password) {
        Connection conn = DBconnection.getConn();
        
        String sql = "SELECT * FROM users WHERE name = ? AND crypted_password = ? AND is_deleted = 0";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, crypted_password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("crypted_password"), rs.getString("profile"));
                DBconnection.closeConn(conn);
                return user;
            } else {
                DBconnection.closeConn(conn);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
            return null;
        }
    }

    // ユーザ登録するメソッド
    public static void registerUser(String name, String crypted_password) {
        Connection conn = DBconnection.getConn();
        
        String sql = "INSERT INTO users (name, crypted_password) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, crypted_password);
            pstmt.executeUpdate();
            DBconnection.closeConn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
        }
    }

    // ユーザが存在するか確認するメソッド
    public static boolean checkUser(String name) {
        Connection conn = DBconnection.getConn();
        
        String sql = "SELECT COUNT(*) FROM users WHERE name = ? AND is_deleted = 0";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
            return false;
        }
    }

    //  パスワードが正しいか確認するメソッド
    public static boolean checkPassword(String name, String crypted_password) {
        Connection conn = DBconnection.getConn();
        
        String sql = "SELECT COUNT(*) FROM users WHERE name = ? AND crypted_password = ? AND is_deleted = 0";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, crypted_password);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
            return false;
        }
    }

    //　ユーザを論理削除するメソッド
    public static void deleteUser(String name, String crypted_password) {
        Connection conn = DBconnection.getConn();
        
        String sql = "UPDATE users SET is_deleted = 1 WHERE name = ? AND crypted_password = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, crypted_password);
            pstmt.executeUpdate();
            DBconnection.closeConn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
        }
    }

    // パスワードを更新するメソッド
    public static void updatePassword(User user, String new_crypted_password) {
        Connection conn = DBconnection.getConn();
        
        String sql = "UPDATE users SET crypted_password = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, new_crypted_password);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();
            DBconnection.closeConn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
        }
    }

    // プロフィールを更新するメソッド
    public static void updateProfile(User user, String new_profile) {
        Connection conn = DBconnection.getConn();
        
        String sql = "UPDATE users SET profile = ? WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, new_profile);
            pstmt.setInt(2, user.getId());
            pstmt.executeUpdate();
            DBconnection.closeConn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
        }
    }
}
