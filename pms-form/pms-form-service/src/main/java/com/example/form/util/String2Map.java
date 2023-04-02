package com.example.form.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GLaDOS
 * @date 2023/4/3 1:26
 */
public class String2Map {
    public static Map<String, Integer> string2map(String s){
        StringBuffer sb = new StringBuffer(s);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);
        String[] split = sb.toString().split(",");

        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String t : split) {
            String[] kv = t.split(":");
            hashMap.put(kv[0], Integer.valueOf(kv[1]));
        }
        return hashMap;
    }
}
