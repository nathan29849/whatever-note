// import { useState } from "react";

const DATA = [
    { name: "TOEIC", completed: false },
    { name: "매일매일단어장", completed: false },
];

function Note(props) {
    return (
        <>
            <li>{props.name}</li>
            <input type="checkbox" defaultChecked={props.completed} />
        </>
    );
}

export default function Collection() {
    return (
        <div className="collection stack-large">
            <div className="collection--input-box">
                <input
                    type="text"
                    className="collection--input--naming"
                    name="text"
                    aucoComplete="off"
                />
                <button type="submit" className="input-btn">
                    add
                </button>
            </div>
            <ul
                role="list"
                className="collection--list stack-large"
                aria-labelledby="list-heading"
            >
                {DATA.map((e) => (
                    <Note name={e.name} completed={e.completed} />
                ))}
            </ul>
        </div>
    );
}
