# 문제 풀이 기록

작성자: 박규민  
작성일: 2026-01-31

---

## 문제 정보

- URL: https://school.programmers.co.kr/learn/courses/30/lessons/340211
- 문제 이름: 충돌위험 찾기
- 난이도: Lv2
- 클래스명: CollisionRisk

---

## 문제 정의

- 문제 요약: 여러 로봇이 주어진 경로를 규칙에 따라 이동할 때, 같은 시간에 같은 좌표에 2대 이상 모이는 위험 상황의 발생 횟수를 계산한다.
- 입력: 좌표 목록, 각 로봇이 방문할 point 인덱스 경로
- 출력: 충돌 위험 상황의 총 횟수(위험한 좌표의 누적 카운트)

---

## Constraint 분석

| 항목       | 값                                                                   |
| -------- | ------------------------------------------------------------------- |
| 입력 크기    | points의 크기 n ≤ 100, 로봇 수 x ≤ 100, 로봇 경로 길이 m ≤ 100 (문제 제한사항 그대로 기입) |
| 시간 제한    | 명시 없음                                                               |
| 메모리 제한   | 명시 없음                                                               |
| 예상 시간복잡도 | 시뮬레이션 기반: 로봇 이동을 시간 단위로 따라가며 좌표 카운팅. 대략 O(총 이동 스텝 + 시간×로봇수)         |

---

## 1차 접근 (브루트포스)

아이디어  
-  각 로봇의 경로를 routes별로 좌표 리스트를 생성한 뒤에 매 초마다 같은 좌표에 충돌이 일어나는 횟수를 카운팅한다.

시간복잡도  
-  O(최대시간X로봇수)

한계점  
-  루프 종료 조건이 `!(startPoint[1] > endPoint[1])`로 c좌표만 비교하여, c가 감소하는 경우 경로 미생성, r만 변하는 경우 무한루프 발생
- 구간마다 `movedPaths`를 새로 생성하고 `paths.put(i, movedPaths)`로 덮어써서 이전 구간 경로 소실

```java
public class CollisionRisk {  
    public int solution(int[][] points, int[][] routes) {  
        /*  
         * routes를 반복문으로 돌며 각 points를 이동할때에 경로 좌표를 전부 Map<Integer, List<int[]> paths(key: routeIdx, 배열길이: second, paths[y, x]: 좌표)  
         *  를 담은 후에 각 초에서 충돌이 일어나는 횟수를 카운팅한다. (각 초에서 같은 좌표의 충돌 카운팅은 최대 1회로 제한한다)  
         */        Map<Integer, List<int[]>> paths = new HashMap<>();  
        for(int i = 0; i < routes.length; i++) {  
            for(int y = 1; y < routes[i].length; y++) {  
                int[] startPoint = points[routes[i][y - 1] - 1].clone();  
                int[] endPoint = points[routes[i][y] - 1].clone();  
                List<int[]> movedPaths = new ArrayList<>();  
                while(!(startPoint[1] > endPoint[1])) {  
                    movedPaths.add(startPoint.clone());  
                    paths.put(i, movedPaths);  
                    startPoint = movePointToNext(startPoint, endPoint);  
                }  
            }  
        }  
  
        /**  
         * paths로 각 초(List의 length)에서 충돌이 일어나는 횟수를 카운팅한다. (각 초에서 같은 좌표의 충돌 카운팅은 최대 1회로 제한한다)  
         */        int count = 0;  
        int second = 0;  
        while(!paths.isEmpty()) {  
            Set<String> movedPoint = new HashSet<>();  
            Set<String> crashedPath = new HashSet<>();  
            Iterator<Map.Entry<Integer, List<int[]>>> it = paths.entrySet().iterator();  
  
            while (it.hasNext()) {  
                Map.Entry<Integer, List<int[]>> entry = it.next();  
                List<int[]> path = entry.getValue();  
  
                if(second < path.size()) {  
                    String key = path.get(second)[0] + "," + path.get(second)[1];  
                    if(!movedPoint.contains(key)) {  
                        movedPoint.add(key);  
                    } else {  
                        crashedPath.add(key);  
                    }  
                } else {  
                    it.remove();  
                }  
            }  
  
            if(movedPoint.size() != paths.size()) {  
                count = count + crashedPath.size();  
            }  
            second++;  
        }  
  
        return count;  
    }  
  
    private int[] movePointToNext(int[] startPoint, int[] endPoint) {  
        int startY = startPoint[0];  
        int startX = startPoint[1];  
        int endY = endPoint[0];  
        int endX = endPoint[1];  
  
        if(startY != endY) {  
            startY = startY < endY ? startY + 1 : startY - 1;  
            startPoint[0] = startY;  
        } else {  
            startX = startX <= endX ? startX + 1 : startX - 1;  
            startPoint[1] = startX;  
        }  
  
        return startPoint;  
    }  
}
```

