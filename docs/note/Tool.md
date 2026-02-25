## 개요
코딩테스트를 풀며 사용할 도구로 활용할 method를 정리합니다. 문제를 파악해서 최적의 시간복잡도를 가진 알고리즘을 찾아내 해당 알고리즘을 활용하기 위해 인풋을 정리하기 위한 도구 method입니다. 후에 문제에 맞게 커스텀해서 사용합니다.

## 목차

1. [HashMap 빈도 세기]
2. [Prefix Sum (부분합)]
3. [투 포인터]
4. [그리디]
5. [이진 탐색]
6. [스택]
7. [큐 + BFS]
8. [DFS]
9. [정렬 활용]
10. [문자열 처리]

---

## 1. HashMap 빈도 세기

### 언제 쓰나?

- "가장 많이 나온 값", "중복 찾기", "두 수의 합" 문제

### 공식 코드

```java
Map<Integer, Integer> map = new HashMap<>();
for (int num : arr) {
    map.put(num, map.getOrDefault(num, 0) + 1);
}
```

### 예시: 배열에서 두 수의 합이 target인 쌍 찾기

```
arr = [2, 7, 11, 15], target = 9

풀이: 순회하면서 "target - 현재값"이 map에 있는지 확인

1. num=2 → target-num=7 → map에 7 없음 → map에 {2:0} 저장
2. num=7 → target-num=2 → map에 2 있음! → 정답: [0, 1]
```

```java
public int[] twoSum(int[] arr, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
        int need = target - arr[i];
        if (map.containsKey(need)) {
            return new int[]{map.get(need), i};
        }
        map.put(arr[i], i);
    }
    return new int[]{};
}
```

### 시간복잡도

- O(n) — 배열 한 번 순회, HashMap 조회는 O(1)
- 브루트포스 O(n²)을 O(n)으로 줄이는 대표적인 패턴

---

## 2. Prefix Sum (부분합)

### 언제 쓰나?

- "구간 합", "부분 배열의 합", "두 줄의 합이 같은 분할점 찾기" 문제

### 공식 코드

```java
int[] prefix = new int[arr.length + 1];
for (int i = 0; i < arr.length; i++) {
    prefix[i + 1] = prefix[i] + arr[i];
}
// i~j 구간 합 = prefix[j+1] - prefix[i]
```

### 예시: 배열을 K 위치에서 잘라서 왼쪽 합 = 오른쪽 합인 K 찾기

```
arr = [1, 2, 3, 4, 10]

prefix 배열 만들기:
prefix = [0, 1, 3, 6, 10, 20]
          ↑  ↑  ↑  ↑   ↑   ↑
        [0] [1] [1+2] [1+2+3] [1+2+3+4] [1+2+3+4+10]

K=1로 자르면: 왼쪽 [1] = 1, 오른쪽 [2,3,4,10] = 19 → 다름
K=2로 자르면: 왼쪽 [1,2] = 3, 오른쪽 [3,4,10] = 17 → 다름
K=3로 자르면: 왼쪽 [1,2,3] = 6, 오른쪽 [4,10] = 14 → 다름
K=4로 자르면: 왼쪽 [1,2,3,4] = 10, 오른쪽 [10] = 10 → 같음! 정답!

왼쪽 합 = prefix[K] - prefix[0]
오른쪽 합 = prefix[5] - prefix[K]
```

```java
public int findSplitPoint(int[] arr) {
    int[] prefix = new int[arr.length + 1];
    for (int i = 0; i < arr.length; i++) {
        prefix[i + 1] = prefix[i] + arr[i];
    }
    int total = prefix[arr.length];
    for (int k = 1; k < arr.length; k++) {
        int left = prefix[k];
        int right = total - left;
        if (left == right) return k;
    }
    return -1;
}
```

### 시간복잡도

- prefix 배열 만들기 O(n) + 분할점 찾기 O(n) = O(n)
- 매번 구간 합을 for문으로 구하면 O(n²)인데, prefix sum으로 O(n)

