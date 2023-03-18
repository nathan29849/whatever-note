import { useCallback, useEffect, useMemo, useState, useRef } from "react";

export default function useDrag(STARTPOSITION) {
  const [drag, setDrag] = useState({
    isDragging: false,
    origin: STARTPOSITION,
    translation: STARTPOSITION,
  });
  const dragRef = useRef(null);

  // const handleMouseDown = useCallback(({ clientX, clientY }) => {
  //   setDrag((drag) => ({
  //     ...drag,
  //     isDragging: true,
  //   }));
  // }, []);

  const handleMouseUp = () => {
    setDrag((drag) => ({
      ...drag,
      isDragging: false,
    }));
  };

  const handleMouseMove = ({ clientX, clientY }) => {
    const translation = {
      x: clientX - drag.origin.x,
      y: clientY - drag.origin.y,
    };

    setDrag((drag) => ({
      ...drag,
      translation,
    }));
    console.log(drag.origin.x, drag.origin.y);
    console.log("client", clientX, clientY);
  };

  const styles = useMemo(
    () => ({
      background: "red",
      cursor: "pointer",
      transform: `translate(${drag.translation.x}px, ${drag.translation.y}px)`,
      transition: drag.isDragging ? "none" : "transform 500ms",
      // zIndex: drag.isDragging ? 5 : 3,
      position: "absolute",
      border: "1px solid red",
      borderRadius: "15px",
      // rotate: "-5deg",
      padding: "5px",
      // fontSize: "10px",
      cursor: "pointer",
    }),
    [drag.isDragging, drag.translation]
  );

  const moveSticker = (e) => {
    const dragref = e.target.getBoundingClientRect();
    setDrag((drag) => ({
      ...drag,
      isDragging: true,
      origin: { x: dragref.x, y: dragref.y },
    }));
    console.log(drag.origin);
  };

  // useEffect(() => {
  //   const dragref = dragRef.current.getBoundingClientRect();
  //   setDrag((drag) => ({
  //     ...drag,
  //     origin: { x: dragref.x, y: dragref.y },
  //   }));
  //   console.log(dragref.x);
  // }, []);

  useEffect(() => {
    if (drag.isDragging) {
      window.addEventListener("mousemove", handleMouseMove);
      window.addEventListener("mouseup", handleMouseUp);
    }

    return () => {
      window.removeEventListener("mousemove", handleMouseMove);
      window.removeEventListener("mouseup", handleMouseUp);
    };
  }, [drag.isDragging, handleMouseMove]);

  return {
    ref: dragRef,
    onMouseDown: (e) => moveSticker(e),
    onMouseUp: (e) => handleMouseUp(e),
    style: styles,
  };
}
