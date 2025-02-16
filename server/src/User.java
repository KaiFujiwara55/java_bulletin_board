import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class User {
    // コマンド一覧
    private static final String LOGIN_COMMAND = "login";
    private static final String REGISTER_COMMAND = "register";
    private static final String LS_COMMAND = "ls";
    private static final String DELETE_COMMAND = "delete";
    private static final String HELP_COMMAND = "help";
    private static final String EXIT_COMMAND = "exit";
    private static final String[] COMMANDS = {LOGIN_COMMAND, REGISTER_COMMAND, LS_COMMAND, DELETE_COMMAND, HELP_COMMAND, EXIT_COMMAND};

    // 入力受付用のScanner
    private static Scanner scanner = new Scanner(System.in);

    // welcomeメッセージを表示
    public static void welcome() {
        System.out.println("=== 掲示板アプリ ===\n");
    }

    // スタートメニューを表示
    public static void startMenu(){
        System.out.println("=== 掲示板：スタートメニュー ===\n");
        System.out.print("コマンドを入力 ("+LOGIN_COMMAND+", "+REGISTER_COMMAND+", "+LS_COMMAND+", "+DELETE_COMMAND+", "+HELP_COMMAND+", "+EXIT_COMMAND+"): ");
        
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        switch (command) {
            case LOGIN_COMMAND:
                login();
                break;
            case REGISTER_COMMAND:
                register();
                break;
            case LS_COMMAND:
                ls();
                break;
            case DELETE_COMMAND:
                delete();
                break;
            case HELP_COMMAND:
                help();
                break;
            case EXIT_COMMAND:
                exit();
                break;
            default:
                System.out.println("不正なコマンド: " + command);
                startMenu();
        }
    }

    
    // ログイン処理
    private static void login() {
        System.out.println("=== 掲示板:ログイン ===\n");
        
        // ユーザ名入力
        System.out.print("ユーザ名(空文字で終了): ");
        String username = scanner.nextLine();
        System.out.println(username);
        
        // ユーザ名によって処理を分岐
        if (username.isEmpty()) {
            startMenu();
        };
        
        // パスワード入力
        System.out.print("パスワード(空文字で終了): ");
        String password = scanner.nextLine();
        
        // パスワードによって処理を分岐
        if (password.isEmpty()) {
            startMenu();
        };
        
        // usrsテーブルにパスワードが一致するか確認
        if (checkPassword(username, password)) {
            System.out.println(username+"でログインしました。");
            Post.myMenu(username);
        } else {
            System.out.println("ユーザ名またはパスワードが異なります。");
            login();
        }
        
    }

    // ユーザ作成処理
    private static void register() {
        System.out.println("=== 掲示板:ユーザ作成 ===\n");
        
        System.out.print("設定するユーザ名(空文字で終了): ");
        String username = scanner.nextLine();

        if (username.isEmpty()) {
            startMenu();
        }

        if (checkUser(username)) {
            System.out.println("そのユーザ名は既に存在します。");
            register();
        }
        
        System.out.print("設定するパスワード(空文字で終了): ");
        String password = scanner.nextLine();

        if (password.isEmpty()) {
            startMenu();
        }
        
        setUser(username, password);
        System.out.println("ユーザが作成されました。");

        startMenu();
    }

    // ユーザ一覧表示処理
    private static void ls() {
        System.out.println("=== 掲示板:ユーザ一覧表示 ===\n");

        // ユーザ一覧情報取得
        ArrayList<TmpUser> tmpUsers = getUsers();

        String[] users = new String[tmpUsers.size()+1];
        users[0] = "ユーザ名";
        String[] profiles = new String[tmpUsers.size()+1];
        profiles[0] = "プロフィール";

        for (int i=0; i<tmpUsers.size(); i++) {
            users[i+1] = tmpUsers.get(i).getName();
            profiles[i+1] = tmpUsers.get(i).getProfile();
        }

        // ユーザ一覧表示
        for (int i=0; i<users.length; i++) {
            System.out.println(arrangeIndent(users[i], users, 2)+": "+profiles[i]);
        }

        startMenu();
    }

    // ユーザ削除処理
    private static void delete() {
        System.out.println("=== 掲示板:ユーザ削除 ===\n");

        System.out.print("削除するユーザ名(空文字で終了): ");
        String username = scanner.nextLine();

        if (username.isEmpty()) {
            startMenu();
        }

        if (!checkUser(username)) {
            System.out.println("ユーザが存在しません。");
            delete();
        }

        while (true) {
            System.out.print("削除するユーザのパスワード(空文字で終了): ");
            String password = scanner.nextLine();

            if (password.isEmpty()) {
                startMenu();
            }

            if (checkPassword(username, password)) {
                System.out.println("次のユーザを削除します: "+username);
                System.out.print("本当によろしいですか？ (y/n): ");
                String isDelete = scanner.nextLine();
                if (isDelete.equals("y")) {
                    deleteUser(username);
                    System.out.println("削除されました。");
                }
                startMenu();
            } else {
                System.out.println("パスワードが異なります。");
            }
        }
    }

    // ヘルプ表示
    private static void help() {
        System.out.println("=== 掲示板:ヘルプ(スタートメニュー) ===\n");
        
        System.out.println(arrangeIndent(LOGIN_COMMAND, COMMANDS, 2)+": ログイン");
        System.out.println(arrangeIndent(REGISTER_COMMAND, COMMANDS, 2)+": ユーザ作成");
        System.out.println(arrangeIndent(LS_COMMAND, COMMANDS, 2)+": ユーザ一覧表示");
        System.out.println(arrangeIndent(DELETE_COMMAND, COMMANDS, 2)+": ユーザ削除");
        System.out.println(arrangeIndent(HELP_COMMAND, COMMANDS, 2)+": ヘルプ(スタートメニュー)");
        System.out.println(arrangeIndent(EXIT_COMMAND, COMMANDS, 2)+": プログラム終了");

        startMenu();
    }

    // 終了処理
    private static void exit() {
        System.out.println("=== 掲示板:プログラム終了 ===\n");

        System.out.print("プログラムを終了しますか？ (y/n): ");
        String isExit = scanner.nextLine();
        if (isExit.equals("y")) {
            System.out.println("終了しています...");
            System.exit(0);
        } else {
            startMenu();
        }
    }

    // インデントを揃えるためのメソッド
    // 引数: 対象文字列, 縦に並べる文字列の配列, 右にどれだけ空白を入れるか
    private static String arrangeIndent(String str, String[] strArray, int rightMargin) {
        int maxLength = 0;
        for (String s : strArray) {
            if (getStringWidth(s) > maxLength) {
                maxLength = getStringWidth(s);
            }
        }
        return str + " ".repeat(maxLength - getStringWidth(str) + rightMargin);
    }

    // 文字列の幅を計算するメソッド
    private static int getStringWidth(String str) {
        int width = 0;
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i) >= 0x80) {
                width += 2;
            } else {
                width += 1;
            }
        }
        return width;
    }

    // ユーザ名の存在確認
    private static boolean checkUser(String username) {
        Connection conn = ConnectDB.getConn();

        String sql = "SELECT name FROM users WHERE is_deleted = 0";
        int is_exist = 0;
        try {
            ResultSet rs = ConnectDB.executeCommand(conn, sql);
            while (rs.next()) {
                if (rs.getString("name").equals(username)) {
                    ConnectDB.closeConn(conn);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDB.closeConn(conn);
        return false;

    }

    // パスワードの一致確認
    private static boolean checkPassword(String username, String password) {
        Connection conn = ConnectDB.getConn();

        String sql = "SELECT COUNT(*) FROM users WHERE name = '"+username+"' AND password = '"+password+"' AND is_deleted = 0";
        ResultSet rs = ConnectDB.executeCommand(conn, sql);

        int count = 0;
        try {
            rs.next();
            count = rs.getInt(1);;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDB.closeConn(conn);

        return count == 1;
    }

    // ユーザ一覧情報取得
    private static ArrayList<TmpUser> getUsers() {
        ArrayList<TmpUser> tmpUsers = new ArrayList<TmpUser>();
        Connection conn = ConnectDB.getConn();
        
        String sql = "SELECT name, profile FROM users WHERE is_deleted = 0";
        ResultSet rs = ConnectDB.executeCommand(conn, sql);
        
        try {
            while (rs.next()) {
                TmpUser tmpUser = new TmpUser(rs.getString("name"), rs.getString("profile"));
                tmpUsers.add(tmpUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ConnectDB.closeConn(conn);

        return tmpUsers;
    }

    // ユーザ情報の登録
    private static void setUser(String username, String password) {
        Connection conn = ConnectDB.getConn();
        
        String sql = "INSERT INTO users (name, password) VALUES ('"+username+"', '"+password+"')";
        ConnectDB.updateCommand(conn, sql);

        ConnectDB.closeConn(conn);
    }

    // ユーザ情報の削除
    private static void deleteUser(String username) {
        Connection conn = ConnectDB.getConn();
        
        String sql = "UPDATE users SET is_deleted = 1 WHERE name = '"+username+"' AND is_deleted = 0";
        ConnectDB.updateCommand(conn, sql);

        ConnectDB.closeConn(conn);
    }
}

class TmpUser {
    private final String name;
    private final String profile;

    public TmpUser(String name, String profile) {
        this.name = name;
        this.profile = profile;
    }

    public String getName() {
        return this.name;
    }

    public String getProfile() {
        return this.profile;
    }
}