---

## 3. 투 포인터

### 언제 쓰나?

- "정렬된 배열에서 조건 맞는 쌍 찾기", "연속 부분 배열" 문제

### 공식 코드

```java
int left = 0, right = arr.length - 1;
while (left < right) {
    int sum = arr[left] + arr[right];
    if (sum == target) return new int[]{left, right};
    else if (sum < target) left++;
    else right--;
}
```

### 예시: 정렬된 배열에서 합이 9인 쌍 찾기

```
arr = [1, 2, 4, 7, 11], target = 9

1. left=0(값1), right=4(값11) → 합=12 → 12>9 → right--
2. left=0(값1), right=3(값7)  → 합=8  → 8<9  → left++
3. left=1(값2), right=3(값7)  → 합=9  → 정답! [1, 3]
```

### 시간복잡도

- O(n) — 양쪽에서 한 칸씩 좁혀오니까 최대 n번
- 브루트포스 O(n²)을 O(n)으로 줄임

---

## 4. 그리디

### 언제 쓰나?

- "최소 횟수", "최대 이익", "거스름돈" 문제
- 공식이 없고 "매 순간 최선의 선택 = 전체 최선"일 때 사용

### 예시: 자물쇠 번호 맞추기 (후기 1번 유형)

```
자물쇠: "115", 비밀번호: "225"
한 번호를 돌리면 바로 다음 번호도 함께 +1 (마지막 번호는 혼자)

왼쪽부터 차이를 맞춰나가는 그리디 접근:
1번째: 1→2 (차이 1) → 1번 돌림 → "225" (2번째도 같이 +1)
   현재: "226"... 이런 식으로 앞에서부터 맞춰나감
```

```java
// 그리디의 핵심: 앞에서부터(또는 큰 것부터) 처리
// 거스름돈 예시 - 큰 동전부터 사용
public int minCoins(int change) {
    int[] coins = {500, 100, 50, 10};
    int count = 0;
    for (int coin : coins) {
        count += change / coin;
        change %= coin;
    }
    return count;
}
```

### 그리디 판단법

- "이 선택이 나중에 후회되지 않는가?" → Yes면 그리디 가능
- 정렬 후 앞/뒤부터 처리하는 패턴이 많음

---

## 5. 이진 탐색

### 언제 쓰나?

- "정렬된 배열에서 값 찾기", "조건을 만족하는 최솟값/최댓값" 문제

### 공식 코드

```java
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    while (left <= right) {
        int mid = (left + right) / 2;
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

### 예시: 정렬된 배열에서 7 찾기

```
arr = [1, 3, 5, 7, 9, 11, 13]
target = 7

1. left=0, right=6 → mid=3 → arr[3]=7 → 찾음! return 3

만약 target=9라면:
1. left=0, right=6 → mid=3 → arr[3]=7 → 7<9 → left=4
2. left=4, right=6 → mid=5 → arr[5]=11 → 11>9 → right=4
3. left=4, right=4 → mid=4 → arr[4]=9 → 찾음! return 4

만약 target=6이라면:
1. left=0, right=6 → mid=3 → arr[3]=7 → 7>6 → right=2
2. left=0, right=2 → mid=1 → arr[1]=3 → 3<6 → left=2
3. left=2, right=2 → mid=2 → arr[2]=5 → 5<6 → left=3
4. left=3, right=2 → left>right → 못 찾음! return -1
```

### 시간복잡도

- O(log n) — 매번 절반씩 줄이니까
- 배열 크기 100만이어도 약 20번이면 찾음

---

## 6. 스택

### 언제 쓰나?

- "괄호 검증", "뒤로가기", "이전 상태로 돌아가기" 문제

### 공식 코드

```java
Stack<Character> stack = new Stack<>();
stack.push('a');     // 추가 O(1)
stack.peek();        // 맨 위 확인 O(1) (제거 안 함)
stack.pop();         // 맨 위 꺼내기 O(1)
stack.isEmpty();     // 비었는지 확인
```

### 예시: 괄호 유효성 검사 "({[]})"

```
문자열: "({[]})"

