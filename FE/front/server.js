import fs from "fs";
import path from "path";
import { fileURLToPath } from "url";
import express from "express";
import { createServer as createViteServer } from "vite";

const __dirname = path.dirname(fileURLToPath(import.meta.url));

async function createServer() {
  const app = express();

  const vite = await createViteServer({
    server: { middlewareMode: true },
    appType: "custom",
  });

  app.use(vite.middlewares);

  //   let template, render;
  //   template = fs.readFileSync(resolve("index.html"), "utf-8");
  //   template = await vite.transformIndexHtml(url, template);
  //   render = (await vite.ssrLoadModule("/src/entry-server.jsx")).render;

  //   const appHTML = render(url, context);

  //   const html = template.replace(`<!--app-html-->`, appHTML);

  //   return { app, vite };

  app.use("*", async (req, res, next) => {
    // serve index.html

    const url = req.originalUrl;

    try {
      // 1. Read index.html
      let template = fs.readFileSync(path.resolve("index.html"), "utf-8");

      template = await vite.transformIndexHtml(url, template);

      const { render } = await vite.ssrLoadModule("/src/entry-server.jsx");

      const appHTML = await render(url);
      const html = template.replace(`<!--app-html-->`, appHTML);

      res.status(200).set({ "Content-Type": "text/html" }).end(html);
    } catch (e) {
      vite.ssrFixStacktrace(e);
      next(e);
    }
  });

  app.listen(5173);
}

createServer();
