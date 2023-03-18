import { useEffect, useRef, useState } from "react";
import { useNoteState, useNoteDispatch } from "./noteContext";

export function useDragAndDrop(ref, dragOverRef) {
  const noteState = useNoteState();
  const noteDispatch = useNoteDispatch();
  const [afterDragAndDrop, setAfterDragAndDrop] = useState([]);

  const startDrag = (e) => {
    const child = e.target;
    const children = e.target.parentNode.childNodes;
    const index = Array.prototype.indexOf.call(children, child);
    console.log(index);
    console.log(e.target.parentNode.childNodes);

    // console.log()
    ref.current = index;
  };

  const enterDrag = (e) => {
    const child = e.target;
    const children = e.target.parentNode.childNodes;
    const index = Array.prototype.indexOf.call(children, child);
    dragOverRef.current = index;
    console.log(dragOverRef.current);
    return;
  };

  const drop = () => {
    const shallowList = [...noteState];
    const dragItemContent = shallowList[ref.current];
    shallowList.splice(ref.current, 1);
    shallowList.splice(dragOverRef.current, 0, dragItemContent);

    console.log(noteState);
    console.log(shallowList);
  };

  const overDrag = (e) => {
    e.preventDefault();
  };

  useEffect(() => {
    if (afterDragAndDrop.length > 1) {
      noteDispatch({
        type: "EDIT_NOTE_POSITION",
        newNoteOrder: afterDragAndDrop,
      });
    }
  }, [afterDragAndDrop]);

  return {
    onDragStart: (e) => startDrag(e),
    onDragEnter: (e) => enterDrag(e),
    onDragEnd: (e) => drop(),
  };
}