1. '(' → 스택에 push    스택: ['(']
2. '{' → 스택에 push    스택: ['(', '{']
3. '[' → 스택에 push    스택: ['(', '{', '[']
4. ']' → 닫는 괄호! pop → '[' → 짝 맞음!   스택: ['(', '{']
5. '}' → 닫는 괄호! pop → '{' → 짝 맞음!   스택: ['(']
6. ')' → 닫는 괄호! pop → '(' → 짝 맞음!   스택: []

스택이 비었으므로 → 유효한 괄호!

문자열: "({])" 라면?
1. '(' → push    스택: ['(']
2. '{' → push    스택: ['(', '{']
3. ']' → pop → '{' → 짝 안 맞음! → 유효하지 않음!
```

```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (c == ')' && top != '(') return false;
            if (c == '}' && top != '{') return false;
            if (c == ']' && top != '[') return false;
        }
    }
    return stack.isEmpty();
}
```

### 시간복잡도

- O(n) — 문자열 한 번 순회

---

## 7. 큐 + BFS

### 언제 쓰나?

- "최단 거리", "최소 횟수", "레벨별 탐색", "퍼져나가기" 문제
- **"최단"이라는 단어가 보이면 BFS**

### 공식 코드

```java
Queue<int[]> queue = new LinkedList<>();
boolean[][] visited = new boolean[n][m];
int[] dx = {-1, 1, 0, 0};  // 상하좌우
int[] dy = {0, 0, -1, 1};

queue.add(new int[]{startX, startY, 0});  // {행, 열, 거리}
visited[startX][startY] = true;

while (!queue.isEmpty()) {
    int[] cur = queue.poll();
    int x = cur[0], y = cur[1], dist = cur[2];

    if (x == endX && y == endY) return dist;  // 도착!

    for (int i = 0; i < 4; i++) {
        int nx = x + dx[i];
        int ny = y + dy[i];
        if (nx >= 0 && nx < n && ny >= 0 && ny < m
            && !visited[nx][ny] && maze[nx][ny] == 1) {
            visited[nx][ny] = true;
            queue.add(new int[]{nx, ny, dist + 1});
        }
    }
}
return -1;  // 도착 불가
```

### 예시: 미로 최단거리

```
미로:
1 1 1
1 1 0
0 1 1 ← 도착

1. (0,0,거리0) 꺼냄 → 상하좌우 확인
   우(0,1) 갈 수 있음, 하(1,0) 갈 수 있음
   큐: [(0,1,거리1), (1,0,거리1)]

2. (0,1,거리1) 꺼냄 → 상하좌우 확인
   우(0,2) 갈 수 있음, 하(1,1) 갈 수 있음
   큐: [(1,0,거리1), (0,2,거리2), (1,1,거리2)]

3. (1,0,거리1) 꺼냄 → 상하좌우 확인
   갈 곳 없음 (벽이거나 이미 방문)
   큐: [(0,2,거리2), (1,1,거리2)]

4. (0,2,거리2) 꺼냄 → 상하좌우 확인
   갈 곳 없음
   큐: [(1,1,거리2)]

5. (1,1,거리2) 꺼냄 → 상하좌우 확인
   하(2,1) 갈 수 있음
   큐: [(2,1,거리3)]

6. (2,1,거리3) 꺼냄 → 상하좌우 확인
   우(2,2) 갈 수 있음
   큐: [(2,2,거리4)]

7. (2,2,거리4) 꺼냄 → 도착! return 4

FIFO 큐 덕분에 거리1 → 거리2 → 거리3 순서로 처리
= 처음 도착한 게 최단거리
```

### 시간복잡도

- O(V + E) — 노드 수 + 간선 수
- 2차원 미로에서는 O(n × m)

---

## 8. DFS

### 언제 쓰나?

- "모든 경로 탐색", "경로 존재 여부", "섬 개수 세기", "연결 요소" 문제
- **"모든 경우"라는 단어가 보이면 DFS**

### 공식 코드 (재귀)

```java
boolean[] visited;

