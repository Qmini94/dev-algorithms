package problems.naver;

import java.util.*;

public class example1 {
    public int solution(String S) {
        Stack<Character> stack = new Stack<>();
        int count = 0;
        for (char c : S.toCharArray()) {
            if(c == '(') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) continue;
                stack.pop();
                count++;
            }
        }
        return count;
    }
}
