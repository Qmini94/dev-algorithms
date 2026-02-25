package problems.naver;

/**
 * 실제 시험 2번째 문제
 * 순회하며 증가가 아닌 경우 count
 */

class example3 {
    public int solution(int[] A) {
        int count = 0;
        int beforeNum = 0;

        for (int a : A) {
            if (beforeNum == 0) {
                beforeNum = a;
                continue;
            }
            if (beforeNum >= a) count++;
            beforeNum = a;
        }
        return count;
    }
}