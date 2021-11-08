export default async function AuthorizationHeader() {
  const user = JSON.parse(localStorage.getItem("user"));

  if (user && user.token) {
    return {
      Authorization: `Bearer ` + user.token,
      Accept: "application/json",
      "Content-Type": "application/json",
    };
  } else {
    return {};
  }
}
