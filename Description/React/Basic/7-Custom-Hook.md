## Custom Hooks

컴포넌트를 만들다보면, 반복되는 로직이 자주 발생합니다. 예를 들어서 input 을 관리하는 코드는 관리 할 때마다 꽤나 비슷한 코드가 반복되죠.

이번에는 그러한 상황에 커스텀 Hooks 를 만들어서 반복되는 로직을 쉽게 재사용하는 방법을 알아보겠습니다.

<br>

프로젝트 src 디렉터리에 `hooks` 디렉터리를 만들고 useInput.ts 파일을 만듭니다.

**Custom Hook을 만들때는 보통 이렇게 `use`라는 키워드로 시작하는 파일을 만들고 그 안에 함수를 작성합니다.**

커스텀 Hooks 를 만드는 방법은 굉장히 간단합니다.

그냥, 그 안에서 `useState`, `useEffect`, `useReducer`, `useCallback` 등 Hooks 를 사용하여 원하는 기능을 구현해주고, 컴포넌트에서 사용하고 싶은 값들을 반환해주면 됩니다.

<br>

**Window.tsx**

예시로 간단한 Custom Hook인 `useWindowWidth`를 작성해보겠습니다. 이 Hook은 현재 창의 너비를 추적하고 반환하는 역할을 합니다.

```tsx
import { useState, useEffect } from 'react';

function useWindowWidth(): number {
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
    };

    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  return windowWidth;
}
```

<br>

**App.tsx**

```tsx
import React from 'react';
import useWindowWidth from './useWindowWidth';

const MyComponent: React.FC = () => {
  const windowWidth = useWindowWidth();

  return <div>Window Width: {windowWidth}px</div>;
}

