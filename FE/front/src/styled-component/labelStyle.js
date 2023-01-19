import styled from "styled-components";

export const LabelListContainer = styled.div`
  display: flex;
  justify-content: flex-start;
  width: 100%;
  margin-top: 10px;
  @media (min-width: 600px) {
    width: 50%;
  }
`;

export const LabelContainer = styled.div`
  background-color: white;
  width: 60px;
  height: 30px;
  margin-left: 2px;
  font-size: 12px;
  pointer: cursor;
  border-radius: 10px 10px 0 0;
  border: 1px solid;
  border-bottom: none;
  border-color: ${(props) => props.theme.labelColor};
  cursor: pointer;
`;
