import { useEffect } from "react";
import { app } from "../util/realm";

export default function LoginEmailConfirm({ token, tokenId }) {
  useEffect(() => {
    const emailconfirmmessage = async () => {
      await app.emailPasswordAuth.confirmUser({ token, tokenId });
    };

    emailconfirmmessage();
  }, [token, tokenId]);

  return <div>가입 확인되었습니당</div>;
}
