package app;

import java.util.*;
import model.User;
import service.UserService;
import service.PostService;
import session.Session;
import util.InputHandler;

public class App {
    private static final String LOGIN_COMMAND = "login";
    private static final String REGISTER_COMMAND = "register";
    private static final String DELETE_COMMAND = "delete";
    private static final String LS_USER_COMMAND = "ls";
    private static final String START_MENU_HELP_COMMAND = "help";
    private static final String LOGOUT_COMMAND = "logout";
    private static final String LS_POST_COMMAND = "ls";
    private static final String POST_COMMAND = "post";
    private static final String LS_MY_POST_COMMAND = "lsmyposts";
    private static final String LS_MY_INFO_COMMAND = "lsmyinfo";
    private static final String USER_MENU_HELP_COMMAND = "help";
    private static final String EXIT_COMMAND = "exit";

    public static void run() {
        // welcome message
        InputHandler.formatKeyword("掲示板アプリ");

        Session session = new Session();
        while(true) {
            if (!session.get_login_status()) {
                String command = startMenu();
                switch (command) {
                    case LOGIN_COMMAND:
                        UserService.login(session);
                        break;
                    case REGISTER_COMMAND:
                        UserService.register();
                        break;
                    case LS_USER_COMMAND:
                        UserService.showAllUser();
                        break;
                    case DELETE_COMMAND:
                        UserService.delete();
                        break;
                    case START_MENU_HELP_COMMAND:
                        helpStartMenu();
                        break;
                    case EXIT_COMMAND:
                        exit();
                        break;
                    default:
                        InputHandler.forbiddenString(command);
                }
            } else {
                String command = userMenu();
                switch (command) {
                    case LS_POST_COMMAND:
                        PostService.lsAllPost();
                        PostService.lsPost();
                        break;
                    case POST_COMMAND:
                        PostService.post(session.get_user());
                        break;
                    case LS_MY_POST_COMMAND:
                        PostService.myPost(session.get_user());
                        break;
                    case LS_MY_INFO_COMMAND:
                        UserService.userInfo(session.get_user());
                        break;
                    case USER_MENU_HELP_COMMAND:
                        helpUserMenu();
                        break;
                    case LOGOUT_COMMAND:
                        UserService.logout(session);
                        break;
                    case EXIT_COMMAND:
                        exit();
                        break;
                    default:
                        InputHandler.forbiddenString(command);
                }
            }
        System.out.println();
        }
    }

    private static String startMenu() {
        InputHandler.formatKeyword("掲示板:スタートメニュー");
        String command = InputHandler.inputString("コマンドを入力 ("+LOGIN_COMMAND+", "+REGISTER_COMMAND+", "+LS_USER_COMMAND+", "+DELETE_COMMAND+", "+START_MENU_HELP_COMMAND+", "+EXIT_COMMAND+")");
        return command;
    }

    private static void helpStartMenu() {
        InputHandler.formatKeyword("掲示板:ヘルプ(スタートメニュー)");
        System.out.println(LOGIN_COMMAND + ": ログイン");
        System.out.println(REGISTER_COMMAND + ": ユーザ作成");
        System.out.println(LS_USER_COMMAND + ": ユーザ一覧表示");
        System.out.println(START_MENU_HELP_COMMAND + ": ヘルプ(スタートメニュー)");
        System.out.println(EXIT_COMMAND + ": プログラム終了");
    }

    private static String userMenu() {
        InputHandler.formatKeyword("掲示板:マイメニュー");
        String command = InputHandler.inputString("コマンドを入力 ("+LOGOUT_COMMAND+", "+LS_POST_COMMAND+", "+POST_COMMAND+", "+LS_MY_POST_COMMAND+", "+LS_MY_INFO_COMMAND+", "+USER_MENU_HELP_COMMAND+", "+EXIT_COMMAND+")");
        return command;
    }

    private static void helpUserMenu() {
        InputHandler.formatKeyword("掲示板:ヘルプ(マイメニュー)");
        System.out.println(LS_POST_COMMAND + ": 投稿一覧表示");
        System.out.println(POST_COMMAND + ": 新規投稿");
        System.out.println(LS_MY_POST_COMMAND + ": 自分の投稿一覧表示");
        System.out.println(LS_MY_INFO_COMMAND + ": ユーザ情報表示");
        System.out.println(USER_MENU_HELP_COMMAND + ": ヘルプ(マイメニュー)");
        System.out.println(LOGOUT_COMMAND + ": ログアウト");
        System.out.println(EXIT_COMMAND + ": プログラム終了");
    }

    private static void exit() {
        InputHandler.formatKeyword("掲示板:プログラム終了");
        String command = InputHandler.inputString("プログラムを終了しますか？(y/n)");
        if (command.equals("y")) {
            System.exit(0);
        }
    }
}
