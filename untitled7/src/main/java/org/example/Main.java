package org.example;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input: m = total time, n = lifespan of each character, s = string
        int m = sc.nextInt(); // total time or limit
        int n = sc.nextInt(); // lifespan of each character
        String s = sc.next(); // input string

        int res = 0; // result to store total operations
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            // If character is not in the map or has expired, add it with full lifetime
            char currentChar = s.charAt(i);
            map.put(currentChar, n); // reset the lifetime of the character

            // Decrease the lifetime of all characters by 1
            Iterator<Character> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                char c = iterator.next();
                map.put(c, map.get(c) - 1); // decrement lifetime
                if (map.get(c) <= 0) {
                    iterator.remove(); // remove character if its lifetime has expired
                }
            }
            res++;
        }

        // Process remaining characters after the loop ends
        while (!map.isEmpty()) {
            Iterator<Character> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                char c = iterator.next();
                map.put(c, map.get(c) - 1); // decrement lifetime
                if (map.get(c) <= 0) {
                    iterator.remove(); // remove character if its lifetime has expired
                }
            }
            res++;
        }

        System.out.println(res); // print the total operations
    }
}
