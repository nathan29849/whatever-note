type HomeProps = {
  name: string;
};

function Home({ name }: HomeProps) {
  return <div>Hello, {name}</div>;
}

Home.defaultProps = {
  name: "",
};

export default Home;
