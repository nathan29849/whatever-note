import express from "express";
import cors from "cors";

const PORT = 8000;
const app = express();

const corsOptions = {
  origin: "http://localhost:5173",
  optionsSuccessStatus: 200, // some legacy browsers (IE11, various SmartTVs) choke on 204
};

app.use(cors(corsOptions));

app.get("/api/v1", (req, res) => {
  res.json({ hello: "world!!" });
});

app.listen(PORT, () => {
  console.log(`App running in port ${PORT}`);
});
