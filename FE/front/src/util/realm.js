import * as Realm from "realm-web";

export const app = new Realm.App({ id: process.env.NEXT_PUBLIC_REALM_APP_ID });

export const login = async (email, password) => {
  const credentials = Realm.Credentials.emailPassword(email, password);
  const logged = await app.logIn(credentials);

  return logged;
};
