## 개요
빅O 표기법은 알고리즘의 시간 복잡도와 공간 복잡도를 표현하는 방법입니다.
입력 크기(n)가 증가할 때 알고리즘의 실행 시간이나 메모리 사용량이 어떻게 증가하는지를 나타냅니다.

---

## 빅O 사용시 장점
- 효율적인 코드 작성: 같은 문제를 푸는 여러 방법 중 가장 효율적인 방법을 선택
- 확장성 예측: 데이터가 커졌을 때 알고리즘이 감당할 수 있는지 판단

---

## 주요 시간 복잡도
### O(1) - 상수 시간
입력 크기와 관계없이 **항상 일정한 시간**이 걸립니다.

```java
// 배열의 특정 인덱스 접근
int getElement(int[] arr, int index) {
    return arr[index];  // O(1)
}

// HashMap의 get/put
Map<String, Integer> map = new HashMap<>();
map.put("key", 1);     // O(1)
int value = map.get("key");  // O(1)
```

### O(log n) - 로그 시간
입력 크기가 2배 증가해도 **단계가 1만 증가**합니다.

```java
// 이진 탐색 (Binary Search)
int binarySearch(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) return mid;
        else if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
// n=1000 → 약 10번, n=1,000,000 → 약 20번
```

### O(n) - 선형 시간
입력 크기에 **비례**하여 시간이 증가합니다.

```java
// 배열 순회
int findMax(int[] arr) {
    int max = arr[0];
    for (int num : arr) {  // O(n)
        if (num > max) max = num;
    }
    return max;
}

// 리스트 검색
boolean contains(List<Integer> list, int target) {
    for (int num : list) {  // O(n)
        if (num == target) return true;
    }
    return false;
}
```

### O(n log n) - 선형 로그 시간
효율적인 **정렬 알고리즘**의 평균 시간 복잡도입니다.

```java
// 병합 정렬, 퀵 정렬(평균), 힙 정렬
import java.util.Arrays;

void efficientSort(int[] arr) {
    Arrays.sort(arr);  // O(n log n) - Dual-Pivot Quicksort
}

// 병합 정렬 구현
void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);      // 분할
        mergeSort(arr, mid + 1, right); // 분할
        merge(arr, left, mid, right);   // 병합
    }
}
```

### O(n²) - 이차 시간
**중첩 반복문**을 사용할 때 주로 나타납니다.

```java
// 버블 정렬
void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {           // O(n)
        for (int j = 0; j < n - i - 1; j++) {   // O(n)
            if (arr[j] > arr[j + 1]) {
                // swap
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// 2차원 배열 순회
void printMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {      // O(n)
        for (int j = 0; j < matrix[i].length; j++) { // O(n)
            System.out.print(matrix[i][j] + " ");
        }
    }
}
```

### O(2ⁿ) - 지수 시간
입력이 1 증가할 때마다 시간이 **2배**씩 증가합니다. 매우 비효율적!

```java
// 피보나치 수열 (재귀, 비효율적)
int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);  // O(2^n)
}
// n=30이면 약 10억번 연산!

// 개선 버전 (동적 프로그래밍)
int fibonacciDP(int n) {
    if (n <= 1) return n;
    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];  // O(n)
    }
    return dp[n];
}
```

---

## 시간 복잡도 비교표

| 표기법 | 이름 | n=10 | n=100 | n=1,000 | 예시 |
|--------|------|------|-------|---------|------|
| O(1) | 상수 | 1 | 1 | 1 | 배열 인덱스 접근 |
| O(log n) | 로그 | 3 | 7 | 10 | 이진 탐색 |
| O(n) | 선형 | 10 | 100 | 1,000 | 선형 탐색 |
| O(n log n) | 선형 로그 | 30 | 700 | 10,000 | 효율적인 정렬 |
| O(n²) | 이차 | 100 | 10,000 | 1,000,000 | 버블 정렬 |
| O(2ⁿ) | 지수 | 1,024 | 1.27×10³⁰ | - | 비효율적 재귀 |

---

## 공간 복잡도

알고리즘이 사용하는 **메모리 공간**을 나타냅니다.

```java
// O(1) 공간
int sum(int[] arr) {
    int total = 0;  // 변수 1개만 사용
    for (int num : arr) {
        total += num;
    }
    return total;
}

// O(n) 공간
int[] copyArray(int[] arr) {
    int[] copy = new int[arr.length];  // 새 배열 생성
    for (int i = 0; i < arr.length; i++) {
        copy[i] = arr[i];
    }
    return copy;
}

// O(n) 공간 - 재귀 호출 스택
int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);  // 호출 스택 n개
}
```

