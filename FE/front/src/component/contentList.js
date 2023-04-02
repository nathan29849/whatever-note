import { useEffect, useState } from "react";
import { getContentFromNathan } from "../editor/api";
import { useContentDispatch, useContentState } from "../editor/contentContext";
import Content from "./content";
import { ContentListContainer } from "../styled-component/contentStyle";
import { ContentAddIndex } from "./contentExtraIndex";

export default function ContentsList(props) {
  const noteId = props.noteId;
  const cardId = props.cardId;
  const contentState = useContentState();
  const contentDispatch = useContentDispatch();

  useEffect(() => {
    getContentFromNathan(contentDispatch, noteId, cardId);
  }, []);

  return (
    <>
      <ContentAddIndex />
      <ContentListContainer>
        {contentState.hasOwnProperty("content") &&
        contentState.content.length > 0 ? (
          <>
            {contentState.content.map((item, index) => (
              <Content key={index} data={item} />
            ))}
          </>
        ) : (
          <div>no content</div>
        )}
      </ContentListContainer>
    </>
  );
}
