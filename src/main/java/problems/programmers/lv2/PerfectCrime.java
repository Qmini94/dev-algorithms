package problems.programmers.lv2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 문제 설명
 * A도둑과 B도둑이 팀을 이루어 모든 물건을 훔치려고 합니다. 단, 각 도둑이 물건을 훔칠 때 남기는 흔적이 누적되면 경찰에 붙잡히기 때문에, 두 도둑 중 누구도 경찰에 붙잡히지 않도록 흔적을 최소화해야 합니다.
 *
 * 물건을 훔칠 때 조건은 아래와 같습니다.
 *
 * 물건 i를 훔칠 때,
 * A도둑이 훔치면 info[i][0]개의 A에 대한 흔적을 남깁니다.
 * B도둑이 훔치면 info[i][1]개의 B에 대한 흔적을 남깁니다.
 * 각 물건에 대해 A도둑과 B도둑이 남기는 흔적의 개수는 1 이상 3 이하입니다.
 * 경찰에 붙잡히는 조건은 아래와 같습니다.
 *
 * A도둑은 자신이 남긴 흔적의 누적 개수가 n개 이상이면 경찰에 붙잡힙니다.
 * B도둑은 자신이 남긴 흔적의 누적 개수가 m개 이상이면 경찰에 붙잡힙니다.
 * 각 물건을 훔칠 때 생기는 흔적에 대한 정보를 담은 2차원 정수 배열 info, A도둑이 경찰에 붙잡히는 최소 흔적 개수를 나타내는 정수 n, B도둑이 경찰에 붙잡히는 최소 흔적 개수를 나타내는 정수 m이 매개변수로 주어집니다. 두 도둑 모두 경찰에 붙잡히지 않도록 모든 물건을 훔쳤을 때, A도둑이 남긴 흔적의 누적 개수의 최솟값을 return 하도록 solution 함수를 완성해 주세요. 만약 어떠한 방법으로도 두 도둑 모두 경찰에 붙잡히지 않게 할 수 없다면 -1을 return해 주세요.
 *
 * 제한사항
 * 1 ≤ info의 길이 ≤ 40
 * info[i]는 물건 i를 훔칠 때 생기는 흔적의 개수를 나타내며, [A에 대한 흔적 개수, B에 대한 흔적 개수]의 형태입니다.
 * 1 ≤ 흔적 개수 ≤ 3
 * 1 ≤ n ≤ 120
 * 1 ≤ m ≤ 120
 * 테스트 케이스 구성 안내
 * 아래는 테스트 케이스 구성을 나타냅니다. 각 그룹 내의 테스트 케이스를 모두 통과하면 해당 그룹에 할당된 점수를 획득할 수 있습니다.
 *
 * 그룹	총점	테스트 케이스 그룹 설명
 * #1	15%	info[i][1] = 1
 * #2	40%	info의 길이 ≤ 20
 * #3	45%	추가 제한 사항 없음
 * 입출력 예
 * info	                        n	    m	    result
 * [[1, 2], [2, 3], [2, 1]]	    4	    4	    2
 * [[1, 2], [2, 3], [2, 1]]	    1	    7	    0
 * [[3, 3], [3, 3]]	            7	    1	    6
 * [[3, 3], [3, 3]]	            6	    1	    -1
 * 입출력 예 설명
 * 입출력 예 #1
 *
 * 첫 번째와 세 번째 물건을 B도둑이 훔치고 두 번째 물건을 A도둑이 훔치면, A도둑에 대한 흔적은 총 2개이고 B도둑에 대한 흔적은 총 3개입니다. 목표를 달성하면서 A도둑에 대한 흔적 개수를 2보다 더 낮게 만들 수 없습니다.
 * 따라서 2를 return 해야 합니다.
 *
 * 입출력 예 #2
 *
 * B도둑이 모든 물건을 훔쳐도 B의 흔적이 7개 이상 쌓이지 않습니다.
 * 따라서 A도둑의 흔적은 최소 0이 되며, 0을 return 해야 합니다.
 *
 * 입출력 예 #3
 *
 * B도둑이 한 번이라도 물건을 훔치면 B의 흔적이 최소 1개 이상 남습니다. 따라서 모든 물건을 A도둑이 훔쳐야 하며, 이 경우에도 A의 흔적은 7개 미만입니다.
 * 따라서, A도둑이 모든 물건을 훔칠 때의 흔적 개수 6을 return 해야 합니다.
 *
 * 입출력 예 #4
 *
 * 어떤 방법으로도 두 도둑 모두 경찰에 붙잡히지 않고 모든 물건을 훔칠 수 없습니다.
 * 따라서 -1을 return 해야 합니다.
 */
