package service;

import java.util.*;
import org.mindrot.jbcrypt.BCrypt;
import model.User;
import session.Session;
import dao.UserDAO;
import util.InputHandler;

public class UserService {
    // ユーザ作成
    public static void register() {
        InputHandler.formatKeyword("ユーザ作成");

        String username = InputHandler.inputString("ユーザ名");
        if (!UserDAO.checkUser(username)) {
            String password = InputHandler.inputString("パスワード");
            if (password.isEmpty()) { return; }
            UserDAO.registerUser(username, BCrypt.hashpw(password, BCrypt.gensalt()));
            System.out.println("ユーザを作成されました。");
        }
    }

    // ユーザ削除
    public static void delete() {
        InputHandler.formatKeyword("ユーザ削除");
        
        while (true) {
            String username = InputHandler.inputString("ユーザ名");
            if (username.isEmpty()) { return; }
            if (UserDAO.checkUser(username)) {
                String password = InputHandler.inputString("削除するユーザのパスワード");
                if (password.isEmpty()) { return; }
                User user = UserDAO.getUser(username);
                if (BCrypt.checkpw(password, user.getCryptedPassword())) {
                    System.out.println("次のユーザを削除します: " + username);
                    if (InputHandler.inputString("本当によろしいですか？(y/n)").equals("y")) {
                        UserDAO.deleteUser(user.getId());
                        System.out.println("ユーザを削除されました。");
                        return;
                    }
                } else {
                    System.out.println("パスワードが違います。");
                }
            } else {
                System.out.println("ユーザが存在しません。");
            }
        }
    }

    // ユーザ一覧表示
    public static void showAllUser() {
        InputHandler.formatKeyword("ユーザ一覧");

        ArrayList<User> users = UserDAO.getAllUser();
        for (User user :users) {
            System.out.println("ユーザ名: " + user.getName());
            System.out.println("プロフィール: " + user.getProfile());
        }
    }

    // ログイン処理
    public static void login(Session session) {
        InputHandler.formatKeyword("ログイン");
        String username = InputHandler.inputString("ユーザ名");
        if (username.isEmpty()) { return; }
        String password = InputHandler.inputString("パスワード");
        if (password.isEmpty()) { return; }
        User user = UserDAO.getUser(username);
        if (BCrypt.checkpw(password, user.getCryptedPassword())) {
            session.login(user);
            System.out.println(username + "でログインしました。");
            return;
        } else {
            System.out.println("ユーザ名またはパスワードが異なります。");
        }
    }

    // ログアウト処理
    public static void logout(Session session) {
        InputHandler.formatKeyword("ログアウト");
        String command = InputHandler.inputString("ログアウトしますか？(y/n)");
        if (command.equals("y")) {
            session.logout();
            System.out.println("ログアウトしました。");
        }
    }

    // プロフィール表示
    public static void showProfile() {
        ArrayList<User> users = UserDAO.getAllUser();
        
        while (true) {
            String username = InputHandler.inputString("プロフィールを表示する投稿者名");
            for (User user :users) {
                if (user.getName().equals(username)) {
                    System.out.println("ユーザ名: " + user.getName());
                    System.out.println("プロフィール: " + user.getProfile());
                    return;
                }
            }
            System.out.println("入力された投稿者は存在しません。");
        }
    }

    // ユーザ情報表示
    public static void userInfo(User user) {
        final String EDITPASS_COMMAND = "editpass";
        final String EDITPROFILE_COMMAND = "editprofile";

        while (true) {
            showUserInfo(user);
            String input_command = InputHandler.inputString("コマンドを入力("+EDITPASS_COMMAND+","+EDITPROFILE_COMMAND+")");
            if (input_command.isEmpty()) { return; }
            switch (input_command) {
                case EDITPASS_COMMAND:
                    editPassword(user);
                    break;
                case EDITPROFILE_COMMAND:
                    editProfile(user);
                    break;
                default:
                    InputHandler.forbiddenString(input_command);
            }
        }
    }

    public static void showUserInfo(User user) {
        InputHandler.formatKeyword("自分の情報表示");

        System.out.println("項目: 値");
        System.out.println("プロフィール: " + user.getProfile());
    }

    public static void editPassword(User user) {
        InputHandler.formatKeyword("パスワード変更");

        while (true) {
            String password = InputHandler.inputString("現在のパスワードを入力");
            if (password.isEmpty()) { return; }
            if (BCrypt.checkpw(password, user.getCryptedPassword())) {
                String new_password = InputHandler.inputString("新しいパスワードを入力");
                if (new_password.isEmpty()) { return; }
                UserDAO.updatePassword(user, BCrypt.hashpw(new_password, BCrypt.gensalt()));
                System.out.println("パスワードを変更されました。");
                return;
            } else {
                System.out.println("パスワードが違います。");
            }
        }
    }

    public static void editProfile(User user) {
        InputHandler.formatKeyword("プロフィール変更");

        System.out.println("現在のプロフィール: " + user.getProfile());
        String new_profile = InputHandler.inputString("新しいプロフィールを入力");
        if (new_profile.isEmpty()) { return; }

        String password = InputHandler.inputString("パスワードを入力");
        if (password.isEmpty()) { return; }
        if (BCrypt.checkpw(password, user.getCryptedPassword())) {
            UserDAO.updateProfile(user, new_profile);
            System.out.println("プロフィールが変更されました。");
            return;
        } else {
            System.out.println("パスワードが違います。");
        }
    }
}
