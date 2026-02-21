## 1.개요
Big O 표기법을 코딩테스트를 준비하며 경험해보았지만 제대로된 이해가 되지 않았습니다. 피드백을 받은대로 Big O 표기법은 무엇이며, 왜 사용을 하며, 어떻게 사용을 하는지 선행 학습하기 위한 문서입니다.

## 2.Big O 표기법이란?
우선 Big O 표기법을 사용하면 시간 복잡도를 빠르게 설명할 수 있습니다. 여기서 시간이란 초(second)나 분(minute)를 뜻하는 것이 아닙니다. 프로그래밍에서 어떠한 프로세스를 완료하기 위해 같은 동작이지만 알고리즘에 따라 10번의 절차가 필요하고, 5번의 절차가 필요하다면 5번의 절차로 완료하는것이 더 적합한 알고리즘이라고 볼 수 있습니다. 이 수치를 시간 복잡도라고 합니다.

- ### Big O 표기 예시
1. 아래 코드와 같이 항상 동일한 절차가 필요하다면 Constant Time이며 O(1)로 표현 할 수 있습니다.
```java
	printFirst(arr);
	printFirst(arr); 
	public void printFirst(List<String> arr) {
		System.out.print(arr.get(0));
	}
```
2. 아래 코드와 같이 인풋의 증가 만큼 절차가 증가한다면 O(n)으로 표기 할 수 있습니다.
```java
	printFirst(arr);
	public void printFirst(List<String> arr) {
		for (String text : arr) {
			System.out.print(text);
		}
		for (String text : arr) {
			System.out.print(text);
		}
	}
```
3. 아래 코드와 같이 인풋의 증가 만큼 전차가 제곱만큼 증가한다면 O(n^2)으로 표기 할 수 있습니다.
```java
	printFirst(arr);
	public void printAll(List<List<String>> arr) {
		for (int x = 0; x < arr.size(); x++) {
			for (int y = 0; y < arr.get(x).size(); y++) {
				System.out.ptrintln(arr.get(x).get(y));
			}
		}
	}
```
4. 아래 코드와 같이 인풋이 2배로 커지더라도 절차는 하나만 증가한다면 O(log n)으로 표기 할 수 있습니다.
```java
	int n = arr.length; 
	while (n > 1) { 
		n = n / 2; 
	}
```
