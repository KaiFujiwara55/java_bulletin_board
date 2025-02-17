package util;

import java.sql.*;

public class DBconnection {
    // 接続用connを取得
    public static Connection getConn() {
        try {
            Connection conn = null;
            // MySQL JDBC ドライバをロード
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // データベース接続のコード
            conn = DriverManager.getConnection("jdbc:mysql://mysql:3306/bulletin_board", "user", "password");
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // クラスが見つからなかった場合のエラーハンドリング
        } catch (SQLException e) {
            e.printStackTrace(); // SQLエラーの処理
        }
        return null;
    }

    // 接続用connを閉じる
    public static void closeConn(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