---

## 2차 접근 (개선 시도)

아이디어  
-  1차 접근의 경로 생성 버그를 수정하고, 충돌 카운팅 조건을 단순화한다.

개선 포인트  
- 종료 조건: `!(startPoint[1] > endPoint[1])` → `!Arrays.equals(startPoint, endPoint)`로 양축 모두 비교
- 경로 저장: `movedPaths`를 바깥 루프에서 한 번만 생성하여 구간별 덮어쓰기 방지
- 도착점 중복 방지: 마지막 구간에서만 `endPoint`를 추가 (`if(y == routes[i].length - 1)`)
- 충돌 판정: `movedPoint.size() != paths.size()` 대신 `!crashedPath.isEmpty()`로 직접 확인

```java
public class CollisionRisk {  
    public int solution(int[][] points, int[][] routes) {  
        /*  
         * routes를 반복문으로 돌며 각 points를 이동할때에 경로 좌표를 전부 Map<Integer, List<int[]> paths(key: routeIdx, 배열길이: second, paths[y, x]: 좌표)  
         *  를 담은 후에 각 초에서 충돌이 일어나는 횟수를 카운팅한다. (각 초에서 같은 좌표의 충돌 카운팅은 최대 1회로 제한한다)  
         */        Map<Integer, List<int[]>> paths = new HashMap<>();  
        for(int i = 0; i < routes.length; i++) {  
            List<int[]> movedPaths = new ArrayList<>();  
            for(int y = 1; y < routes[i].length; y++) {  
                int[] startPoint = points[routes[i][y - 1] - 1].clone();  
                int[] endPoint = points[routes[i][y] - 1].clone();  
                while(!Arrays.equals(startPoint, endPoint)) {  
                    movedPaths.add(startPoint.clone());  
                    startPoint = movePointToNext(startPoint, endPoint);  
                }  
                if(y == routes[i].length - 1) {  
                    movedPaths.add(endPoint.clone());  
                }  
            }  
            paths.put(i, movedPaths);  
        }  
  
        /**  
         * paths로 각 초(List의 length)에서 충돌이 일어나는 횟수를 카운팅한다. (각 초에서 같은 좌표의 충돌 카운팅은 최대 1회로 제한한다)  
         */        int count = 0;  
        int second = 0;  
        while(!paths.isEmpty()) {  
            Set<String> movedPoint = new HashSet<>();  
            Set<String> crashedPath = new HashSet<>();  
            Iterator<Map.Entry<Integer, List<int[]>>> it = paths.entrySet().iterator();  
  
            while (it.hasNext()) {  
                Map.Entry<Integer, List<int[]>> entry = it.next();  
                List<int[]> path = entry.getValue();  
  
                if(second < path.size()) {  
                    String key = path.get(second)[0] + "," + path.get(second)[1];  
                    if(!movedPoint.contains(key)) {  
                        movedPoint.add(key);  
                    } else {  
                        crashedPath.add(key);  
                    }  
                } else {  
                    it.remove();  
                }  
            }  
  
            if(!crashedPath.isEmpty()) {  
                count = count + crashedPath.size();  
            }  
            second++;  
        }  
  
        return count;  
    }  
  
    private int[] movePointToNext(int[] startPoint, int[] endPoint) {  
        int startY = startPoint[0];  
        int startX = startPoint[1];  
        int endY = endPoint[0];  
        int endX = endPoint[1];  
  
        if(startY != endY) {  
            startY = startY < endY ? startY + 1 : startY - 1;  
            startPoint[0] = startY;  
        } else {  
            startX = startX < endX ? startX + 1 : startX - 1;  
            startPoint[1] = startX;  
        }  
  
        return startPoint;  
    }  
}
```

---

## 최적 풀이 (Solution)

핵심 알고리즘  
- 각 로봇에 대해 **현재 위치(y,x)**, **다음 목적지 인덱스(targetIdx)** 만 들고 간다.
    
- **0초**에 시작 좌표를 카운팅한다. (시작 좌표 포함)
    
