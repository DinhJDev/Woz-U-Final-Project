const user = JSON.parse(localStorage.getItem("user"));

export default async function jwtToken() {
  if (user && user.token) {
    return {
      Authorization: user.token,
    };
  } else {
    return {};
  }
}
