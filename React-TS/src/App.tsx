import './App.css'

import ReactiveFC from "./components/item/ReactiveFC";
import RefInput from "./components/item/RefInput";
import ContextProvider from "./components/context/MainContext";
import React from "react";
import CounterForm from "./components/counter/CounterForm";
import Counter from "./components/counter/Counter";
import CounterReducer from "./components/counter/CounterReducer";
import ReducerContext from "./components/counter/ReducerContext";

const App: React.FC = () => {

    const onSubmit = (form: { name: string; description: string; }) => {
        console.log(form);
    }

    return (
        <ContextProvider>
            <div>
                <RefInput/>
                <ReactiveFC/>
            </div>

            <div>
                <Counter/>
                <CounterForm onSubmit={onSubmit}/>
            </div>

            <div>
                <ReducerContext>
                    <CounterReducer/>
                </ReducerContext>
            </div>
        </ContextProvider>
    );
}

export default App
