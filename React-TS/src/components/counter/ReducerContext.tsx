import React, {createContext, Dispatch, useContext, useReducer} from "react";

// 필요한 타입 선언
type Color = 'red' | 'orange' | 'yellow';

// State를 위한 타입
type State = {
    count: number;
    text: string;
    color: Color;
    isGood: boolean;
};

// Action을 위한 타입
type Action =
    | { type: 'SET_COUNT'; count: number }
    | { type: 'SET_TEXT'; text: string }
    | { type: 'SET_COLOR'; color: Color }
    | { type: 'TOGGLE_GOOD' };

// 디스패치를 위한 타입, (Dispatch 를 리액트에서 불러올 수 있음), 액션들의 타입을 Dispatch 의 Generics로 설정
type SampleDispatch = Dispatch<Action>;

// Context 생성
const StateContext = createContext<State | null>(null);
const DispatchContext = createContext<SampleDispatch | null>(null);

// Reducer 함수
function reducer(state: State, action: Action): State {
    switch (action.type) {
        case "SET_COUNT": return { ...state, count: action.count };
        case "SET_TEXT": return { ...state, text: action.text };
        case "SET_COLOR": return { ...state, color: action.color };
        case "TOGGLE_GOOD": return { ...state, isGood: !state.isGood };
        default: throw new Error('Unhandled Action');
    }
}

export default function ReducerContext({children}: {children: React.ReactNode}) {

    // 초기 State 값
    const initState: State = {
        count: 0,
        text: 'hello',
        color: 'red',
        isGood: true
    };

    // Use Reducer 1. Reducer함수, 초기 상태값
    const [state, dispatch] = useReducer(reducer, initState);

    return (
        <StateContext.Provider value={state}>
            <DispatchContext.Provider value={dispatch}>
                {children}
            </DispatchContext.Provider>
        </StateContext.Provider>
    );
}

// State와 Dispatch를 쉽게 사용하기 위한 Custom Hook
export function useCounterState() {
    const state = useContext(StateContext);
    if (!state) throw new Error('Cannot find Provider');
    return state;
}

export function useCounterDispatch() {
    const dispatch = useContext(DispatchContext);
    if (!dispatch) throw new Error('Cannot find Provider');
    return dispatch;
}