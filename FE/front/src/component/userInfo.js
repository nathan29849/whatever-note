import { useState } from "react";
import { app } from "../util/realm";

export default function UserInfo() {
  const [user, setUser] = useState(app.currentUser);

  return <div>userinfo : user id is.. {user.id}</div>;
}
