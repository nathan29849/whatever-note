import styled from "styled-components";

export const CardAndContentContainer = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const CardListConainer = styled.div`
  width: 100%;
  display: flex;
  align-item: center;
  flex-direction: column-reverse;
`;

export const CardContainer = styled.div`
  background: ${(props) => props.theme.noteColor};
  display: flex;
  align-items: center;
  justify-content: center;
  color: ${(props) => props.theme.fontColor};
  width: 100%;
  @media (min-width: 600px) {
    width: 90%;
  }
  height: 60px;
  border-radius: 20px;
  margin-bottom: 10px;
  overflow: hidden;
  cursor: pointer;
`;

export const CardEditMode = styled.div`
  display: ${(props) => (props.editing ? "flex" : "none")};
  background: ${(props) => props.theme.noteEditColor};
  width: 100%;
  height: inherit;
  justify-content: flex-end;
  align-items: center;
`;

export const CardCover = styled.div`
  display: ${(props) => (props.editing ? "none" : "block")};
  width: 100%;
  text-align: center;
`;

export const CardEditModeButton = styled.button`
  display: ${(props) => (props.editing ? "none" : "block")};
  width: 30%;
  background: #ebebeb;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const CardEditInput = styled.input`
  background: transparent;
  border: none;
  height: inherit;
`;

export const CardEditDoneButton = styled.button`
  width: 20%;
  background: #ffffff;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const CardEditCancelButton = styled.button`
  width: 20%;
  background: #efefef;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const CardRemoveButton = styled.button`
  width: 20%;
  background: #ebebeb;
  border: none;
  height: inherit;
  cursor: pointer;
`;
