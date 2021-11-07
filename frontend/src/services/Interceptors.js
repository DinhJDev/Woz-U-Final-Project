import axios from "axios";

export default async function jwtToken() {
  const user = JSON.parse(localStorage.getItem("user"));
  axios.interceptors.request.use(
    function (config) {
      if (user) {
        config.headers["authorization"] = "Bearer " + user.token;
      }
      return config;
    },
    function (err) {
      return Promise.reject(err);
    }
  );
}
