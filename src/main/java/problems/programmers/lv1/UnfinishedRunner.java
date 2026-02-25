package problems.programmers.lv1;

import java.util.HashMap;
import java.util.Map;

public class UnfinishedRunner {
    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> completionMap = new HashMap<>();
        String UnfinishedName = "";

        for (String name : completion) {
            /**
             * getOrDefault으로 기본값 설정.
             */
            completionMap.put(name, completionMap.getOrDefault(name, 0) + 1);
        }


        for (int i = 0; i < participant.length; i++) {
            if (!completionMap.containsKey(participant[i])) return participant[i];
            if(completionMap.get(participant[i]) != 0) {
                completionMap.put(participant[i], completionMap.get(participant[i]) - 1);
            } else {
                UnfinishedName = participant[i];
            }
        }
        return UnfinishedName;
    }
}
