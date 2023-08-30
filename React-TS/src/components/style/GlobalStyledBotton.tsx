import React, { ButtonHTMLAttributes } from 'react';
import styled from 'styled-components';

interface StyledButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    // 추가적인 스타일링이 필요한 경우 해당 props를 정의할 수 있습니다.
}

const StyledButton = styled.button<StyledButtonProps>`
  /* 공통 스타일 */
  display: inline-flex;
  outline: none;
  border: none;
  border-radius: 4px;
  color: white;
  font-weight: bold;
  cursor: pointer;
  padding-left: 1rem;
  padding-right: 1rem;

   /* 크기 */
   height:2.25rem ;
   font-size :1rem ;

   /* 색상 */
    background :#228be6 ;
    &:hover {
        background:#339af0 ;
    }
    &:active {
        background:#1c7ed6 ;
    }

     /* 기타 */
     & + &{
         margin-left :1rem ;
     }
`;

function Button({ children, ...rest }: StyledButtonProps): JSX.Element {
    return <StyledButton {...rest}>{children}</StyledButton>;
}

export default Button;
