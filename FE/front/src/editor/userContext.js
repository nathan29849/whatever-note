import { createContext, useReducer, useContext } from "react";
import { app } from "../util/realm";

const UserStateContext = createContext(null);
const UserDispatchContext = createContext(null);

function userReducer(state, action) {
  switch (action.type) {
    case "SET_USER":
      return action.user;
  }
}

export function UserProvider({ children }) {
  const [state, dispatch] = useReducer(userReducer, null);

  return (
    <UserStateContext.Provider value={state}>
      <UserDispatchContext.Provider value={dispatch}>
        {children}
      </UserDispatchContext.Provider>
    </UserStateContext.Provider>
  );
}

export function useUserState() {
  return useContext(UserStateContext);
}

export function useUserDispatch() {
  return useContext(UserDispatchContext);
}
