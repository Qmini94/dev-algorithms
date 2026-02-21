package problems.programmers.lv1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnfinishedRunnerTest {
    @Test
    void example1() {
        UnfinishedRunner ur = new UnfinishedRunner();

        String[] participant = {
                "leo", "kiki", "eden"
        };

        String[] completion = {
                "eden", "kiki"
        };

        assertEquals("leo", ur.solution(participant, completion));

    }

    /**
     * 엣지케이스: 동명이인인데 완주하지 못했을 경우.
     */
    @Test
    void example2() {
        UnfinishedRunner ur = new UnfinishedRunner();

        String[] participant = {
                "leo", "kiki", "eden", "kiki"
        };

        String[] completion = {
                "eden", "kiki", "leo"
        };

        assertEquals("kiki", ur.solution(participant, completion));

    }
}
