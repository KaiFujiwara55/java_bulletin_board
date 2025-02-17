package util;

import java.util.*;

public class InputHandler {
    public static String inputString(String message) {
        System.out.print(message + ": ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }

    public static int inputInt(String message) {
        System.out.print(message + ": ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        return input;
    }

    public static void formatKeyword(String keyword) {
        System.out.println("=== " + keyword + " ===");
    }

    public static void forbiddenString(String keyword) {
        System.out.println("不正なコマンドです: " + keyword);
    }
}
