import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useCardDispatch, useCardState } from "../editor/cardContext";
import { ContentProvider, useContentDispatch } from "../editor/contentContext";

import { useThemeState } from "../editor/themeContext";
import {
  CardContainer,
  CardEditMode,
  CardCover,
  CardEditModeButton,
  CardEditInput,
  CardEditDoneButton,
  CardEditCancelButton,
  CardRemoveButton,
  CardAndContentContainer,
} from "../styled-component/cardStyle";
import { ContentAddIndex } from "./contentExtraIndex";
import ContentsList from "./contentList";

export default function Card({ data }) {
  const theme = useThemeState();
  const param = useParams();
  const noteId = parseInt(param.id);
  const cardData = data;
  const cardTitle = cardData.title;
  const cardId = cardData.id;
  const [editing, setEditing] = useState(false);
  const [contentsPage, setContentsPage] = useState(false);
  const [writtenCardTitle, setWrittenCardTitle] = useState(cardTitle);
  const [editedCardName, setEditedCardName] = useState(writtenCardTitle);

  const cardState = useCardState();
  const cardDispatch = useCardDispatch();
  const contentDispatch = useContentDispatch();

  const handleChange = (event) => {
    setEditedCardName(event.target.value);
  };

  const turnEditorOn = () => {
    setEditing(true);
  };

  const turnEditorOff = () => {
    setEditing(false);
    setEditedCardName(writtenCardTitle);
  };

  const editCard = () => {
    setWrittenCardTitle(editedCardName);
    cardDispatch({
      type: "EDIT_CARD",
      noteId: param.id,
      cardId: cardId,
      title: editedCardName,
    });
    setEditing(false);
  };

  const removeCard = () => {
    cardDispatch({ type: "REMOVE_CARD", noteId: param.id, cardId: cardId });
  };

  const openAndCloseContentsPage = () => {
    if (contentsPage) {
      setContentsPage(false);
    } else if (!contentsPage) {
      setContentsPage(true);
    }
  };

  return (
    <>
      <ContentProvider>
        <CardAndContentContainer>
          <CardContainer theme={theme} editing={editing}>
            <CardCover editing={editing} onClick={openAndCloseContentsPage}>
              {writtenCardTitle}
            </CardCover>
            <CardEditModeButton
              theme={theme}
              editing={editing}
              onClick={turnEditorOn}
            >
              card edit
            </CardEditModeButton>

            <CardEditMode theme={theme} editing={editing}>
              <CardEditInput
                type="text"
                value={editedCardName}
                onChange={handleChange}
                editing={editing}
              />
              <CardEditDoneButton
                theme={theme}
                editing={editing}
                onClick={editCard}
              >
                done
              </CardEditDoneButton>
              <CardEditCancelButton
                theme={theme}
                editing={editing}
                onClick={turnEditorOff}
              >
                cancel
              </CardEditCancelButton>
              <CardRemoveButton
                theme={theme}
                editing={editing}
                onClick={removeCard}
              >
                remove
              </CardRemoveButton>
            </CardEditMode>
          </CardContainer>

          {contentsPage ? (
            <ContentsList noteId={noteId} cardId={cardId} />
          ) : (
            <></>
          )}
        </CardAndContentContainer>
      </ContentProvider>
    </>
  );
}