public class PerfectCrime {
    /**
     * B도둑의 흔적 개수를 경찰에게 붙잡히지 않는 최대개수를 만들 수 있는 info배열을 제외한뒤 A도둑의  흔적 최솟값 구하기.
     * 어떤 방법으로도 두 도둑 모두 붙잡힌다면 -1 반환.
     */
    public int solution(int[][] info, int n, int m) {
        /**
         * info에서 도둑B의 흔적을 기준으로 desc로 sort한다.
         */
        sortByBDesc(info);
        exceptThiefBEvidence(info, m);

        /**
         * 남은info의 값의 도둑A의 흔적을 더해 리턴한다. 단, n이상이라면 -1을 반환한다. 없으면 0;
         */


        return -1;
    }


    /**
     * 도둑B를 기준으로 m > count의 조건이 될 수있는 최대값을 구한다.
     * int diff = 1; 선언뒤에 desc로 정렬된 info를 순서대로 더하며 값을 저장한다. m-diff과 동일한 수가 나올때 까지 더하며 안되면
     * 반복문을 다음 배열부터 다시돈다. 끝까지 돌아도 안될시 diff++; 하고 다시 돌려 도둑B의 최대값을 구하면 해당 배열을 pop한다.
     */
    private int[][] exceptThiefBEvidence(int[][] info, int m) {
        int n = info.length;
        if (n == 0) return info;

        int diff = 1;

        // diff가 커질수록 target = m - diff가 작아짐.
        // target <= 0이면 의미 없음 (B 흔적이 0 이하로 맞춰라? 불가능/무의미)
        while (diff < m) {
            int target = m - diff;

            for (int start = 0; start < n; start++) {
                int count = 0;
                Set<Integer> useIdx = new HashSet<>();

                // 네 방식: start부터 끝까지 "쭉 더하기"
                for (int i = start; i < n; i++) {
                    count += info[i][1];
                    useIdx.add(i);

                    // 딱 맞으면 종료
                    if (count == target) {
                        return removeIndices(info, useIdx);
                    }

                    // 오버하면 이 start는 가망 없음 (B가 전부 양수니까)
                    if (count > target) {
                        break;
                    }
                }
            }

            // start 전부 실패 → diff++
            diff++;
        }

        // 끝까지 못 찾음: 네 계획대로면 "제외할 게 없음"이니 원본 반환
        return info;
    }

    /** info에서 remove에 포함된 인덱스들을 제외하고 새 배열 리턴 */
    private int[][] removeIndices(int[][] info, Set<Integer> remove) {
        int newSize = info.length - remove.size();
        if (newSize <= 0) return new int[0][2];

        int[][] result = new int[newSize][2];
        int idx = 0;

        for (int i = 0; i < info.length; i++) {
            if (remove.contains(i)) continue;
            result[idx][0] = info[i][0];
            result[idx][1] = info[i][1];
            idx++;
        }

        return result;
    }

    /** B 기준 내림차순 정렬 */
    private void sortByBDesc(int[][] info) {
        Arrays.sort(info, (a, b) -> Integer.compare(b[1], a[1]));
    }
}
