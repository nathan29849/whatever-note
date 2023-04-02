import styled from "styled-components";

export const NoticeContainer = styled.div`
  background: ${(props) => props.theme.noteColor};
  color: ${(props) => props.theme.fontColor};
  width: 300px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: bold;
  padding: 10px;
  margin-bottom: 10px;
`;
