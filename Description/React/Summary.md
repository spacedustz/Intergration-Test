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

## Ref & State

Ref는 기본적으로 제네릭 타입이며 모든 종류의 데이터와 HTML 객체를 사용할 수 있습니다.

TypeScript에서는 `Ref`를 생성하고 `제네릭에 타입을 꼭 명시해야 합니다.`

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
