import './App.css'
import ReactiveFC from "./components/ReactiveFC";
import Reactive from "./models/data";
import RefInput from "./components/RefInput";

function App() {
    // ReactiveFC 컴포넌트를 위한 더미 데이터
    // RefInput으로 폼 제출하면 여기에 추가 돠어야함
    const item = [
        new Reactive('A'),
        new Reactive('B')
    ];

    return (
        <div>
            <RefInput />
            <ReactiveFC items={item} />
        </div>
    );
}

export default App
