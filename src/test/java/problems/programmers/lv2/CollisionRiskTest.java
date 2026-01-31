package problems.programmers.lv2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CollisionRiskTest {

    @Test
    void example1() {
        CollisionRisk cr = new CollisionRisk();

        int[][] points = {
                {3, 2}, {6, 4}, {4, 7}, {1, 4}
        };

        int[][] routes = {
                {4, 2},
                {1, 3},
                {2, 4}
        };

        assertEquals(1, cr.solution(points, routes));
    }

    @Test
    void example2() {
        CollisionRisk cr = new CollisionRisk();

        int[][] points = {
                {3, 2}, {6, 4}, {4, 7}, {1, 4}
        };

        int[][] routes = {
                {4, 2},
                {1, 3},
                {4, 2},
                {4, 3}
        };

        assertEquals(9, cr.solution(points, routes));
    }

    @Test
    void example3() {
        CollisionRisk cr = new CollisionRisk();

        int[][] points = {
                {2, 2}, {2, 3}, {2, 7}, {6, 6}, {5, 2}
        };

        int[][] routes = {
                {2, 3, 4, 5},
                {1, 3, 4, 5}
        };

        assertEquals(0, cr.solution(points, routes));
    }
}
