package problems.naver;

public class example2 {
    public int solution(int[] A, int[] B) {
        int[] sumArrA = new int[A.length];
        int[] sumArrB = new int[B.length];
        int sumA = 0;
        int sumB = 0;
        int count = 0;

        for (int i = 0; i < A.length; i++) {
            sumA += A[i];
            sumB += B[i];
            sumArrA[i] = sumA;
            sumArrB[i] = sumB;
        }

        for (int i = 0; i < A.length - 1; i++) {
            if(sumArrA[i] == sumB - sumArrB[i]) count++;
        }
        return count;
    }
}
