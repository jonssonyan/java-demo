package com.jonssonyan.数据结构.数组.最长公共前缀;

public class Solution1 {
    public static void main(String[] args) {
        String[] strings = {"fla", "flag", "flag1"};
        System.out.println(solution1(strings));
    }

    public static String solution1(String[] strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        String template = strings[0];
        int i = template.length(), j = 1;
        while (i > 0 && j <= strings.length - 1) {
            String str = template.substring(0, i);
            if (strings[j].startsWith(str)) {
                j++;
            } else {
                j = 1;
                i--;
            }
        }
        return j == strings.length ? template.substring(0, i) : "";
    }
}
