package com.jonssonyan.数据结构.数组.两个数组的交集;

import java.util.HashMap;
import java.util.Map;

public class Solution1 {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 2, 1};
        int[] array2 = {2, 2};
        solution1(array1, array2);
    }

    public static void solution1(int[] array1, int[] array2) {

        Map<Integer, Integer> map = new HashMap<>();

        // 遍历array1，进行映射，key数组元素值，value出现的次数
        for (int ele : array1) {
            if (map.containsKey(ele)) {
                map.put(ele, map.get(ele) + 1);
            } else {
                map.put(ele, 1);
            }
        }

        for (int ele : array2) {
            // 如果map key中包含array2的元素，并且map value大于0
            if (map.containsKey(ele) && map.get(ele) > 0) {
                // 打印
                System.out.println(ele);
                // 计数器-- 匹配一次减少一个
                map.put(ele, map.get(ele) - 1);
            }
        }
    }
}
