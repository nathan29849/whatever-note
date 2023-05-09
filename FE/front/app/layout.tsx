import { App } from "../src/App";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <header>{children}</header>
        <main>
          <App />
        </main>
      </body>
    </html>
  );
}
