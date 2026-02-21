package problems.programmers.lv1;

import java.util.HashMap;
import java.util.Map;

public class UnfinishedRunner {
    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> completionMap = new HashMap<>();
        String UnfinishedName = "";

        /**
         *  완주자를 map에 답는다. 엣지케이스를 생각해서 value값에 동명이인일 경우 +1
         */
        for (String name : completion) {
            if (completionMap.containsKey(name)) {
                completionMap.put(name, completionMap.get(name) + 1);
            } else {
                completionMap.put(name, 1);
            }
        }

        /**
         * 참가자 배열을 인덱스로 돌면서 완주자에 없다면 바로 return하거나 동명이인일 경우 -1
         * 해당 이름에 완주자가 없어 0이라면 return
         */
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
