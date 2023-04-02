import { useCallback, useEffect, useMemo, useState } from "react";
const POSITION = { x: 0, y: 0 };
const STARTPOSITION = { x: 100, y: 100 };

export default function MoveableComponent() {
  const [drag, setDrag] = useState({
    isDragging: false,
    origin: POSITION,
    translation: POSITION,
  });

  const handleMouseDown = useCallback(() => {
    setDrag((drag) => ({
      ...drag,
      isDragging: true,
    }));
    console.log("mouse down");
  }, []);
  //
  const handleMouseUp = useCallback(() => {
    setDrag((drag) => ({
      ...drag,
      isDragging: false,
    }));
    console.log(drag);
  }, []);

  const handleMouseMove = useCallback(
    ({ clientX, clientY }) => {
      console.log(drag);
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
      border: "2px solid red",
      padding: "20px",
    }),
    [drag.isDragging, drag.translation]
  );
  //
  useEffect(() => {
    if (drag.isDragging) {
      window.addEventListener("mousemove", handleMouseMove);
    }

    return () => {
      window.removeEventListener("mousemove", handleMouseMove);
    };
  }, [drag.isDragging, handleMouseMove]);

  return (
    <>
      <div
        style={styles}
        onMouseUp={handleMouseUp}
        onMouseDown={handleMouseDown}
      >
        <div>bye</div>
      </div>
    </>
  );
}
