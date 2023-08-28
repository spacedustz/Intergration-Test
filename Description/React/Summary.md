## React

React 핵심만 정리합니다.

[My Github Repository](https://github.com/spacedustz/Intergration-Test/)

<br>

**Solid Foundation**

- Components & JSX
- Props
- State & Events
- Outputting Content
- Styling
- Hooks
- Debugging

<br>

**Advanced Concepts**

- Refs & Portals
- Behind the Scenes
- HTTP Requests
- Side Effects
- Context API
- Authentication
- Advanced Hooks
- Redux
- Unit Testing
- Custom Hook
- Routing
- Next.js

---

## Project 생성 & 세팅

Browser-Based Setup(CodeSandbox&Similar) 방식은 제외하고 Local Setup 방식으로 프로젝트를 만들어 보겠습니다.

NodeJS를 설치하고 아래 두 방식 중 하나를 선택해 React App을 만듭니다.

<br>

**Create-React-App 방식**

``` bash
# 프로젝트 생성
npm i -g typescript
npx create-react-app [프로젝트 이름]

# 프로젝트 실행
npm start
```

<br>

**Vite 방식**

```bash
# 프로젝트 생성
npm i -g vite typescript
npm create vite@latest

# 프로젝트 실행
npm run dev
```

<br>

**타입스크립트 컴파일러 실행**

```bash
npx tsc [파일명].ts
```

<br>

**vite 기반 리액트 포트 변경**

- `package.json` 파일에서 `"dev": "vite"` 부분을 `"dev": "vite --port [원하는 포트]"` 로 변경

<br>

**tsconfig.json**

Linting 부분에`"allowSyntheticDefaultImports": true`를  사용하면, 기본적으로 default export가 없는 모듈에서도 default import를 허용합니다.

<br>

**추가된 패키지 (필요한 패키지가 생길 때마다 추가 중)**

- axios
- @types/node @types/react @types/react-dom @types/jest
- eslint
- chart.js & react-chartjs-2
- lodash @types/lodash
- chartjs-adapter-moment
- @reduxjs/toolkit react-redux @types/react-redux

---

## Functional Component & Props

**함수형 컴포넌트 작성**

- `const Item` : Item이라는 새로운 상수를 선언합니다. 이 상수는 React의 함수형 컴포넌트를 나타냅니다.
- `React.FC<{items: string[]}>` : 여기서 `React.FC`는 "React Function Component"의 약어로, 이 컴포넌트가 함수형 컴포넌트임을 나타냅니다. `<{items: string[]}>` 부분은 제네릭을 사용하여 해당 컴포넌트의 `props`의 타입을 정의합니다. 즉, 이 컴포넌트는 `items`라는 이름의 prop을 받으며, 그 타입은 문자열의 배열(`string[]`)입니다.
- `(props) => { ... }` : 이것은 화살표 함수(arrow function)입니다. `props`는 이 컴포넌트에 전달된 속성들을 포함하는 객체입니다. 이 경우, `items`라는 속성만을 기대합니다.
- `return (...)` : 함수형 컴포넌트는 JSX를 반환합니다. 여기서는 `<ul>` 태그(리스트)를 반환합니다.
- `{props.items}` : 이 부분은 JSX 내에서 JavaScript 표현식을 삽입하기 위한 문법입니다. 여기서 `props.items`는 문자열의 배열(`string[]`)로 예상됩니다. 그러나 이렇게 배열을 직접 렌더링하면 React는 경고를 발생시킵니다. 이 부분은 아마도 원하는 동작을 수행하지 않을 것입니다. 각 항목을 `<li>` 태그로 감싸주려면 다음과 같이 변경해야 합니다:

```tsx
const Item: React.FC<{ items: string[] }> = (props) => {  
    return (  
        <ul>  
            {props.items.map((item, index) =>  
                <li key={index}>{item}</li>,  
            )}  
        </ul>  
    )  
}  
  
export default Item;
```

<br>

**App.tsx**

함수형 컴포넌트의 타입으로 `string[]`으로 정했으니 해당 컴포넌트에는 무조건 값이 들어가야 합니다.

```tsx
import './App.css'  
import ReactiveVar from "./components/ReactiveVar";  
  
function App() {  
    return (  
        <div>  
            <ReactiveVar items={['A', 'B']} />  
        </div>  
    );  
}  
  
export default App
```

---

## useRef & useState & Event

> **useRef**()

- 함수형 컴포넌트에서 이를 설정 할 때 `useRef` 를 사용하여 설정하는 기능,
- 특정 DOM을 선택할때에도 `useRef`를 사용할 수 있습니다. (ex: 입력칸을 제출하고 화면 포커싱을 다시 입력칸으로 이동시키기)
- TypeScript에서는 `Ref`를 생성하고 `제네릭에 타입을 꼭 명시해야 합니다.`

<br>

`useRef` Hook 은 DOM 을 선택하는 용도 외에도, 다른 용도가 한가지 더 있는데요, 바로, 컴포넌트 안에서 조회 및 수정 할 수 있는 변수를 관리하는 것 입니다.

**`useRef` 로 관리하는 변수는 값이 바뀐다고 해서 컴포넌트가 리렌더링되지 않습니다.**

리액트 컴포넌트에서의 상태는 상태를 바꾸는 함수를 호출하고 나서 그 다음 렌더링 이후로 업데이트 된 상태를 조회 할 수 있는 반면, `useRef` 로 관리하고 있는 변수는 설정 후 바로 조회 할 수 있습니다.

이 변수를 사용하여 다음과 같은 값을 관리 할 수 있습니다.

- `setTimeout`, `setInterval` 을 통해서 만들어진 `id`
- 외부 라이브러리를 사용하여 생성된 인스턴스
- scroll 위치

<br>

> **useState**() - `useState`는 컴포넌트의 상태를 관리하는 Hook입니다.

```tsx
const [num, setNum] = useState(0);
```

`useState`를 사용하는 방법은, 상태의 기본값을 넣어서 호출해주고 배열을 반환합니다..

첫번째 배열의 원소는 **현재 상태**, 두번째 원소는 **Setter 함수**입니다.

<br>

**RefInput.tsx**

- 사용자의 입력값이 Ref에 저장됩니다
- 폼이 제출되면 사용자 입력값을 enteredText로 변수에 담습니다.
- if문으로 검증
- 검증이 통과되면 함수형 컴포넌트의 파라미터인 props의 핸들러를 호출해서 입력값을 넘깁니다.

```tsx
import React, {useRef} from "react";  
  
const Input: React.FC<{onAddItem: (enteredText: string) => void}> = (props) => {  
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
  
        props.onAddItem(enteredText);  
    };  
  
    return <form onSubmit={submitHandler}>  
        <label htmlFor="text">Text Here</label>  
        <input type="text" id="text" ref={inputRef} />  
        <button>Add Item</button>  
    </form>  
}  
  
export default Input;
```

<br>

**App.tsx**

`useState`를 생성할때도 타입스크립트에선 제네릭에 상태의 타입을 명시해야 합니다.

<br>

- `useState`를 이용해 빈 상태 배열 생성
- RefInput에서 Props으로 넘긴 사용자의 입력값을 addItemHandler로 넘김
- addItemHandler에 사용자의 입력값이 들어오면 새 입력값 객체를 생성하고, setItem으로 상태 배열에 추가합니다.
- 이 때 배열에 바로 추가하는게 아닌 concat 등을 이용해 **새 배열을 반환해야 합니다. (중요)**

```tsx
import './App.css'  
import { useState } from 'react';  
  
import ReactiveFC from "./components/ReactiveFC";  
import Reactive from "./models/data";  
import RefInput from "./components/RefInput";  
  
function App() {  
    // State, RefInput으로 폼 제출하면 여기에 추가 돠어야함  
    const [item, setItem] = useState<Reactive[]>([]);  
  
    const addItemHandler = (text: string) => {  
        const newItem = new Reactive(text);  
  
        // 이전 상태를 기반으로 상태를 업데이터 하려면 함수 형식을 사용해야 함  
        // concat으로 새로운 Item을 추가한 새 배열 반환  
        setItem((pre) => {  
            return pre.concat(newItem);  
        });  
    };  
  
    return (  
        <div>  
            <RefInput onAddItem={addItemHandler} />  
            <ReactiveFC items={item} />  
        </div>  
    );  
}  
  
export default App
```

---

## 항목 삭제

**ReactiveFCItem.tsx**

- `<li>` 태그에 onClick 이벤트를 걸고 props.임의의 이름(함수)를 넣어줍니다.
- 컴포넌트의 FC 파라미터에 `onRemoveItem` 함수를 파라미터로 넣어줍니다.
- 클릭 이벤트니까 `onRemoveItem` 함수의 파라미터로 `event: React.MouseEvent`를 받아도 되지만 Optional이기 때문에 파라미터는 비워도 됩니다.

```tsx
const Item: React.FC<{text: string; onRemoveItem: () => void}> = (props) => {  
  
    return <li onClick={props.onRemoveItem}>{props.text}</li>  
}  
  
export default Item;
```

<br>

**ReactiveFC.tsx**

위의 아이템을 실제로 사용하는 곳에서는 단순히 App.tsx로 동일하게 props를 넘깁니다.

- 단 실제 사용하는 App.tsx에서 itemId를 이용해 단순히 ID를 삭제할거기 때문에 파라미터로 itemId를 넣어줍니다.
- `onRemoveItem={() => props.onRemoveItem(item.id)} ` 이부분에서 `bind(null, item.id)` 방식으로 함수 바인딩을 사용해도 되지만 단순한 화살표 함수를 이용해도 됩니다.

```tsx
import React from "react";  
import Reactive from "../models/data";  
import ReactiveFCItem from "./ReactiveFCItem";  
  
const Item: React.FC<{ items: Reactive[]; onRemoveItem: (itemId: string) => void }> = (props) => {  
    return (  
        <ul>  
            {props.items.map((item) =>  
                <ReactiveFCItem  
                    key={item.id}  
                    text={item.text}  
                    onRemoveItem={() => props.onRemoveItem(item.id)}  
                />  
            )}  
        </ul>  
    )  
}  
  
export default Item;
```

<br>

**App.tsx**

- 하위 컴포넌트가 받을 props인 `onRemoveItemHandler`를 작성합니다.
- 상태는 이전 상태를 기준으로 업데이트 해야 하기 떄문에 파라미터로 pre를 받아줍니다.
- filter를 이용하여 Item의 id 값이 같은것만 삭제합니다.
- `<ReactiveFC items={item} onRemoveItem={removeItemHandler} />` 컴포넌트에 핸들러를 연결해줍니다.

```tsx
import './App.css'  
import { useState } from 'react';  
  
import ReactiveFC from "./components/ReactiveFC";  
import Reactive from "./models/data";  
import RefInput from "./components/RefInput";  
  
function App() {  
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
  
    return (  
        <div>  
            <RefInput onAddItem={addItemHandler} />  
            <ReactiveFC items={item} onRemoveItem={removeItemHandler} />  
        </div>  
    );  
}  
  
export default App
```

---

## Context API

React에서 Context API란 상위 컴포넌트에서 하위 컴포넌트로 데이터를 전달하기 위한 메커니즘을 제공합니다.

<br>

**App.tsx**

위 내용에선 App.tsx에 많은 이벤트 핸들러와 변수, 함수 등이 있었지만 전부 MainContext.tsx로 옮깁니다.

그리고 MainContext에 정의된 ContextProvider를 통해 메인 앱에 출력합니다.

ContextProvider 컴포넌트는 바로 아래에서 설명하겠습니다.

```tsx
import './App.css'  
  
import ReactiveFC from "./components/item/ReactiveFC";  
import RefInput from "./components/item/RefInput";  
import ContextProvider from "./components/context/MainContext";  
  
function App() {  
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

---

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
## Redux

리액트에서 리덕스를 사용하기 위해서 필요한 패키지를 먼저 설치합니다. 

```
npm i @reduxjs/toolkit react-redux
```

<br>

타입스크립트를 위해서 해당 패키지가 필요하지 않은가 생각할 수 있지만 

react-redux 7.2.3버전부터 해당 패키지를 포함하고 있기 때문에 이후 버전은 설치할 필요가 없습니다.

```
npm i @types/react-redux
```

<br>

**Store** 

- 전체 저장소( 상태 ) 가 들어가 있습니다.
- 하나의 프로젝트는 하나의 스토어를 권장하지만,
- 필요하다면 여러개도 가능은 합니다. 

**Reducer** 

- 변화를 일으키는,  그리고 초기값을 지정하는 공간입니다.
- 필요에 따라 여라가지 Reducer가 들어갑니다. 

**Hooks** 

- Dispatch나 Selecter를 쉽게 사용하기 위한 커스텀 훅을 작성할 것입니다. 
- 이쪽은 Redux관련 Hook만 들어가는 것이 아닌 필요에 따라 다양하게 활용합니다.

<br>

>**Redux 사용해보기**

**store/index.ts**

아직 Reducer를 만들지 않았기 때문에 들어가는 것은 없고, 

RootState와 AppDispatch는 Hooks에서 사용될 Dispatch와 Selector에 사용할 타입입니다.

```tsx
import { configureStore } from "@reduxjs/toolkit";  
  
export const store = configureStore({  
    reducer: {},  
});  
  
export type RootState = ReturnType<typeof store.getState>;  
export type AppDispatch = typeof store.dispatch;
```

<br>

**hooks/index.ts**

```tsx

```