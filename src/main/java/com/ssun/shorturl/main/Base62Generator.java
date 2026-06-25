package com.ssun.shorturl.main;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Base62Generator {

    // BASE64에서 가져옴
    private static final char[] toBase62 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    private static final Map<Character, Integer> indexMap;

    static {
        Map<Character, Integer> initialMap = new HashMap<>();
        for (int i = 0; i < toBase62.length; i++) {
            initialMap.put(toBase62[i], i);
        }
        indexMap = Collections.unmodifiableMap(initialMap);
    }

    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        if (num == 0) return String.valueOf(toBase62[0]);
        while (num > 0) {
            sb.append(toBase62[(int)(num % 62)]);
            num /= 62;
        }
        return sb.reverse().toString();

    }

    public static long decode(String shortUrl) {
        long result = 0;
        for (char c : shortUrl.toCharArray()) {
            result = result * 62 + indexOf(c); // toBase62에서 c의 인덱스
        }
        return result;
    }

    private static int indexOf(char c) {
        Integer index = indexMap.get(c);
        if (index == null) throw new IllegalArgumentException("Invalid base62 character: " + c);
        return index;
    }
}
