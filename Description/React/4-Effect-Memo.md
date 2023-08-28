## useEffect

`useEffect`는 마운트/언마운트/업데이트 시 할 작업을 설정할 수 있는 LifeCycle Hook입니다.

`useEffect`는 2개의 파라미터를 받습니다.

- 1번 파라미터 : 함수(effect)
- 2번 파라미터 : 배열(deps)

<br>

1번째 파라미터는 단순히 실행시킬 함수를 등록하면 됩니다.

2번쨰 파라미터인 배열이 **빈 배열이라면 컴포넌트가 마운트 될 시 에만 적용**이 됩니다.

```tsx
useEffect(() => {
	// 1. 실행할 함수,
	// 2. 빈 배열
});
```

<br>

그리고 **배열에 특정 배열을 넣을 경우**, 해당 배열이 업데이트 될 때만 1번째 파라미터인 함수가 실행됩니다.

```tsx
useEffect(() => {
	// 1. 실행할 함수,
	// 2. 특정 배열
});
```

<br>
**※ cleanup 함수**  

- useEffect 안에서 return 할 때 실행 된다.(useEffcet의 뒷정리를 한다.)
- 만약 컴포넌트가 마운트 될 때 이벤트 리스너를 통해 이벤트를 추가하였다면 컴포넌트가 언마운트 될 때 이벤트를 삭제 해주어야 한다.

그렇지 않으면 컴포넌트가 리렌더링 될 때마다 새로운 이벤트 리스너가 핸들러에 바인딩 될 것이다. 이는 자주 리렌더링 될 경우 메모리 누수가 발생할 수 있다.

```tsx
useEffect(() => {
 // 함수 처리부
 return () => {
	 // cleanup
 }
});
```

---

## useMemo

`useMemo` - 성능 최적화를 위하여 연산된 값을 `useMemo`라는 Hook 을 사용하여 재사용하는 방법을 알아보도록 하겠습니다.

예를 들어 유저의 필드 중 `active: true` 인 것들만 찾아서 렌더링 한다고 했을때 active 값이 true인 사용자를 찾는 예시를 보겠습니다.

```tsx
const countActiveUsers(users: User[]): number {
	console.log('활성화 상태인 사용자 수를 세는중...');
	return users.filter(user => user.active).length;
}
```

위 코드에서 활성 사용자 수를 세는건 users 에 변화가 있을때만 세야되는건데 input 값이 바뀔 때에도

컴포넌트가 리렌더링 되므로 이렇게 불필요할때에도 호출하여서 자원이 낭비되고 있습니다.

<br>

이러한 상황에서 `useMemo`라는 Hook 함수를 사용하여 성능을 최적화 할 수 있습니다.

Memo는 `memoized`를 의미하는데, 이전에 계산한 값을 재사용한다는 의미를 가지고 있습니다.

```tsx
const countActiveUsers(users: User[]): number {
	console.log('활성화 상태인 사용자 수를 세는중...');
	return users.filter(user => user.active).length;
}

const count = useMemo(() => countActiveUsers(users), [users]);
```

`useMemo` 의 첫번째 파라미터에는 어떻게 연산할지 정의하는 함수를 넣어주면 됩니다.

두번째 파라미터에는 deps 배열을 넣어주면 되는데 이 배열 안에 넣은 내용이 바뀌면,

등록한 함수를 호출해서 값을 연산해주고, 만약에 내용이 바뀌지 않았다면 이전에 연산한 값을 재사용하게 됩니다.