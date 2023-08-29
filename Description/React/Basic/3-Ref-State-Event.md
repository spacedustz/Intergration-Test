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