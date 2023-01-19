export async function getNoteList(dispatch) {
  await fetch(
    "https://data.mongodb-api.com/app/note-ppfia/endpoint/getnotelist"
  )
    .then((res) => res.text())
    .then((res) => JSON.parse(res))
    // .then((res) => console.log(res))
    .then(
      (res) =>
        dispatch({
          type: "SET_NOTE_LIST",
          data: res.data.notes,
          hasNext: res.data.hasNext,
          pageNumber: res.data.pageNumber,
        })
      // console.log(res)
    )
    // .finally((res) => console.log(res))
    .catch((err) => console.log("err!" + err));
}

export async function getNoteListFromNathan(dispatch) {
  await fetch("https://whatevernote.site/api/note")
    .then((res) => res.text())
    .then((res) => JSON.parse(res))
    // .then((res) => console.log(res))
    .then(
      (res) =>
        dispatch({
          type: "SET_NOTE_LIST",
          data: res.data.notes,
          hasNext: res.data.hasNext,
          pageNumber: res.data.pageNumber,
        })
      // console.log(res)
    )
    // .finally((res) => console.log(res))
    .catch((err) => console.log("err!" + err));
}

export async function createNote(data, dispatch) {
  // return console.log(data);
  await fetch(
    "https://data.mongodb-api.com/app/note-ppfia/endpoint/createnote",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    }
  )
    .then((res) => res.text())
    .then((res) => JSON.parse(res))
    // .then((res) => console.log(res))
    .then((res) => console.log(res))
    .catch((err) => alert("note create error! " + err));
}

export async function editNote(data) {
  // await fetch("https://data.mongodb-api.com/app/note-ppfia/endpoint/editnote", {
  //   method: "POST",
  //   headers: {
  //     "Content-Type": "application/json",
  //   },
  //   body: JSON.stringify(data),
  // })
  // .then((res) => res.text())
  // .then((res) => JSON.parse(res))
  // // .then((res) => console.log(res))
  // .then((res) =>
  //   dispatch({
  //     type: "ADD_NOTE",
  //     id: res.data.id,
  //     seq: res.data.seq,
  //     title: res.data.title,
  //   })
  // )
  // // .finally((res) => console.log(res))
  // .catch((err) => console.log("err!" + err));
}

export async function removeNote(data) {
  await fetch(
    `https://data.mongodb-api.com/app/note-ppfia/endpoint/removenote`,
    {
      method: "POST",
      body: JSON.stringify(data),
    }
  )
    .then((res) => res.json())
    .then((res) => console.log(res))
    .catch((err) => alert("note remove error! " + err));
}

export async function getCards(dispatch, noteId) {
  const cardURL = `https://whatevernote.site/api/note/${noteId}/card`;
  const cardresult = await fetch(cardURL)
    .then((res) => res.text())
    .then((res) => JSON.parse(res))
    // .then((res) => console.log(res))
    .then((res) => dispatch({ type: "SET_CARDS", data: res.data.cards }))
    .catch((err) => console.log("err!" + err));

  return cardresult;
}

export async function getCardsFromNathan(dispatch, noteId) {
  const cardURL = `https://whatevernote.site/api/note/${noteId}/card`;
  const cardresult = await fetch(cardURL)
    .then((res) => res.text())
    .then((res) => JSON.parse(res))
    // .then((res) => console.log(res))
    .then((res) => dispatch({ type: "SET_CARDS", data: res.data.cards }))
    .catch((err) => console.log("err!" + err));

  return cardresult;
}

export async function createCard(data) {
  // const cardresult = await fetch(
  //   "https://data.mongodb-api.com/app/note-ppfia/endpoint/createcard",
  //   {
  //     method: "POST",
  //     headers: {
  //       "Content-Type": "application/json",
  //     },
  //     body: JSON.stringify(data),
  //   }
  // )
  //   .then((res) => res.json())
  //   .then((res) => console.log(res))
  //   .catch((err) => console.log(err));

  // return cardresult;
  return;
}

export async function editCard(data) {
  // const cardresult = await fetch(
  //   "https://data.mongodb-api.com/app/note-ppfia/endpoint/editcard",
  //   {
  //     method: "POST",
  //     headers: {
  //       "Content-Type": "application/json",
  //     },
  //     body: JSON.stringify(data),
  //   }
  // )
  //   .then((res) => res.json())
  //   .then((res) => console.log(res))
  //   .catch((err) => console.log(err));

  // return cardresult;
  return;
}

export async function removeCard(data) {
  // const cardresult = await fetch(
  //   "https://data.mongodb-api.com/app/note-ppfia/endpoint/removecard",
  //   {
  //     method: "POST",
  //     headers: {
  //       "Content-Type": "application/json",
  //     },
  //     body: JSON.stringify(data),
  //   }
  // )
  //   .then((res) => res.json())
  //   .then((res) => console.log(res))
  //   .catch((err) => console.log(err));

  // return cardresult;

  return;
}

export async function testapi() {
  const result = await fetch(
    "https://data.mongodb-api.com/app/note-ppfia/endpoint/getnotelist"
  )
    .then((res) => res.json())
    .then((res) => console.log(res))
    .catch((err) => console.log(err));

  return result;
}

export async function getContentFromNathan(dispatch, noteId, cardId) {
  await fetch(
    `https://whatevernote.site/api/note/${noteId}/card/${cardId}/content`
  )
    .then((res) => res.text())
    .then((res) => JSON.parse(res))
    // .then((res) => console.log(res))
    .then((res) =>
      dispatch({
        type: "SET_CONTENTS",
        data: res.data.contents,
        noteId: noteId,
        cardId: cardId,
      })
    )
    // .finally((res) => console.log(res))
    .catch((err) => console.log("err!" + err));

  return;
}