void dfs(int cur, List<List<Integer>> graph) {
    visited[cur] = true;
    for (int nxt : graph.get(cur)) {
        if (!visited[nxt]) {
            dfs(nxt, graph);
        }
    }
}
```

### 예시: 섬 개수 세기

```
지도 (1=땅, 0=바다):
1 1 0 0
1 0 0 1
0 0 1 1

섬 찾기: 1을 만나면 DFS로 연결된 모든 1을 방문 처리

1. (0,0)=1 발견! DFS 시작 → 섬 1개
   (0,0) 방문 → 상하좌우 중 (0,1)=1 → 방문
   (0,1) 방문 → 상하좌우 중 (1,0)=1 → 방문
   (1,0) 방문 → 갈 곳 없음 → DFS 종료

   방문 상태:
   V V 0 0
   V 0 0 1
   0 0 1 1

2. (0,2)=0 → 건너뜀
3. (0,3)=0 → 건너뜀
4. (1,1)=0 → 건너뜀
5. (1,2)=0 → 건너뜀
6. (1,3)=1 발견! DFS 시작 → 섬 2개
   (1,3) 방문 → (2,3)=1 → 방문
   (2,3) 방문 → (2,2)=1 → 방문
   (2,2) 방문 → 갈 곳 없음 → DFS 종료

   방문 상태:
   V V 0 0
   V 0 0 V
   0 0 V V

7. 나머지 다 방문했거나 0 → 종료

결과: 섬 2개
```

```java
public int numIslands(int[][] grid) {
    int count = 0;
    boolean[][] visited = new boolean[grid.length][grid[0].length];

    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == 1 && !visited[i][j]) {
                dfs(grid, visited, i, j);
                count++;
            }
        }
    }
    return count;
}

void dfs(int[][] grid, boolean[][] visited, int x, int y) {
    if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) return;
    if (visited[x][y] || grid[x][y] == 0) return;

    visited[x][y] = true;
    dfs(grid, visited, x - 1, y);  // 상
    dfs(grid, visited, x + 1, y);  // 하
    dfs(grid, visited, x, y - 1);  // 좌
    dfs(grid, visited, x, y + 1);  // 우
}
```

### 시간복잡도

- O(n × m) — 모든 칸을 한 번씩 방문

---

## 9. 정렬 활용

### 언제 쓰나?

- "K번째 큰 수", "가장 큰/작은", "정렬 후 비교" 문제

### 공식 코드

```java
// 배열 정렬
int[] arr = {5, 2, 8, 1, 9};
Arrays.sort(arr);  // [1, 2, 5, 8, 9] 오름차순

// 내림차순 (Integer 배열만 가능)
Integer[] arr2 = {5, 2, 8, 1, 9};
Arrays.sort(arr2, Collections.reverseOrder());  // [9, 8, 5, 2, 1]

// 리스트 정렬
List<Integer> list = new ArrayList<>();
Collections.sort(list);  // 오름차순
Collections.sort(list, Collections.reverseOrder());  // 내림차순

// 2차원 배열 특정 기준으로 정렬
int[][] intervals = {{3,6}, {1,4}, {2,8}};
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);  // 첫번째 값 기준 오름차순
// 결과: {{1,4}, {2,8}, {3,6}}

// 배열 자르기
int[] cut = Arrays.copyOfRange(array, i, j);
```

### 예시: 겹치는 구간 합치기

```
intervals = [[1,3], [2,6], [8,10], [15,18]]

1. 시작점 기준 정렬 → 이미 정렬됨
2. [1,3]과 [2,6] → 2 <= 3이므로 겹침 → [1,6]으로 합침
3. [1,6]과 [8,10] → 8 > 6이므로 안 겹침 → [1,6] 확정
4. [8,10]과 [15,18] → 15 > 10이므로 안 겹침 → [8,10] 확정

