import './App.css'
import ReactiveVar from "./components/ReactiveFC";

function App() {
    return (
        <div>
            <ReactiveVar items={['A', 'B']} />
        </div>
    );
}

export default App
