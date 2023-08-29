## Context API

React에서 Context API란 상위 컴포넌트에서 하위 컴포넌트로 데이터를 전달하기 위한 메커니즘을 제공합니다.

Context API 를 사용하면, 프로젝트 안에서 전역적으로 사용 할 수 있는 값을 관리 할 수 있습니다.

<br>

우선, Context API 를 사용해서 새로운 Context 를 만드는 방법을 알아보겠습니다.

Context 를 만들 땐 다음과 같이 `React.createContext()` 라는 함수를 사용합니다.

```tsx
type ContextObject = {  
    items: Reactive[];  
    addItem: (text: string) => void;  
    removeItem: (id: string) => void;  
}  
  
const UserDispatch = React.createContext<ContextObject>(null);
```

`createContext` 의 파라미터에는 Context 의 기본값을 설정할 수 있습니다. 여기서 설정하는 값은 Context 를 쓸 때 값을 따로 지정하지 않을 경우 사용되는 기본 값 입니다.

<br>

Context 를 만들면, Context 안에 Provider 라는 컴포넌트가 들어있는데 이 컴포넌트를 통해 Context 의 값을 정할 수 있습니다.

이 컴포넌트를 사용할 때는, `value` 라는 값을 설정해주면 됩니다.

```tsx
<UserDispatch.Provider value={dispatch}>...</UserDispatch.Provider>
```

이렇게 설정해주고 나면 Provider 에 의하여 감싸진 컴포넌트 중 어디서든지 우리가 Context 의 값을 다른 곳에서 바로 조회해서 사용 할 수 있습니다.

<br>

**App.tsx**

App.tsx에 많은 이벤트 핸들러와 변수, 함수 등이 있다고 가정하고 전부 MainContext.tsx로 옮깁니다.

그리고 MainContext에 정의된 ContextProvider를 통해 메인 앱에 출력합니다.

ContextProvider 컴포넌트는 바로 아래에서 설명하겠습니다.

```tsx
import './App.css'  
  
import ReactiveFC from "./components/item/ReactiveFC";  
import RefInput from "./components/item/RefInput";  
import ContextProvider from "./components/context/MainContext";  
  
const App: React.FC = () => {  
    return (  
        <ContextProvider>  
            <RefInput />  
            <ReactiveFC />  
        </ContextProvider>  
    );  
}  
  
export default App
```

<br>

**MainContext.tsx**

App.tsx에 있던 핸들러와 아이템 삭제 함수 등을 전부 `ContextProvider` 컴포넌트에 넣었습니다.

그리고, MainContext를 `React.createContext<ContextObject>`의 하위 컴포넌트로 넣어서,

반환할 때 MainContext 컴포넌트에 `props.children`으로 메인 컴포넌트에 넣어줍니다.

<br>

아래 코드는 React 컴포넌트에서 상태 관리와 컨텍스트를 사용하는 방법을 보여줍니다.

1. `ContextObject` 타입 정의: `items` 배열은 `Reactive` 타입의 요소를 가지며, `addItem`와 `removeItem`은 각각 문자열과 아이템 ID를 매개변수로 받는 함수입니다.

2. `MainContext` 생성: `React.createContext<ContextObject>()`를 사용하여 컨텍스트 객체인 `MainContext`를 생성합니다. 초기값으로는 빈 배열을 가진 `items`, 빈 함수(`addItem`, `removeItem`)가 제공됩니다.

3. `ContextProvider`: 이 함수형 컴포넌트는 상태와 이벤트 핸들러를 관리하고, 해당 값을 컨텍스트로 제공합니다.

    - 상태: 초기값으로 빈 배열인 `item`과 함께 useState 훅을 사용하여 선언됩니다.
    - 아이템 추가 핸들러(`addItemHandler`): 새로운 아이템을 생성한 후, 이전 상태 배열에 새로운 아이템을 추가하는 방식으로 상태 업데이트가 이루어집니다.
    - 아이템 삭제 핸들러(`removeItemHandler`): 주어진 아이디와 일치하지 않는 모든 아이템만 남기고 필터링하여 상태 업데이트가 이루어집니다.
    - contextValue: 위에서 정의한 ContextObject 타입에 따라 현재 상태와 핸들러 함수들을 포함하는 객체입니다.
    - `<MainContext.Provider>`: contextValue 값을 MainContext.Provider의 value 속성에 전달하여 자식 컴포넌트에서 해당 값에 액세스할 수 있도록 합니다.
