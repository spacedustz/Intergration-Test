import React from "react";
import {useCounterDispatch, useCounterState} from "../context/ReducerContext";

export default function CounterReducer() {

    const state = useCounterState();
    const dispatch = useCounterDispatch();

    // DisPatch를 이용한 상태 변경
    const setCount = () => dispatch({ type: 'SET_COUNT', count: 5 }); // count 를 넣지 않으면 에러발생
    const setText = () => dispatch({ type: 'SET_TEXT', text: 'bye' }); // text 를 넣지 않으면 에러 발생
    const setColor = () => dispatch({ type: 'SET_COLOR', color: 'orange' }); // orange 를 넣지 않으면 에러 발생
    const toggleGood = () => dispatch({ type: 'TOGGLE_GOOD' });

    // 출력
    return (
        <div>
            <p><code>count: </code> {state.count}</p>
            <p><code>text: </code> {state.text}</p>
            <p><code>color: </code> {state.color}</p>
            <p><code>isGood: </code> {state.isGood ? 'true' : 'false'}</p>

            <div>
                <button onClick={setCount}>SET_COUNT</button>
                <button onClick={setText}>SET_TEXT</button>
                <button onClick={setColor}>SET_COLOR</button>
                <button onClick={toggleGood}>TOGGLE_GOOD</button>
            </div>
        </div>
    );
}