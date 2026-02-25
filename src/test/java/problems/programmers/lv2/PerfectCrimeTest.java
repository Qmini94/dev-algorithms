package problems.programmers.lv2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerfectCrimeTest {

    @Test
    void example1() {
        PerfectCrime pc = new PerfectCrime();

        int[][] info = {
                {1, 2}, {2, 3}, {2, 1}
        };

        assertEquals(2, pc.solution(info, 4, 4));
    }

    @Test
    void example2() {
        PerfectCrime pc = new PerfectCrime();

        int[][] info = {
                {1, 2}, {2, 3}, {2, 1}
        };

        assertEquals(0, pc.solution(info, 1, 7));
    }

    @Test
    void example3() {
        PerfectCrime pc = new PerfectCrime();

        int[][] info = {
                {3, 3}, {3, 3}
        };

        assertEquals(6, pc.solution(info, 7, 1));
    }

    @Test
    void example4() {
        PerfectCrime pc = new PerfectCrime();

        int[][] info = {
                {3, 3}, {3, 3}
        };

        assertEquals(-1, pc.solution(info, 6, 1));
    }

    // 그리디 반례: B에게 큰 것부터 주면 틀림
    @Test
    void greedyTrap() {
        PerfectCrime pc = new PerfectCrime();

        int[][] info = {
                {2, 2}, {2, 2}, {3, 3}
        };

        assertEquals(3, pc.solution(info, 5, 5));
    }

    // 물건 1개: B가 가져가는 게 유리
    @Test
    void singleItem() {
        PerfectCrime pc = new PerfectCrime();

        int[][] info = {
                {3, 1}
        };

        assertEquals(0, pc.solution(info, 2, 2));
    }

    // 둘 다 한계가 1이면 아무것도 못 훔침
    @Test
    void bothLimitOne() {
        PerfectCrime pc = new PerfectCrime();

        int[][] info = {
                {1, 1}
        };

        assertEquals(-1, pc.solution(info, 1, 1));
    }
}