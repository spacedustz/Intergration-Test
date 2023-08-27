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
