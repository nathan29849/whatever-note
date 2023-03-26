type HomeProps = {
  name: string;
};

Home.defaultProps = {
  name: "",
};

function Home({ name }: HomeProps) {
  return <main>Hello,{name}</main>;
}

export default Home;
