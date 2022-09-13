import { useState } from "react";
import Draggable from "./draggable";

export default function Collection() {
    const [newNoteName, setnewNoteName] = useState("");
    const [notes, setNote] = useState([]);

    const handleChange = (event) => {
        setnewNoteName(event.target.value);
        console.log("value is: ", event.target.value);
    };

    const addNote = () => {
        const newId = notes.length;
        const newNote = {
            name: newNoteName,
            id: newId,
        };
        if (!notes) {
            setNote([newNote]);
        } else {
            setNote([...notes, newNote]);
        }
    };

    const consoleNote = () => {
        console.log(newNoteName);
        console.log(notes);
    };

    return (
        <div className="collection stack-large">
            <div className="collection--input-box">
                <input
                    type="text"
                    className="collection--input--naming"
                    name="text"
                    onChange={handleChange}
                    value={newNoteName}
                />
                <button type="submit" className="input-btn" onClick={addNote}>
                    add
                </button>
            </div>
            <ul
                role="list"
                className="note-collection stack-large"
                aria-labelledby="list-heading"
            >
                {notes.map((e) => {
                    return (
                        <li key={e.id} className="note">
                            {e.name}
                            <Draggable>
                                <div className="note--edit-btn">수정</div>
                            </Draggable>
                        </li>
                    );
                })}
            </ul>
        </div>
    );
}
