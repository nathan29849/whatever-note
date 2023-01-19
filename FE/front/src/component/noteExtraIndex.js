import { useState } from "react";
import { useNoteDispatch } from "../editor/noteContext";
import useDrag from "../editor/useDrag";

export function NoteAddIndex() {
  const noteDispatch = useNoteDispatch();
  const [newNoteName, setNewNoteName] = useState("");
  const [openIndex, setOpenIndex] = useState(false);

  const handleChange = (event) => {
    setNewNoteName(event.target.value);
  };

  const addNote = () => {
    if (newNoteName.length > 0) {
      noteDispatch({ type: "ADD_NOTE", seq: 1000, title: newNoteName });
      setNewNoteName("");
    }
    return;
  };

  const openExtraIndex = () => {
    setOpenIndex(true);
  };

  const closeExtraIndex = () => {
    setOpenIndex(false);
  };

  const usedrag = useDrag({ x: "50", y: "50" });

  return (
    <div {...usedrag}>
      {openIndex ? (
        <>
          <input
            type="text"
            name="notecollection"
            onChange={handleChange}
            value={newNoteName}
          />
          <button onClick={addNote}>add</button>
          <button onClick={closeExtraIndex}>cancel</button>
        </>
      ) : (
        <p onClick={openExtraIndex}>노트 추가하기</p>
      )}
    </div>
  );
}
