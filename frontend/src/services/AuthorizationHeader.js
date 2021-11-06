export default function AuthorizationHeader() {
  const user = JSON.parse(localStorage.getItem("user"));

  if (user && user.token) {
    return { Authorization: "Bearer" + user.token };
  } else {
    return {};
  }
}
