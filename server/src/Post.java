import java.util.Scanner;

public class Post {
    // コマンド一覧
    private static final String LS_POSTS_COMMAND = "lsposts";
    private static final String POST_COMMAND = "post";
    private static final String LS_MY_POSTS_COMMAND = "lsmyposts";
    private static final String LS_MY_INFO_COMMAND = "lsmyinfo";
    private static final String LOGOUT_COMMAND = "logout";
    private static final String HELP_COMMAND = "help";
    private static final String EXIT_COMMAND = "exit";

    // 入力受付用のScanner
    private static Scanner scanner = new Scanner(System.in);

    public static void myMenu(String username) {
        System.out.println("=== 掲示板:マイメニュー ===");
        System.out.print("コマンドを入力 ("+LS_POSTS_COMMAND+", "+POST_COMMAND+", "+LS_MY_POSTS_COMMAND+", "+LS_MY_INFO_COMMAND+", "+LOGOUT_COMMAND+", "+HELP_COMMAND+", "+EXIT_COMMAND+"): ");
        String command = scanner.next();
    }
}
