package service;

import java.util.*;
import model.User;
import model.Post;
import dao.PostDAO;
import util.InputHandler;

public class PostService {
    // 投稿
    public static void post(User user) {
        InputHandler.formatKeyword("新規投稿");

        String content = InputHandler.inputString("投稿内容");
        if (content.isEmpty()) { return; }
        PostDAO.postContent(user, content);
        System.out.println("投稿されました。");
    }

    // 投稿一覧表示
    public static void lsPost() {
        InputHandler.formatKeyword("投稿一覧");

        ArrayList<Post> posts = PostDAO.getAllPost();
        for (Post post : posts) {
            System.out.println("投稿番号: " + post.getId());
            System.out.println("投稿者名: " + post.getUserName());
            System.out.println("投稿内容: " + post.getContent());
            System.out.println("いいね: " + post.getLikeCount());
        }
    }

    // 投稿一覧フィルタ表示
    public static void fileterPost() {
        final String AUTHER_FILTER_COMMAND = "author";
        final String LIKE_FILTER_COMMAND = "like";
        final String CONTENT_FILTER_COMMAND = "content";

        String command = InputHandler.inputString("コマンドを入力("+AUTHER_FILTER_COMMAND+", "+LIKE_FILTER_COMMAND+", "+CONTENT_FILTER_COMMAND+")");
        if (command.isEmpty()) { return; }

        switch (command) {
            case AUTHER_FILTER_COMMAND:
                fileterPostByAuthor();
                break;
            case LIKE_FILTER_COMMAND:
                filterPostByLike();
                break;
            case CONTENT_FILTER_COMMAND:
                filterPostByContent();
                break;
            default:
                System.out.println("不正なコマンドです: " + command);
        }
    }

    // 投稿者フィルタ
    public static void fileterPostByAuthor() {
        String author = InputHandler.inputString("投稿者名");
        if (author.isEmpty()) { return; }

        ArrayList<Post> posts = PostDAO.getAllPost();
        for (Post post : posts) {
            if (post.getUserName().equals(author)) {
                System.out.println("投稿番号: " + post.getId());
                System.out.println("投稿者名: " + post.getUserName());
                System.out.println("投稿内容: " + post.getContent());
                System.out.println("いいね: " + post.getLikeCount());
            }
        }
    }

    // 投稿内容フィルタ
    public static void filterPostByContent() {
        String keyword = InputHandler.inputString("キーワード");
        if (keyword.isEmpty()) { return; }

        ArrayList<Post> posts = PostDAO.getAllPost();
        for (Post post : posts) {
            if (post.getContent().contains(keyword)) {
                System.out.println("投稿番号: " + post.getId());
                System.out.println("投稿者名: " + post.getUserName());
                System.out.println("投稿内容: " + post.getContent());
                System.out.println("いいね: " + post.getLikeCount());
            }
        }
    }

    // いいね数フィルタ
    public static void filterPostByLike() {
        int like = InputHandler.inputInt("最小いいね数");
        if (like < 0) { return; }

        ArrayList<Post> posts = PostDAO.getAllPost();
        for (Post post : posts) {
            if (post.getLikeCount() >= like) {
                System.out.println("投稿番号: " + post.getId());
                System.out.println("投稿者名: " + post.getUserName());
                System.out.println("投稿内容: " + post.getContent());
                System.out.println("いいね: " + post.getLikeCount());
            }
        }
    }

    // 自分の投稿一覧表示
    public static void myPost(User user) {
        InputHandler.formatKeyword("自分の投稿一覧表示");

        ArrayList<Post> posts = PostDAO.getAllPost();
        for (Post post : posts) {
            if (post.getUserName().equals(user.getName())) {
                System.out.println("投稿番号: " + post.getId());;
                System.out.println("投稿内容: " + post.getContent());
            }
        }
    }

    // 投稿削除
    public static void deletePost(User user) {
        int postId = InputHandler.inputInt("削除する投稿の投稿番号");
        if (postId < 0) {
            System.out.println("投稿番号は数値で入力してください。");
        }

        ArrayList<Post> posts = PostDAO.getAllPost();
        for (Post post : posts) {
            if (post.getId() == postId) {
                if (post.getUserName().equals(user.getName())) {
                    System.out.println("投稿番号: " + post.getId());
                    System.out.println("投稿内容: " + post.getContent());

                    String is_delete = InputHandler.inputString("この投稿を削除しますか？(y/n)");
                    if (is_delete.equals("y")) {
                        PostDAO.deletePost(post);
                        System.out.println("投稿が削除されました。");
                    }
                }
            } else {
                System.out.println("存在する自分の投稿番号を入力してください。");
            }
        }
    }
}
