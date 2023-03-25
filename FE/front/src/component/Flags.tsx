type FlagProps = {
  name: string;
};

Flag.defaultProps = {
  name: "",
};

function Flag({ name }: FlagProps) {
  return <li>{name}</li>;
}

export default function Flags() {
  return <ul>flags</ul>;
}
