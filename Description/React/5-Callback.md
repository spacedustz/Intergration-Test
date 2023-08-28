## useCallback

`useCallback`은 `useMemo`와 비슷한 Hook입니다.

`useMemo`는 특정 결과값을 재사용 할 때 사용하는 반면, `useCallback`은 특정 함수를 재사용 하고 싶을 때 사용합니다.

<br>

예시로 아래의 세 함수 `onCreate, onRemove, onToggle`을 보겠습니다.

```tsx
import React, { useState, useRef } from 'react';  
  
interface User {  
    id: number;  
    username: string;  
    email: string;  
    active?: boolean;  
}  
  
const App: React.FC = () => {  
    const nextId = useRef(1);  
    const [users, setUsers] = useState<User[]>([]);  
    const [inputs, setInputs] = useState({ username: '', email: '' });  
  
    const onCreate = () => {  
        const user: User = {  
            id: nextId.current,  
            username: inputs.username,  
            email: inputs.email  
        };  
        setUsers(prevUsers => [...prevUsers, user]);  
  
        setInputs({  
            username: '',  
            email: ''  
        });  
        nextId.current += 1;  
    };  
  
    const onRemove = (id: number) => {  
        setUsers(prevUsers => prevUsers.filter(user => user.id !== id));  
    };  
  
    const onToggle = (id: number) => {  
        setUsers(prevUsers =>  
            prevUsers.map(user =>  
                user.id === id ? { ...user, active: !user.active } : user  
            )  
        );  
    };  
  
    return (  
        <div>  
            {/* 컴포넌트 JSX 내용 */}  
        </div>  
    );  
};  
  
export default App;
```

위 함수들은 컴포넌트가 리렌더링 될 때 마다 새로 만들어집니다.

함수를 선언하는 것 자체는 사실 메모리도, CPU 도 리소스를 많이 차지 하는 작업은 아니기 때문에 함수를 새로 선언한다고 해서,

그 자체 만으로 큰 부하가 생길일은 없지만, 한번 만든 함수를 필요할때만 새로 만들고 재사용하는 것은 여전히 중요합니다.

그 이유는, 나중에 컴포넌트에서 `props` 가 바뀌지 않았으면 Virtual DOM 에 새로 렌더링하는 것 조차 하지 않고 컴포넌트의 결과물을 재사용 하는 최적화 작업을 할때 함수를 재사용하는것이 필수입니다.

<br>

useCallback 은 이런식으로 사용합니다.

```tsx
import React, { useState, useRef, useCallback } from 'react';  
  
interface User {  
    id: number;  
    username: string;  
    email: string;  
    active: boolean;  
}  
  
interface InputValues {  
    username: string;  
    email: string;  
}  
  
function App() {  

	// ... 나머지 코드

    const onCreate = useCallback(() => {  
        const user: User = {  
            id: nextId.current,  
            username,  
            email,  
            active: true  
        };  
        setUsers(prevUsers => prevUsers.concat(user));  
  
        setInputs({  
            username: '',  
            email: ''  
        });  
        nextId.current += 1;  
    }, [username, email]);  
  
    const onRemove = useCallback(  
        (id: number) => {  
            setUsers(prevUsers => prevUsers.filter(user => user.id !== id));  
        },  
        [users]  
    );  
  
    const onToggle = useCallback(  
        (id: number) => {  
            setUsers(prevUsers =>  
                prevUsers.map(user =>  
                    user.id === id ? { ...user, active: !user.active } : user  
                )  
            );  
        },  
        [users]  
    );  
  
    const count = users.filter(user => user.active).length;  
  
    return (  
        <>  
            {/* 여기에 CreateUser와 UserList 컴포넌트 사용 */}  
            <div>활성사용자 수 : {count}</div>  
        </>  
    );  
}  
  
export default App;
```

주의할 점은, 함수 안에서 사용하는 상태 혹은 props 가 있다면 꼭, `deps` 배열안에 포함시켜야 된다는 것 입니다.

만약에 `deps` 배열 안에 함수에서 사용하는 값을 넣지 않게 된다면, 함수 내에서 해당 값들을 참조할때 가장 최신 값을 참조 할 것이라고 보장 할 수 없습니다.

props 로 받아온 함수가 있다면, 이 또한 `deps` 에 넣어주어야 해요.

사실, `useCallback` 은 `useMemo` 를 기반으로 만들어졌습니다.

다만, 함수를 위해서 사용 할 때 더욱 편하게 해준 것 뿐이지요. 이런식으로도 표현 할 수 있습니다.
