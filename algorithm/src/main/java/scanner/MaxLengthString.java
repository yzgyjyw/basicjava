package scanner;

import java.util.Scanner;

public class MaxLengthString {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();


        StringBuffer sb = new StringBuffer();

        String maxStr = null;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) >= '0' && line.charAt(i) <= '9') {
                sb.append(line.charAt(i));
            } else {
                if (maxStr == null || sb.length() > maxStr.length()) {
                    maxStr = sb.toString();
                }
                sb = new StringBuffer();
            }
        }

        if (maxStr == null || sb.length() > maxStr.length()) {
            maxStr = sb.toString();
        }


        System.out.println(maxStr);

    }
}
