package top100;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 * <p>
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 */
public class LongestCommonPrefix {
    public static void main(String[] args) {
        String s = longestCommonPrefix(new String[]{"dog", "racecar", "car"});
        System.out.println(s);
    }

    public static String longestCommonPrefix(String[] strs) {
        StringBuffer sb = new StringBuffer();

        int j = 0;

        boolean continueRetry = true;

        while (true) {
            if(strs[0].length() < j + 1){
                break;
            }

            char target = strs[0].charAt(j);
            for (int i = 1; i < strs.length; i++) {
                if (strs[i].length() < j + 1 || target != strs[i].charAt(j)) {
                    continueRetry = false;
                    break;
                }
            }
            if (!continueRetry) {
                break;
            }
            j++;
            sb.append(target);
        }

        return sb.toString();
    }
}
