## useReducer

지금까지는 `useState`를 사용해 상태관리 로직을 컴포넌트 내부에 사용했었습니다.

`useReducer`를 사용하면 상태 관리 로직을 컴포넌트에서 분리시킬 수 있습니다.

즉, 상태 관리만을 위한 파일에 작성 후, import로 불러와서 사용할 수 있다는 의미입니다.

<br>

> **reducer란?**

`Reducer`란 현재 상태와 액션 객체를 파라미터로 받아와서 새로운 상태를 반환해주는 함수입니다.

reducer 에서 반환하는 상태는 곧 컴포넌트가 지닐 새로운 상태가 됩니다.

여기서 `action` 은 업데이트를 위한 정보를 가지고 있습니다. 주로 `type` 값을 지닌 객체 형태로 사용하지만, 꼭 따라야 할 규칙은 따로 없습니다.

```ts
type Action =
    | { type: 'LOGIN_SUCCESS'; payload: { userId: string } }
    | { type: 'LOGIN_FAILURE'; payload: { error: string } };

function reducer(state: State, action: Action): State {
    switch (action.type) {
        case 'LOGIN_SUCCESS':
            // 로그인 성공 시 상태 변경 로직  
            // action.payload.userId를 사용하여 상태를 업데이트  
            return nextState;

        case 'LOGIN_FAILURE':
            // 로그인 실패 시 상태 변경 로직  
            // action.payload.error를 사용하여 상태를 업데이트  
            return nextState;

        default:
            return state;
    }
}
```

<br>

**`State & Action`**

상태의 타입을 나타내며, `Action`은 가능한 모든 액션의 타입을 나타냅니다.

각 액션에 대한 타입과 payload의 타입을 적절히 정의하여 사용하면 됩니다.

<br>

**`Payload`**

액션 객체 안에 포함되는 데이터를 가리키는 용어입니다.

액션은 어떤 종류의 변화가 일어나야 하는지를 나타내는 객체입니다. 그리고 이 액션에 따라 상태(state)를 업데이트하기 위해 필요한 데이터는 `payload`라는 속성에 담겨집니다.

<br>

예를 들어, 사용자가 로그인하는 액션을 생각해보겠습니다.

이 액션은 로그인 성공 또는 실패에 따라 상태를 업데이트해야 할 것입니다.

<br>

위 예시에서 `payload`는 로그인 성공 시에는 `userId`를, 로그인 실패 시에는 `error`를 담고 있습니다.

액션 타입마다 어떤 데이터가 필요한지에 따라 `payload`의 구조가 다를 수 있습니다.

이를 통해 리듀서는 액션에 따른 적절한 상태 업데이트를 수행하게 됩니다.

<br>

**그럼 이제 Reducer를 알았으니 `useReducer`의 사용법을 알아보겠습니다.**

```ts
const [state, dispatch] = useReducer(reducer, initialState);  
```

여기서 `state` 는 컴포넌트에서 사용 할 수 있는 상태를 가르키게 되고, `dispatch` 는 액션을 발생시키는 함수라고 이해하면 됩니다.

이 함수는 다음과 같이 사용합니다: `dispatch({ type: 'INCREMENT' })`.

그리고 `useReducer` 에 넣는 첫번째 파라미터는 reducer 함수이고, 두번째 파라미터는 초기 상태입니다.

```tsx
import React, { useReducer } from 'react';  
  
interface State {  
    // 상태의 타입 정의  
}  
  
type Action =  
    | { type: 'ACTION_TYPE_1'; payload: /* payload의 타입 */ }  
    | { type: 'ACTION_TYPE_2'; payload: /* payload의 타입 */ }  
// 다른 액션들 추가  
  
function reducer(state: State, action: Action): State {  
    switch (action.type) {  
        case 'ACTION_TYPE_1':  
            // ACTION_TYPE_1에 따른 상태 변경 로직  
            // const nextState = ...  
            return nextState;  
  
        case 'ACTION_TYPE_2':  
            // ACTION_TYPE_2에 따른 상태 변경 로직  
            // const nextState = ...  
            return nextState;  
  
        // 다른 case 추가  
  
        default:  
            return state;  
    }  
}  
  
const initialState: State = {  
    // 초기 상태 값  
};  
  
const YourComponent: React.FC = () => {  
    const [state, dispatch] = useReducer(reducer, initialState);  
  
    // ... 컴포넌트의 나머지 코드 ...  
    return (  
        <div>  
            {/* 컴포넌트 JSX 내용 */}  
        </div>  
    );  
};  
  
export default YourComponent;
```

<br>

예시를 적용하면 이런 형태가 됩니다.

```tsx
import React, { useReducer } from 'react';  
  
interface State {  
    count: number;  
}  
  
type Action =  
    | { type: 'increment' }  
    | { type: 'decrement' }  
    | { type: 'reset' };  
  
const initialState: State = { count: 0 };  
  
function reducer(state: State, action: Action): State {  
    switch (action.type) {  
        case 'increment':  
            return { count: state.count + 1 };  
        case 'decrement':  
            return { count: state.count - 1 };  
        case 'reset':  
            return initialState;  
        default:  
            throw new Error('Unhandled action type');  
    }  
}  
  
function Counter() {  
    const [state, dispatch] = useReducer(reducer, initialState);  
  
    return (  
        <div>  
            Count: {state.count}  
            <button onClick={() => dispatch({ type: 'increment' })}>Increment</button>  
            <button onClick={() => dispatch({ type: 'decrement' })}>Decrement</button>  
            <button onClick={() => dispatch({ type: 'reset' })}>Reset</button>  
        </div>  
    );  
}  
  
export default Counter;
