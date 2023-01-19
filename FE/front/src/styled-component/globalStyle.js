import { createGlobalStyle } from "styled-components";

export const GlobalStyle = createGlobalStyle`
  body{
    background: ${(props) => props.theme.bodyColor};
    width:100%;
    height:100%;
  }

  h3{
    font-size:15px;
    font-weight:300;
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }
`;
