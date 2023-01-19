import { useState } from "react";
import { useContentDispatch } from "../editor/contentContext";
const styles = {
  cursor: "pointer",
  //   transform: `translate(${drag.translation.x}px, ${drag.translation.y}px)`,
  //   transition: drag.isDragging ? "none" : "transform 500ms",
  //   zIndex: drag.isDragging ? 2 : 1,
  position: "absolute",
  border: "1px solid red",
  borderRadius: "15px",
  rotate: "-5deg",
  padding: "5px",
  fontSize: "10px",
};

export function ContentAddIndex() {
  const contentDispatch = useContentDispatch();
  const [newContent, setNewContent] = useState("");
  const [openIndex, setOpenIndex] = useState(false);

  const handleChange = (event) => {
    setNewContent(event.target.value);
  };

  const addContent = () => {
    if (newContent.length > 0) {
      contentDispatch({ type: "ADD_CONTENT", newContent: newContent });
      setNewContent("");
    }
    return;
  };

  const openExtraIndex = () => {
    setOpenIndex(true);
  };

  const closeExtraIndex = () => {
    setOpenIndex(false);
  };

  return (
    <div style={styles}>
      {openIndex ? (
        <>
          <input
            type="text"
            name="content"
            onChange={handleChange}
            value={newContent}
          />
          <button onClick={addContent}>add</button>
          <button onClick={closeExtraIndex}>cancel</button>
        </>
      ) : (
        <p onClick={openExtraIndex}>콘텐츠 추가하기</p>
      )}
    </div>
  );
}
