import { useState, useMemo, useCallback, useEffect } from "react";

const POSITION = { x: 0, y: 0 };

function Draggable({ children }) {
    const [mouseState, setMouseState] = useState({
        isDragging: false,
        origin: { x: 0, y: 0 },
        translation: { x: 0, y: 0 },
    });

    const handleMouseDown = useCallback(({ clientX, clientY }) => {
        const origin = { x: clientX, y: clientY };

        console.log("start with" + clientX + clientY);
        setMouseState((mouseState) => ({
            ...mouseState,
            origin,
            isDragging: true,
        }));
    }, []);

    const handleMouseMove = useCallback(({ clientX, clientY }) => {
        const translation = {
            x: clientX,
            y: 0,
        };

        console.log(clientX, clientY);
        console.log(mouseState.origin.x, mouseState.origin.y);

        setMouseState((mouseState) => ({
            ...mouseState,
            translation,
        }));

        console.log("why" + mouseState.isDragging);
    }, []);

    const handleMouseUp = useCallback(() => {
        setMouseState((mouseState) => ({
            ...mouseState,
            translation: POSITION,
            isDragging: false,
        }));

        console.log(mouseState.isDragging);
    }, []);

    useEffect(() => {
        if (mouseState.isDragging === true) {
            window.addEventListener("mousemove", handleMouseMove);
            window.addEventListener("mouseup", handleMouseUp);
            console.log("start");
        } else if (mouseState.isDragging === false) {
            window.removeEventListener("mousemove", handleMouseMove);
            window.removeEventListener("mouseup", handleMouseUp);
            console.log("end");
        }
    }, [mouseState.isDragging, handleMouseMove, handleMouseUp]);

    const styles = useMemo(
        () => ({
            cursor: mouseState.isDragging ? "-webkit-grabbing" : "-webkit-",
            transform: `translate(${mouseState.translation.x}px, ${mouseState.translation.y}px)`,
            transition: mouseState.isDragging ? "none" : "transform 50ms",
            zIndex: 1,
            position: "absolute",
        }),
        [mouseState.isDragging, mouseState.translation]
    );

    return (
        <div style={styles} onMouseDown={handleMouseDown}>
            {children}
        </div>
    );
}

export default Draggable;
