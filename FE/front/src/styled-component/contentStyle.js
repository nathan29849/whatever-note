import styled from "styled-components";

export const ContentListContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column-reverse;
  align-items: center;
`;

export const ContentContainer = styled.div`
  background: ${(props) => props.theme.noteColor};
  width: 100%;
  height: ${(props) => (props.open ? "100%" : "60px")};
  min-height: 60px;
  @media (min-width: 600px) {
    width: 90%;
  }
  margin: 0 0 15px 0;
  border-radius: 20px;
  overflow: hidden;
  cursor: pointer;
  display: flex;
`;

export const ContentImgContainer = styled.img`
  position: relative;
  background: transparent;
  width: 100%;
  border-radius: 20px;
  z-index: 0;
`;

export const ContentImgEditMode = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  z-index: 0;
`;

export const ContentEditMode = styled.div`
  display: ${(props) => (props.editing ? "flex" : "none")};
  background: ${(props) => props.theme.noteColor};
  width: 100%;
  height: inherit;
  justify-content: flex-end;
  align-items: center;
`;

export const ContentCover = styled.h3`
  display: ${(props) => (props.editing ? "none" : "block")};
  width: 100%;
  height: 100%;
  text-align: center;
`;

export const ContentEditModeButton = styled.button`
  display: ${(props) => (props.editing ? "none" : "block")};
  width: 30%;
  background: #ebebeb;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const ContentEditInput = styled.input`
  background: transparent;
  border: none;
  height: inherit;
`;

export const ContentEditDoneButton = styled.button`
  width: 20%;
  background: #ffffff;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const ContentEditCancelButton = styled.button`
  position: relative;

  width: 20%;
  background: #efefef;
  border: none;
  height: inherit;
  cursor: pointer;
  z-index: 1;
`;

export const ContentRemoveButton = styled.button`
  display: ${(props) => (props.open ? "none" : "block")};
  width: 20%;
  background: #ebebeb;
  border: none;
  height: inherit;
  cursor: pointer;
`;

export const ImgContentRemoveButton = styled.button`
  position: absolute;
  display: ${(props) => (props.open ? "none" : "block")};
  width: 20%;
  background: #ebebeb;
  border: none;
  height: inherit;
  cursor: pointer;
`;
