# 문제 풀이 기록

작성자: 박규민  
작성일: 2026-02-21

---

## 문제 정보

- URL: https://school.programmers.co.kr/learn/courses/30/lessons/42576?language=java
- 문제 이름: 완주하지 못한 선수
- 난이도: Lv1
- 클래스명: UnfinishedRunner

---

## 문제 정의

- 문제 요약: 마라톤에 참여한 선수들의 이름이 담긴 배열과 완주한 선수들의 이름이 담긴 배열이 주어질때, 완주하지 못한 선수를 구한다.
- 입력: 선수들의 이름이 담긴 배열, 완주한 선수들의 이름이 담긴 배열
- 출력:  완주하지 못한 선수 이름.
- 엣지케이스: 동명이인이 있울 수 있기 때문에 동명이인중에 한명만 완주했을 경우가 있음.

---

## Constraint 분석

| 항목       | 값               |
| -------- | --------------- |
| 입력 크기    | 1 ≤ n ≤ 100,000 |
| 시간 제한    | 명시 없음           |
| 메모리 제한   | 명시 없음           |
| 예상 시간복잡도 | O(n)            |

---

## 1차 접근 (브루트포스)

아이디어  
-  두개의 배열을 비교해야하는데 문자열이기 때문에 HashMap에 완료자 전체를 담으면서 동명이인일 경우 value값에 완주한 동명이인 수 만큼 더한다. 참가자 전체를 반복문을 돌리면서 완주자에 없다면 해당 이름의 value가 0이면 리턴 아니라면 -1 

시간복잡도  
-  두개의 배열이라 이중반복문을 사용해서 O(n^2)으로 구현할 수도 있지만. 공부한 내용에서 이런 경우 HashMap을 활용하여 O(n)으로 구현 가능하다는점을 인지.

구현 시 아쉬웠던 점.
-  풀이 방식을 잘못 생각해서 완료자를 map에 담는 것이 아니라 참가자를 map에 담아서 구현중에 당황하며 시간을 허비함. 

```java
public String solution(String[] participant, String[] completion) {  
    Map<String, Integer> completionMap = new HashMap<>();  
    String UnfinishedName = "";  
  
    /**  
     *  완주자를 map에 답는다. 엣지케이스를 생각해서 value값에 동명이인일 경우 +1  
     */    for (String name : completion) {  
        if (completionMap.containsKey(name)) {  
            completionMap.put(name, completionMap.get(name) + 1);  
        } else {  
            completionMap.put(name, 1);  
        }  
    }  
  
    /**  
     * 참가자 배열을 인덱스로 돌면서 완주자에 없다면 바로 return하거나 동명이인일 경우 -1  
     * 해당 이름에 완주자가 없어 0이라면 return  
     */    for (int i = 0; i < participant.length; i++) {  
        if (!completionMap.containsKey(participant[i])) return participant[i];  
  
        if(completionMap.get(participant[i]) != 0) {  
            completionMap.put(participant[i], completionMap.get(participant[i]) - 1);  
        } else {  
            UnfinishedName = participant[i];  
        }  
    }  
    return UnfinishedName;  
}
```

---

## 2차 접근 (개선 시도)

아이디어  
-  

개선 포인트  
-  

시간복잡도  
-  

```java
```

---

## 최적 풀이 (Solution)

핵심 알고리즘  
-  

자료구조 선택 이유  
-  

시간복잡도  
-  

공간복잡도  
-  

```java
```

---

## 배운 점

- 실수한 부분:
- 다음에 같은 유형 만나면 체크할 것:

---

## 테스트 케이스

| 입력 | 예상 출력 | 결과 |
|------|-----------|------|
|      |           |      |
|      |           |      |