결과: [[1,6], [8,10], [15,18]]
```

```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    List<int[]> result = new ArrayList<>();
    result.add(intervals[0]);

    for (int i = 1; i < intervals.length; i++) {
        int[] last = result.get(result.size() - 1);
        if (intervals[i][0] <= last[1]) {
            last[1] = Math.max(last[1], intervals[i][1]);
        } else {
            result.add(intervals[i]);
        }
    }
    return result.toArray(new int[0][]);
}
```

### 시간복잡도

- Arrays.sort()는 O(n log n)

---

## 10. 문자열 처리

### 언제 쓰나?

- "문자열 변환", "부분 문자열 찾기", "회문 검사" 문제
- **네이버 클라우드 코테 후기에서 3번 문제로 출제된 유형 (prefix == suffix)**

### 자주 쓰는 메서드

```java
String s = "abcdef";
s.length();              // 6
s.charAt(0);             // 'a'
s.substring(0, 3);       // "abc" (0부터 3 이전까지)
s.substring(3);          // "def" (3부터 끝까지)
s.equals("abcdef");      // true (== 쓰지 말 것!)
s.contains("bcd");       // true
s.indexOf("cd");         // 2
s.toCharArray();         // ['a','b','c','d','e','f']
s.split(",");            // 쉼표로 분리

// StringBuilder - 문자열 반복 연결 시 필수
StringBuilder sb = new StringBuilder();
sb.append("hello");
sb.append(" world");
sb.toString();           // "hello world"
sb.reverse().toString(); // "dlrow olleh"
```

### 예시: 가장 긴 prefix == suffix 찾기 (후기 3번 유형)

```
문자열: "ababab"

prefix 목록: "a", "ab", "aba", "abab", "ababa"
suffix 목록: "b", "ab", "bab", "abab", "babab"

길이 1: prefix "a" vs suffix "b" → 다름
길이 2: prefix "ab" vs suffix "ab" → 같음!
길이 3: prefix "aba" vs suffix "bab" → 다름
길이 4: prefix "abab" vs suffix "abab" → 같음!
길이 5: prefix "ababa" vs suffix "babab" → 다름

가장 긴 것 = "abab" (길이 4)
```

```java
// 단순 풀이 O(n²) - 시간 넉넉하면 이것부터
public int longestPrefixSuffix(String s) {
    int n = s.length();
    int answer = 0;
    for (int len = 1; len < n; len++) {
        String prefix = s.substring(0, len);
        String suffix = s.substring(n - len);
        if (prefix.equals(suffix)) {
            answer = len;
        }
    }
    return answer;
}

