export default function NoteList() {
  const theme = "black-white--";
  const className = {
    list: theme + "note-list",
  };
  return (
    <ul className={className.list}>
      <li>note1</li>
      <li>note2</li>
    </ul>
  );
}
