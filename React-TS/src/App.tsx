import './App.css'

import ReactiveFC from "./components/item/ReactiveFC";
import RefInput from "./components/item/RefInput";
import ContextProvider from "./components/context/MainContext";
import React from "react";
import CounterForm from "./components/counter/CounterForm";
import Counter from "./components/counter/Counter";
import CounterReducer from "./components/counter/CounterReducer";
import ReducerContext from "./components/context/ReducerContext";
import styled, {css} from "styled-components";
import Button from "./components/style/GlobalStyledBotton";
import RabbitMqWebSocketHandler from "./components/mqtt/RabbitMqWebSocketHandler";
import RedisSocketSubscriber from "./components/mqtt/RedisSocketSubscriber";
import KakaoMap from "./components/kakao/KakaoMap";

interface CircleProps {
    color?: string;
    size?: string;
}

const Circle = styled.div<CircleProps>`
    width: 5rem;
    height: 5rem;
    background: ${props => props.color || 'black'};
    border-radius: 50%;
    ${props => props.size && 
    css`
    width: 10rem; 
    height: 10rem;
    `}
`;

const AppBlock = styled.div`
  width: 512px;
  margin: 0 auto;
  margin-top: 4rem;
  border: 1px solid black;
  padding: 1rem;
`;

const App: React.FC = () => {

    const onSubmit = (form: { name: string; description: string; }) => {
        console.log(form);
    }

    return (
        <ContextProvider>
            {/*/!* Ref & State & Event *!/*/}
            {/*<div>*/}
            {/*    <AppBlock>*/}
            {/*        <RefInput/>*/}
            {/*        <ReactiveFC/>*/}
            {/*    </AppBlock>*/}
            {/*</div>*/}

            {/*/!* Reducer & Input Form *!/*/}
            {/*<div>*/}
            {/*    <AppBlock>*/}
            {/*        <Counter/>*/}
            {/*        <CounterForm onSubmit={onSubmit}/>*/}
            {/*    </AppBlock>*/}
            {/*</div>*/}

            {/*/!* Context *!/*/}
            {/*<div>*/}
            {/*    <ReducerContext>*/}
            {/*        <AppBlock>*/}
            {/*            <CounterReducer/>*/}
            {/*        </AppBlock>*/}
            {/*    </ReducerContext>*/}
            {/*</div>*/}

            {/*/!* Styled Component *!/*/}
            {/*<div>*/}
            {/*    <AppBlock>*/}
            {/*        <Circle color="blue" size/>*/}
            {/*    </AppBlock>*/}
            {/*    <AppBlock>*/}
            {/*        <Button>Button</Button>*/}
            {/*    </AppBlock>*/}
            {/*</div>*/}

            {/* Zustand Item List Statement */}
            {/*<div>*/}
            {/*    <AppBlock>*/}

            {/*    </AppBlock>*/}
            {/*</div>*/}

            <div>
                <AppBlock>
                    {/*<RabbitMqWebSocketHandler />*/}
                    <RedisSocketSubscriber />
                </AppBlock>
                <AppBlock>
                    <KakaoMap />
                </AppBlock>
            </div>
        </ContextProvider>
    );
}

export default App
