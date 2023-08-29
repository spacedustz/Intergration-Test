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