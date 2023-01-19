import { useState } from "react";
import { useContentDispatch } from "../editor/contentContext";
import { useThemeState } from "../editor/themeContext";
import {
  ContentContainer,
  ContentImgContainer,
  ContentCover,
  ContentEditMode,
  ContentImgEditMode,
  ContentEditModeButton,
  ContentEditInput,
  ContentEditDoneButton,
  ContentRemoveButton,
  ContentEditCancelButton,
  ImgContentRemoveButton,
} from "../styled-component/contentStyle";

export default function Content({ data }) {
  const contentInfo = data.info;
  const contentId = data.id;
  const isImage = data.isImage;

  const theme = useThemeState();
  const contentDispatch = useContentDispatch();

  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState(false);

  const [contentInfoText, setContentInfoText] = useState(contentInfo);
  const [editedContentInfo, setEditedContentInfo] = useState(contentInfo);

  const handleChange = (event) => {
    setEditedContentInfo(event.target.value);
  };

  const openContent = () => {
    if (!open) {
      return setOpen(true);
    } else if (open) {
      return setOpen(false);
    }
  };

  const onEditing = () => {
    setEditing(true);
  };

  const offEditing = () => {
    setEditing(false);
  };

  const editContent = () => {
    setContentInfoText(editedContentInfo);
    if (editedContentInfo.length > 0) {
      contentDispatch({
        type: "EDIT_CONTENT",
        contentId: contentId,
        contentInfo: editedContentInfo,
      });
      setEditing(false);
    }
  };

  const removeContent = () => {
    contentDispatch({ type: "REMOVE_CONTENT", contentId: contentId });
  };

  return (
    <ContentContainer theme={theme} open={open}>
      {isImage ? (
        <>
          <ContentImgEditMode>
            <ContentImgContainer
              onClick={openContent}
              open={open}
              src={contentInfo}
            />
            <ImgContentRemoveButton onClick={removeContent} open={open}>
              remove
            </ImgContentRemoveButton>
          </ContentImgEditMode>
        </>
      ) : (
        <>
          <ContentCover editing={editing}>{contentInfoText}</ContentCover>
          <ContentEditModeButton editing={editing} onClick={onEditing}>
            content edit
          </ContentEditModeButton>
          <ContentEditMode editing={editing}>
            <ContentEditInput
              type="text"
              value={editedContentInfo}
              onChange={handleChange}
            />
            <ContentEditDoneButton onClick={editContent}>
              edit done
            </ContentEditDoneButton>
            <ContentEditCancelButton onClick={offEditing}>
              edit cancel
            </ContentEditCancelButton>
            <ContentRemoveButton onClick={removeContent}>
              remove
            </ContentRemoveButton>
          </ContentEditMode>
        </>
      )}
    </ContentContainer>
  );
}
