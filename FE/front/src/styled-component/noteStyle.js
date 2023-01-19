import styled from "styled-components";

export const NoteCollectionContainer = styled.div`
  background: ${(props) => props.theme.backgroundColor};
  display: flex;
  flex-direction: column-reverse;
  align-items: center;
  width: inherit;
  padding: 20px;
  border-radius: 15px;
  @media (min-width: 600px) {
    width: 50%;
  }
`;

export const NoteContainer = styled.div`
  background: ${(props) => props.theme.noteColor};
  color: ${(props) => props.theme.fontColor};
  width: 100%;
  @media (min-width: 600px) {
    width: 90%;
  }
  height: 60px;
  margin: 0 0 15px 0;
  border-radius: 20px;
  overflow: hidden;
  cursor: pointer;
`;

export const NoteInsideContainer = styled.div`
  background: ${(props) => props.theme.backgroundColor};
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  width: inherit;
  height: auto;
  border-radius: 15px;
  @media (min-width: 600px) {
    width: 50%;
  }
`;

export const NoteTitleContainer = styled.div`
  background: ${(props) => props.theme.noteColor};
  color: ${(props) => props.theme.fontColor};
  width: 100%;
  @media (min-width: 600px) {
    width: 80%;
  }
  height: 40px;
  border-radius: 20px;
  padding: 10px;
  margin-bottom: 10px;
`;

export const NoteEditMode = styled.div`
  display: ${(props) => (props.editing ? "block" : "none")};
  background: transparent;
  height: inherit;
  display: flex;
  justify-content: flex-end;
  align-items: center;
`;

export const NoteCover = styled.h3`
  display: ${(props) => (props.editing ? "none" : "block")};
  width: 80%;
  text-align: center;
`;

export const NoteEditModeButton = styled.button`
  display: ${(props) => (props.editing ? "none" : "block")};
  width: 20%;
  background: #ebebeb;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const NoteEditInput = styled.input`
  display: ${(props) => (props.editing ? "block" : "none")};
  background: transparent;
  border: none;
  height: inherit;
`;

export const NoteEditDoneButton = styled.button`
  display: ${(props) => (props.editing ? "block" : "none")};
  width: 20%;
  background: #ffffff;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const NoteEditCancelButton = styled.button`
  display: ${(props) => (props.editing ? "block" : "none")};
  width: 20%;
  background: #efefef;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const NoteRemoveButton = styled.button`
  display: ${(props) => (props.editing ? "block" : "none")};
  width: 20%;
  background: #ebebeb;
  border: none;
  height: inherit;
  cursor: pointer;
`;
