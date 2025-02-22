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
        // 投稿処理
        final String FILTER_COMMAND = "filter";
        final String LIKE_COMMAND = "like";
        final String SHOW_PROFILE_COMMAND = "profile";
        
        String command = InputHandler.inputString("コマンドを入力("+FILTER_COMMAND+", "+LIKE_COMMAND+", "+SHOW_PROFILE_COMMAND+")");
        if (command.isEmpty()) { return; }
        switch (command) {
            case FILTER_COMMAND:
                filterPost();
                break;
            case LIKE_COMMAND:
                likePost();
                break;
            case SHOW_PROFILE_COMMAND:
                UserService.showProfile();
                break;
            default:
                InputHandler.forbiddenString(command);
        }
        lsPost();
    }

    // 投稿一覧
    public static void lsAllPost() {
        InputHandler.formatKeyword("投稿一覧");

        ArrayList<Post> posts = PostDAO.getAllPost();
        for (Post post : posts) {
            System.out.println("投稿番号: " + post.getId());
            System.out.println("投稿者名: " + post.getUserName());
            System.out.println("投稿内容: " + post.getContent());
            System.out.println("いいね: " + post.getLikeCount());
        }
    }

    // フィルタ処理
    public static void filterPost() {
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
                InputHandler.forbiddenString(command);
                filterPost();
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

    // いいね処理
    public static void likePost() {
        ArrayList<Post> posts = PostDAO.getAllPost();

        while (true) {
            String postId = InputHandler.inputString("いいねする投稿番号");
            if (postId.isEmpty()) { return; }
            try {
                Integer.parseInt(postId);
            } catch (NumberFormatException e) {
                System.out.println("投稿番号は数値で入力してください。");
                continue;
            }

            for (Post post : posts) {
                if (post.getId() == Integer.parseInt(postId)) {
                    PostDAO.likePost(post);
                    System.out.println("いいねしました。");
                    return;
                }
            }
            System.out.println("存在する投稿番号を入力してください。");
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