- 이후 매 초마다:
    
    1. 아직 진행 중인 로봇은 **규칙대로 1칸 이동** (y 먼저, 그 다음 x)
        
    2. 이동 후 좌표를 카운팅
        
    3. 같은 초에 같은 좌표에 **2대 이상** 있으면 그 좌표는 **충돌 1회**로 카운트
        
    4. 해당 초에 **마지막 목적지 도착한 로봇**은 그 초까지만 포함하고 다음 초부터 제거 (마지막 좌표 포함)

자료구조 선택 이유  
- 로봇 상태: `RobotState{y,x,targetIdx,done}` 같은 형태를 배열로 관리 (O(로봇 수))
    
- 초별 좌표 카운팅: `HashMap<Long, Integer>`
    
    - 좌표를 `"y,x"` 문자열로 만들지 말고 `long key`로 패킹
        
    - `key = ((long)y << 32) ^ (x & 0xffffffffL)`


```java
public class CollisionRisk {

    static class Robot {
        int y, x;          // current position
        int targetIdx;     // next point index in routes[i]
        boolean done;      // finished (will be removed after counting this second)

        Robot(int y, int x, int targetIdx, boolean done) {
            this.y = y;
            this.x = x;
            this.targetIdx = targetIdx;
            this.done = done;
        }
    }

    public int solution(int[][] points, int[][] routes) {
        int rCount = routes.length;
        Robot[] robots = new Robot[rCount];

        // init robots at time 0 (starting coordinate must be included)
        for (int i = 0; i < rCount; i++) {
            int startPointNo = routes[i][0];
            int sy = points[startPointNo - 1][0];
            int sx = points[startPointNo - 1][1];

            // if route length == 1, robot is already at final destination at t=0
            boolean done = (routes[i].length == 1);
            robots[i] = new Robot(sy, sx, 1, done);
        }

        int answer = 0;

        // time = 0 count (include start positions)
        answer += countCollisionsThisSecond(robots);

        // remove robots that finished at t=0 (they should not exist in t=1)
        boolean anyActive = false;
        for (Robot rb : robots) {
            if (!rb.done) { anyActive = true; break; }
        }

        // simulate from time = 1 onward
        while (anyActive) {
            // move each active robot by 1 step
            for (int i = 0; i < rCount; i++) {
                Robot rb = robots[i];
                if (rb == null || rb.done) continue;

                int[] route = routes[i];
                int targetPointNo = route[rb.targetIdx];
                int ty = points[targetPointNo - 1][0];
                int tx = points[targetPointNo - 1][1];

                moveOneStep(rb, ty, tx);

                // reached current target?
                if (rb.y == ty && rb.x == tx) {
                    rb.targetIdx++;
                    if (rb.targetIdx >= route.length) {
                        // reached final destination at this second (must be counted this second)
                        rb.done = true;
                    }
                }
            }

            // count collisions for this second (includes final positions reached this second)
            answer += countCollisionsThisSecond(robots);

            // check if any robot remains active for next second
            anyActive = false;
            for (Robot rb : robots) {
                if (rb != null && !rb.done) { anyActive = true; break; }
            }
        }

        return answer;
    }

    // move rule: y first, then x
    private void moveOneStep(Robot rb, int ty, int tx) {
        if (rb.y != ty) {
            rb.y += (rb.y < ty) ? 1 : -1;
        } else if (rb.x != tx) {
            rb.x += (rb.x < tx) ? 1 : -1;
        }
    }

    // Counts collision points in current second:
    // if >=2 robots share same cell, that cell counts as 1 collision.
    private int countCollisionsThisSecond(Robot[] robots) {
        HashMap<Long, Integer> freq = new HashMap<>(robots.length * 2);

        for (Robot rb : robots) {
            if (rb == null) continue;
            // IMPORTANT: even if rb.done == true, it still exists in THIS second.
            // We remove it by not processing in next loop; counting happens here.
            long key = pack(rb.y, rb.x);
            freq.put(key, freq.getOrDefault(key, 0) + 1);
        }

        int collisions = 0;
        for (int v : freq.values()) {
            if (v >= 2) collisions++;
        }
        return collisions;
    }

    private long pack(int y, int x) {
        return ((long) y << 32) ^ (x & 0xffffffffL);
    }
}
```

---

## 배운 점

- 실수한 부분:
- 다음에 같은 유형 만나면 체크할 것:

---