// 최적 풀이 O(n) - KMP의 실패함수 활용
public int longestPrefixSuffixKMP(String s) {
    int n = s.length();
    int[] lps = new int[n];  // lps[i] = s[0..i]에서 prefix==suffix인 최대 길이
    int len = 0;
    int i = 1;

    while (i < n) {
        if (s.charAt(i) == s.charAt(len)) {
            len++;
            lps[i] = len;
            i++;
        } else {
            if (len != 0) {
                len = lps[len - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
    }
    return lps[n - 1];  // 마지막 값이 정답
}
```

### KMP 실패함수 동작 과정

```
문자열: "ababab"

i=1: s[1]='b' vs s[0]='a' → 다름 → lps[1]=0
     lps: [0, 0, -, -, -, -]

i=2: s[2]='a' vs s[0]='a' → 같음! len=1 → lps[2]=1
     lps: [0, 0, 1, -, -, -]

i=3: s[3]='b' vs s[1]='b' → 같음! len=2 → lps[3]=2
     lps: [0, 0, 1, 2, -, -]

i=4: s[4]='a' vs s[2]='a' → 같음! len=3 → lps[4]=3
     lps: [0, 0, 1, 2, 3, -]

i=5: s[5]='b' vs s[3]='b' → 같음! len=4 → lps[5]=4
     lps: [0, 0, 1, 2, 3, 4]

lps[5] = 4 → "ababab"에서 prefix==suffix인 최대 길이 = 4 ("abab")
```

---

## 빠른 판단 가이드

| 키워드                          | 도구         |
| ---------------------------- | ---------- |
| "최단 거리", "최소 횟수"             | BFS        |
| "모든 경로", "경로 존재"             | DFS        |
| "정렬된 배열에서 찾기"                | 이진 탐색      |
| "구간 합", "부분 합"               | Prefix Sum |
| "빈도", "중복", "두 수의 합"         | HashMap    |
| "정렬된 배열 + 조건 쌍"              | 투 포인터      |
| "최소 동전", "최대 이익"             | 그리디        |
| "괄호", "되돌리기"                 | 스택         |
| "K번째", "정렬 후 처리"             | 정렬         |
| "prefix", "suffix", "부분 문자열" | 문자열 (KMP)  |

---

## Java 문법 빠른 참조

```java
// 출력
System.out.println("출력");

// 배열
int[] arr = new int[5];
arr.length;                    // 크기
Arrays.sort(arr);              // 정렬
Arrays.fill(arr, 0);           // 초기화

// 리스트
List<Integer> list = new ArrayList<>();
list.add(1);                   // 추가
list.get(0);                   // 접근
list.size();                   // 크기
list.remove(0);                // 삭제

// HashMap
Map<String, Integer> map = new HashMap<>();
map.put("key", 1);             // 추가
map.get("key");                // 조회
map.getOrDefault("key", 0);    // 없으면 기본값
map.containsKey("key");        // 존재 여부

// Set (HashSet) 
Set<Integer> set = new HashSet<>(); 
set.add(1); // 추가 (중복이면 무시) 
set.contains(1); // 존재 여부 
set.remove(1); // 삭제 
set.size(); // 크기

// 스택
Stack<Integer> stack = new Stack<>();
stack.push(1);                 // 추가
stack.pop();                   // 꺼내기
stack.peek();                  // 확인만
stack.isEmpty();               // 비었는지

// 큐
Queue<Integer> queue = new LinkedList<>();
queue.add(1);                  // 추가
queue.poll();                  // 꺼내기
queue.peek();                  // 확인만
queue.isEmpty();               // 비었는지

// 문자열
String s = "hello";
s.length();                    // 길이
s.charAt(0);                   // 문자 접근
s.substring(0, 3);             // 부분 문자열
s.equals("hello");             // 비교 (== 쓰지 말 것!)
s.toCharArray();               // char 배열로

// Math
Math.max(a, b);                // 최댓값
Math.min(a, b);                // 최솟값
Math.abs(a);                   // 절대값
Math.pow(2, 3);                // 거듭제곱

// 최대/최소 초기값
int max = Integer.MIN_VALUE;   // -2147483648
int min = Integer.MAX_VALUE;   //  2147483647

// ===== 약수/배수/소수 패턴 =====

// 약수 구하기 (√n 공식)
List<Integer> divisors = new ArrayList<>();
for (int i = 1; i * i <= num; i++) {
    if (num % i == 0) {
        divisors.add(i);
        if (i != num / i) divisors.add(num / i);
    }
}

// 최대공약수 (GCD) - 유클리드 호제법
int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

// 최소공배수 (LCM)
int lcm(int a, int b) {
    return a / gcd(a, b) * b;
}

// 소수 판별
boolean isPrime(int n) {
    if (n < 2) return false;
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) return false;
    }
    return true;
}

// 에라토스테네스의 체 (1~n까지 소수 나열)
boolean[] sieve = new boolean[n + 1];
Arrays.fill(sieve, true);
sieve[0] = sieve[1] = false;
for (int i = 2; i * i <= n; i++) {
    if (sieve[i]) {
        for (int j = i * i; j <= n; j += i) {
            sieve[j] = false;
        }
    }
}
// sieve[i] == true면 소수
```