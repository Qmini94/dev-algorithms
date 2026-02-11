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
}
