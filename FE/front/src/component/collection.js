import { useState } from "react";

const DATA = [
    { name: "TOEIC", completed: false, id: "1" },
    { name: "매일매일단어장", completed: false, id: "2" },
    { name: "가끔보는단어장", completed: false, id: "3" },
];

function Note(props) {
    return (
        <li>
            {props.name}
            <input type="checkbox" defaultChecked={props.completed} />
        </li>
    );
}

export default function Collection(props) {
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
            completed: false,
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
                <button onClick={consoleNote}>check</button>
            </div>
            <ul
                role="list"
                className="collection--list stack-large"
                aria-labelledby="list-heading"
            >
                {notes.map((e) => {
                    return (
                        <li key={e.id}>
                            {e.name}
                            <input
                                type="checkbox"
                                defaultChecked={e.completed}
                            />
                        </li>
                    );
                })}
            </ul>
        </div>
    );
}