---

## 복잡도 계산 팁

### 1. 최고차항만 고려
```java
// O(3n² + 2n + 1) → O(n²)
for (int i = 0; i < n; i++) {           // n
    for (int j = 0; j < n; j++) {       // n
        for (int k = 0; k < 3; k++) {   // 상수
            System.out.println(i + j + k);
        }
    }
}
```

### 2. 상수는 무시
```java
// O(5n) → O(n)
for (int i = 0; i < n; i++) {
    System.out.println(i);
}
for (int i = 0; i < n; i++) {
    System.out.println(i * 2);
}
```

### 3. 중첩 반복문은 곱하기
```java
// O(n) × O(m) = O(n×m)
for (int i = 0; i < n; i++) {
    for (int j = 0; j < m; j++) {
        // ...
    }
}
```

### 4. 연속된 반복문은 더하기
```java
// O(n) + O(m) = O(n+m)
for (int i = 0; i < n; i++) {
    // ...
}
for (int j = 0; j < m; j++) {
    // ...
}
```

---

## 자주 사용하는 자료구조의 시간 복잡도

### ArrayList
- 접근: O(1)
- 삽입/삭제 (끝): O(1)
- 삽입/삭제 (중간): O(n)
- 검색: O(n)

### LinkedList
- 접근: O(n)
- 삽입/삭제 (처음/끝): O(1)
- 삽입/삭제 (중간): O(n)
- 검색: O(n)

### HashMap
- 삽입: O(1) 평균
- 삭제: O(1) 평균
- 검색: O(1) 평균

### TreeMap
- 삽입: O(log n)
- 삭제: O(log n)
- 검색: O(log n)

### PriorityQueue (Heap)
- 삽입: O(log n)
- 삭제 (최소/최대): O(log n)
- 조회 (최소/최대): O(1)

---

## 코딩테스트에서의 시간 복잡도 기준

일반적으로 **1초당 약 1억(10⁸)번 연산**을 기준으로 합니다.

| 입력 크기(n) | 허용 가능한 시간 복잡도 |
|-------------|---------------------|
| n ≤ 10 | O(n!), O(2ⁿ) |
| n ≤ 20 | O(2ⁿ) |
| n ≤ 100 | O(n³) |
| n ≤ 1,000 | O(n²) |
| n ≤ 10,000 | O(n²) (타이트) |
| n ≤ 100,000 | O(n log n) |
| n ≤ 1,000,000 | O(n) |
| n > 1,000,000 | O(log n), O(1) |

---

## 최적화 전략

### 1. 불필요한 연산 제거
```java
// Before: O(n²)
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        if (i == j) continue;
        // ...
    }
}

// After: O(n²) but 절반으로 감소
for (int i = 0; i < n; i++) {
    for (int j = i + 1; j < n; j++) {
        // ...
    }
}
```

### 2. 적절한 자료구조 선택
```java
// Before: O(n²) - 중복 검사
List<Integer> list = new ArrayList<>();
for (int num : array) {
    if (!list.contains(num)) {  // O(n)
        list.add(num);
    }
}

// After: O(n) - HashSet 사용
Set<Integer> set = new HashSet<>();
for (int num : array) {
    set.add(num);  // O(1)
}
```

### 3. 미리 계산(Preprocessing)
```java
// Before: O(n) × 쿼리 수
int rangeSum(int[] arr, int left, int right) {
    int sum = 0;
    for (int i = left; i <= right; i++) {
        sum += arr[i];
    }
    return sum;
}

// After: O(1) × 쿼리 수 (전처리 O(n))
int[] prefixSum = new int[arr.length + 1];
for (int i = 0; i < arr.length; i++) {
    prefixSum[i + 1] = prefixSum[i] + arr[i];
}
// 구간 합: prefixSum[right + 1] - prefixSum[left]
```

---

## 마치며

빅O 표기법은 알고리즘의 효율성을 객관적으로 평가하는 도구입니다.
코딩테스트에서는 문제의 제약조건을 보고 필요한 시간 복잡도를 역산한 후,
그에 맞는 알고리즘을 선택하는 것이 중요합니다.

**핵심 원칙**:
- 입력 크기를 확인하라
- 시간 제한을 고려하라
- 적절한 알고리즘과 자료구조를 선택하라
- 최적화가 필요한지 판단하라

---

**관련 문서**:
- [왜 알고리즘을 사용할까](why-algorithms.md)

