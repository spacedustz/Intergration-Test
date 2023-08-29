import {useReducer} from "react";

type Color = 'red' | 'orange' | 'yellow';

// State 정의
type State = {
    count: number;
    text: string;
    color: Color;
    isGood: boolean;
};

// Action 정의
type Action =
    | { type: 'SET_COUNT'; count: number }
    | { type: 'SET_TEXT'; text: string }
    | { type: 'SET_COLOR'; color: Color }
    | { type: 'TOGGLE_GOOD' };

// Reducer 함수에 Action 정의
function reducer(state: State, action: Action): State {
    switch (action.type) {
        case "SET_COUNT": return { ...state, count: action.count };
        case "SET_TEXT": return { ...state, text: action.text };
        case "SET_COLOR": return { ...state, color: action.color };
        case "TOGGLE_GOOD": return { ...state, isGood: !state.isGood };
        default: throw new Error('Unhandled Action');
    }
}

export default function CounterReducer() {
    // 초기 State 값
    const initState: State = {
        count: 0,
        text: 'hello',
        color: 'red',
        isGood: true
    };

    // Use Reducer 1. Reducer함수, 초기 상태값
    const [state, dispatch] = useReducer(reducer, initState);

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

// type Actions = { type: 'INCREASE' } | { type: 'DECREASE' };
//
// function reducer(state: number, action: Actions): number {
//     switch (action.type) {
//         case 'INCREASE': return state + 1;
//         case 'DECREASE': return state - 1;
//         default: throw new Error('Unhandled Action');
//     }
// }
//
// export default function CounterReducer() {
//     const [count, dispatch] = useReducer(reducer, 0, 0);
//     const onIncrease = () => dispatch({type: 'INCREASE'});
//     const onDecrease = () => dispatch({type: 'DECREASE'});
//
//     return (
//         <div>
//             <h1>{count}</h1>
//
//             <div>
//                 <button onClick={onIncrease}>+1</button>
//                 <button onClick={onDecrease}>-1</button>
//             </div>
//         </div>
//     );
// }