import java.sql.*;

public class ConnectDB {
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

    public static ResultSet executeCommand(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateCommand(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConn(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
