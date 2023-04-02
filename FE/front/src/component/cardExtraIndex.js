import { useState } from "react";
import { useParams } from "react-router-dom";
import { useCardDispatch } from "../editor/cardContext";
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

export function CardAddIndex() {
  const param = useParams();
  const cardDispatch = useCardDispatch();
  const [newCard, setNewCard] = useState("");
  const [openIndex, setOpenIndex] = useState(false);

  const handleChange = (event) => {
    setNewCard(event.target.value);
  };

  const addCard = () => {
    if (newCard.length > 0) {
      cardDispatch({ type: "ADD_CARD", noteId: param.id, title: newCard });
      setNewCard("");
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
            value={newCard}
          />
          <button onClick={addCard}>add</button>
          <button onClick={closeExtraIndex}>cancel</button>
        </>
      ) : (
        <p onClick={openExtraIndex}>카드 추가하기</p>
      )}
    </div>
  );
}