4. ContextProvider 내보내기: ContextProvider 컴포넌트를 외부에서 임포트할 수 있도록 내보냅니다.


<br>

MainContext.Provider 하위에 있는 자식 컴포넌트에서 useContext(MainContext) 훅을 사용하여 items 배열과 addItem, removeItem 함수에 액세스할 수 있습니다.

```tsx
import React, {useState} from 'react';  
import Reactive from "../../models/data";  
  
type ContextObject = {  
    items: Reactive[];  
    addItem: (text: string) => void;  
    removeItem: (id: string) => void;  
}  
  
// Context Hook을 위해 export 필요  
export const MainContext = React.createContext<ContextObject>({  
    items: [],  
    addItem: () => {},  
    removeItem: () => {}  
});  
  
// Context의 요소를 구성하는 함수형 컴포넌트, Context의 상태를 관리함  
const ContextProvider: React.FC<React.PropsWithChildren> = (props) => {  
  
    // State, RefInput으로 폼 제출하면 여기에 추가 돠어야함  
    const [item, setItem] = useState<Reactive[]>([]);  
  
    // 아이템 추가 핸들러  
    const addItemHandler = (text: string) => {  
        const newItem = new Reactive(text);  
  
        // 이전 상태를 기반으로 상태를 업데이터 하려면 함수 형식을 사용해야 함  
        // concat으로 새로운 Item을 추가한 새 배열 반환  
        setItem((pre) => {  
            return pre.concat(newItem);  
        });  
    };  
  
    // 아이템 삭제 핸들러  
    // 상태는 이전 상태를 기준으로 업데이트 하기 때문에 pre(전) 상태를 파라미터로 받는다  
    const removeItemHandler = (itemId: string) => {  
        setItem((pre) => {  
            // 삭제하려는 itemId가 이전 상태 배열의 아이템 중 일치하는 item이 있다면 삭제  
            return pre.filter(item => item.id !== itemId)  
        });  
    };  
  
    const contextValue: ContextObject = {  
        items: item,  
        addItem: addItemHandler,  
        removeItem: removeItemHandler  
    };  
  
    return <MainContext.Provider value={contextValue}>{props.children}</MainContext.Provider>  
};  
  
export default ContextProvider;
```

<br>

**ReactiveFC.tsx**

기존에 props을 받던것을 userContext를 이용해서 컨텍스트를 받아 props을 context로 대체합니다.

```tsx
import React, {useContext} from "react";  
  
import ReactiveFCItem from "./ReactiveFCItem";  
import {MainContext} from "../context/MainContext";  
  
const Item: React.FC = () => {  
    const context = useContext(MainContext)  
  
    return (  
        <ul>  
            {context.items.map((item) =>  
                <ReactiveFCItem  
                    key={item.id}  
                    text={item.text}  
                    onRemoveItem={context.removeItem.bind(null, item.id)}  
                />  
            )}  
        </ul>  
    )  
}  
  
export default Item;
```

<br>

**RefInput.tsx**

기존에 props을 받던것을 userContext를 이용해서 컨텍스트를 받아 props을 context로 대체합니다.

```tsx
import React, {useRef, useContext} from "react";  
import {MainContext} from "../context/MainContext";  
  
const Input: React.FC = () => {  
    const context = useContext(MainContext);  
  
    // Input Ref  
    const inputRef = useRef<HTMLInputElement>(null);  
  
    // Form 입력 시, Browser Default 방지  
    const submitHandler = (event: React.FormEvent) => {  
        event.preventDefault();  
  
        const enteredText = inputRef.current?.value;  
  
        // Input 검증  
        if (enteredText.trim().length === 0) {  
            // Throw an Error  
            return;  
        }  
  
        context.addItem(enteredText);  
    };  
  
    return <form onSubmit={submitHandler}>  
        <label htmlFor="text">Text Here</label>  
        <input type="text" id="text" ref={inputRef} />  
        <button>Add Item</button>  
    </form>  
}  
  
export default Input;
```