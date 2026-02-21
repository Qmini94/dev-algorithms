## 알고리즘이란?

**알고리즘(Algorithm)**은 문제를 해결하기 위한 **명확한 절차**와 **단계적 방법**입니다.
요리 레시피처럼, 입력을 받아 원하는 출력을 만들어내는 일련의 과정이라고 할 수 있습니다.

```
입력(Input) → [알고리즘] → 출력(Output)
```

---

## 왜 알고리즘을 공부해야 할까?

### 1. 문제 해결 능력 향상

알고리즘은 단순히 코드를 작성하는 것이 아니라, **논리적으로 사고하는 방법**을 배우는 과정입니다.

**예시: 친구 찾기**
```java
// 방법 1: 처음부터 끝까지 찾기 (선형 탐색)
// 1000명 중에서 최악의 경우 1000번 확인

// 방법 2: 이름 순으로 정렬 후 이진 탐색
// 1000명 중에서 최대 10번만 확인!

// 같은 문제, 다른 접근 → 100배 차이
```

### 2. 효율적인 코드 작성

**비효율적인 코드**는 작은 데이터에서는 괜찮지만, 데이터가 커지면 문제가 됩니다.

```java
// 나쁜 예: O(n²) - 중복 제거
List<Integer> removeDuplicates(List<Integer> list) {
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
        boolean isDuplicate = false;
        for (int j = 0; j < result.size(); j++) {  // 매번 전체 검사!
            if (list.get(i).equals(result.get(j))) {
                isDuplicate = true;
                break;
            }
        }
        if (!isDuplicate) {
            result.add(list.get(i));
        }
    }
    return result;
}

// 좋은 예: O(n) - HashSet 활용
Set<Integer> removeDuplicates(List<Integer> list) {
    return new HashSet<>(list);  // 한 줄로 해결!
}
```

**실행 시간 비교**:
- 데이터 1,000개: 나쁜 예 0.5초 vs 좋은 예 0.001초
- 데이터 100,000개: 나쁜 예 50초 vs 좋은 예 0.01초

### 3. 취업과 면접
대부분의 IT 기업은 **코딩테스트**와 **기술 면접**에서 알고리즘을 평가합니다.
**왜 알고리즘으로 평가할까?**
- 논리적 사고력 측정
- 코드 품질 파악
- 복잡한 문제 해결 능력 확인
- 학습 능력과 성장 가능성 판단

### 4. 실무에서의 활용

알고리즘은 학문적인 것이 아니라 **실제 서비스에 직접 적용**됩니다.

| 서비스 기능 | 사용되는 알고리즘 |
|------------|-----------------|
| 유튜브 추천 시스템 | 그래프, 추천 알고리즘 |
| 네이버 지도 길찾기 | 다익스트라, A* 알고리즘 |
| 구글 검색 엔진 | 페이지랭크, 문자열 매칭 |
| 넷플릭스 영상 압축 | 동적 프로그래밍 |
| 카카오톡 메시지 전송 | 큐, 해시 테이블 |
| 배달의민족 배달 최적화 | 그리디, 동적 프로그래밍 |

**실무 예시: 친구 추천 기능**
```java
// SNS의 "알 수도 있는 친구" 기능
// → 그래프 알고리즘 (공통 친구 찾기)

class FriendRecommendation {
    // 2촌 관계에서 아직 친구가 아닌 사람 찾기
    List<User> recommendFriends(User user) {
        Set<User> friends = user.getFriends();
        Map<User, Integer> mutualFriendsCount = new HashMap<>();
        
        // 친구의 친구 탐색 (BFS/DFS)
        for (User friend : friends) {
            for (User friendOfFriend : friend.getFriends()) {
                if (!friends.contains(friendOfFriend) 
                    && !friendOfFriend.equals(user)) {
                    mutualFriendsCount.put(
                        friendOfFriend,
                        mutualFriendsCount.getOrDefault(friendOfFriend, 0) + 1
                    );
                }
            }
        }
        
        // 공통 친구가 많은 순으로 정렬 (정렬 알고리즘)
        return mutualFriendsCount.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(10)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
```

---

## 알고리즘 vs 자료구조

### 자료구조 (Data Structure)
**데이터를 저장하고 관리하는 방법**

```java
// 배열: 순서대로 저장
int[] numbers = {1, 2, 3, 4, 5};

// 리스트: 동적 크기
List<String> names = new ArrayList<>();

// 맵: 키-값 쌍으로 저장
Map<String, Integer> scores = new HashMap<>();

// 큐: 선입선출(FIFO)
Queue<Integer> queue = new LinkedList<>();

// 스택: 후입선출(LIFO)
Stack<Integer> stack = new Stack<>();
```

### 알고리즘 (Algorithm)
**데이터를 처리하는 방법**

```java
// 정렬 알고리즘
Arrays.sort(numbers);

// 검색 알고리즘
int index = Arrays.binarySearch(numbers, 3);

// 그래프 탐색 알고리즘
void bfs(Graph graph, Node start) { ... }
void dfs(Graph graph, Node start) { ... }
```

**둘의 관계**:
> 자료구조는 **재료와 도구**, 알고리즘은 **요리법**

---

## 주요 알고리즘 카테고리

### 1. 정렬 (Sorting)
데이터를 특정 순서로 배열

```java
// 버블 정렬, 선택 정렬, 삽입 정렬: O(n²)
// 병합 정렬, 퀵 정렬, 힙 정렬: O(n log n)

int[] arr = {5, 2, 8, 1, 9};
Arrays.sort(arr);  // [1, 2, 5, 8, 9]
```

### 2. 탐색 (Searching)
데이터에서 특정 값 찾기

```java
// 선형 탐색: O(n)
// 이진 탐색: O(log n) - 정렬된 배열에서만

int[] sortedArr = {1, 3, 5, 7, 9};
int index = Arrays.binarySearch(sortedArr, 7);  // index = 3
```

### 3. 그래프 (Graph)
네트워크 구조 문제 해결

```java
// BFS (너비 우선 탐색): 최단 거리
// DFS (깊이 우선 탐색): 모든 경로 탐색
// 다익스트라: 최단 경로 (가중치 그래프)

// 예: 미로 탈출, SNS 친구 관계, 지도 길찾기
```

### 4. 동적 프로그래밍 (Dynamic Programming)
중복 계산을 피해 효율성 높이기

```java
// 피보나치, 배낭 문제, 최장 공통 부분 수열

// Before: O(2^n)
int fib(int n) {
    if (n <= 1) return n;
    return fib(n-1) + fib(n-2);
}

// After: O(n)
int fibDP(int n) {
    int[] dp = new int[n+1];
    dp[0] = 0; dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2];
    }
    return dp[n];
}
```

### 5. 그리디 (Greedy)
매 순간 최선의 선택

```java
// 거스름돈 문제, 회의실 배정, 최소 신장 트리

// 예: 500원, 100원, 50원, 10원으로 거스름돈 주기
int[] coins = {500, 100, 50, 10};
int change = 1260;
int count = 0;

for (int coin : coins) {
    count += change / coin;
    change %= coin;
}
// 500원 2개 + 100원 2개 + 50원 1개 + 10원 1개 = 6개
```

---


**관련 문서**:
- [빅O(Big-O) 표기법](Big_O.md)