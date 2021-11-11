const user = JSON.parse(localStorage.getItem("user"));

export default async function jwtToken() {
  if (user && user.token) {
    return {
      Authorization: user.token,
      Accept: "application/json",
      "Content-Type": "application/json",
    };
  } else {
    return {};
  }
}
