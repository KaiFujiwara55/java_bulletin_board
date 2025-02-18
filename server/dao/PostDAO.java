package dao;

import java.util.*;
import java.sql.*;
import model.User;
import model.Post;
import util.DBconnection;

public class PostDAO {
    // 投稿を取得するメソッド
    public static ArrayList<Post> getAllPost() {
        Connection conn = DBconnection.getConn();
        
        String sql = "SELECT posts.id, users.name, posts.content, like_count.count  FROM posts\n" + 
        "LEFT JOIN users ON posts.user_id = users.id\n" +
        "LEFT JOIN like_counts ON posts.id = like_count.post_id\n" +
        "WHERE posts.is_deleted = 0";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<Post> posts = new ArrayList<Post>();
            while (rs.next()) {
                Post post = new Post(rs.getInt("id"), rs.getString("name"), rs.getString("content"), rs.getInt("like_count"));
                posts.add(post);
            }
            DBconnection.closeConn(conn);
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
            return null;
        }
    }

    // 投稿を登録するメソッド
    public static void postContent(User user, String content) {
        Connection conn = DBconnection.getConn();
        
        String sql = "INSERT INTO posts (user_id, content) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "INSERT INTO like_count (post_id) VALUES (?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.executeUpdate();
            DBconnection.closeConn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
        }
    }

    // 投稿を論理削除するメソッド
    public static void deletePost(Post post) {
        Connection conn = DBconnection.getConn();
        
        String sql = "UPDATE posts SET is_deleted = 1 WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, post.getId());
            pstmt.executeUpdate();
            DBconnection.closeConn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
        }
    }

    // 投稿をいいねするメソッド
    public static void likePost(Post post) {
        Connection conn = DBconnection.getConn();
        
        String sql = "UPDATE like_count SET count = count + 1 WHERE post_id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, post.getId());
            pstmt.executeUpdate();
            DBconnection.closeConn(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            DBconnection.closeConn(conn);
        }
    }
}
