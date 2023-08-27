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
