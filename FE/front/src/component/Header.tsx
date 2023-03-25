import Flags from "./Flags";

type HeaderProps = {
  name: string;
};

Header.defaultProps = {
  name: "",
};

function Header({ name }: HeaderProps) {
  return (
    <header>
      header
      <Flags />
    </header>
  );
}

export default Header;
