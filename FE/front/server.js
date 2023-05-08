import fs from "fs";
import path from "path";
import express from "express";
import { createServer as createViteServer } from "vite";

async function createServer() {
  const app = express();

  const vite = await createViteServer({
    server: { middlewareMode: true },
    appType: "custom",
  });

  app.use(vite.middlewares);

  app.use("*", async (req, res, next) => {
    try {
      const url = req.originalUrl;
      let template, render;

      template = fs.readFileSync(path.resolve("index.html"), "utf-8");

      template = await vite.transformIndexHtml(url, template);

      render = await vite.ssrLoadModule("/src/entry-server.jsx");

      const appHtml = await render(url);

      const html = template.replace(`<!--ssr-outlet-->`, appHtml);

      res.status(200).set({ "Content-Type": "text/html" }).end(html);
    } catch (e) {
      vite.ssrFixStacktrace(e);
      next(e);
    }
  });

  app.listen(5173);
}

createServer();
