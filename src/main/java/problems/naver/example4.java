package problems.naver;

/**
 * 실제 시험 3번째 문제
 * S에서 알파벳 순서와 다른 첫번째 char를 제거해서 return
 */
class example4 {
    public String solution(String S) {
        for (int i = 0; i < S.length() - 1; i++) {
            if (S.charAt(i) > S.charAt(i + 1)) {
                return S.substring(0, i) + S.substring(i + 1);
            }
        }
        return S.substring(0, S.length() - 1);
    }
}