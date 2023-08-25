import './App.css'
import ReactiveVar from "./components/ReactiveFC";
import Reactive from "./models/data";

function App() {
    const item = [
        new Reactive('A'),
        new Reactive('B')
    ];

    return (
        <div>
            <ReactiveVar items={item} />
        </div>
    );
}

export default App
