import { useCallback, useEffect, useMemo, useState } from "react";

const POSITION = { x: 0, y: 0 };

export default function useDrag(STARTPOSITION) {
  const [drag, setDrag] = useState({
    isDragging: false,
    origin: POSITION,
    translation: STARTPOSITION,
  });

  const handleMouseDown = useCallback(({ clientX, clientY }) => {
    setDrag((drag) => ({
      ...drag,
      isDragging: true,
    }));
    console.log("mouse down");
  }, []);

  const handleMouseUp = useCallback(() => {
    console.log(drag);
    setDrag((drag) => ({
      ...drag,
      isDragging: false,
    }));
    console.log(drag);
  }, []);

  const handleMouseMove = useCallback(
    ({ clientX, clientY }) => {
      const translation = {
        x: clientX - drag.origin.x,
        y: clientY - drag.origin.y,
      };

      setDrag((drag) => ({
        ...drag,
        translation,
      }));
      console.log(drag.origin.x, drag.origin.y);
      console.log(clientX, clientY);
    },
    [drag.origin]
  );

  const styles = useMemo(
    () => ({
      cursor: "pointer",
      transform: `translate(${drag.translation.x}px, ${drag.translation.y}px)`,
      transition: drag.isDragging ? "none" : "transform 500ms",
      zIndex: drag.isDragging ? 2 : 1,
      position: "absolute",
      border: "1px solid red",
      borderRadius: "15px",
      rotate: "-5deg",
      padding: "5px",
      fontSize: "10px",
    }),
    [drag.isDragging, drag.translation]
  );
  //
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
    onMouseDown: (e) => handleMouseDown(e),
    onMouseUp: (e) => handleMouseUp(e),
    style: styles,
  };
}
