import React from "react";
import wait from "../utils/wait";

const Theme = () => {
  let clickedClass = "clicked";
  const body = document.body;
  const lightTheme = "light-mode";
  const darkTheme = "dark-mode";
  let theme;

  if (localStorage) {
    theme = localStorage.getItem("theme");
  }
  if (theme === lightTheme || theme === darkTheme) {
    body.classList.add(theme);
  } else {
    body.classList.add(lightTheme);
  }

  document.onkeyup = function (e) {
    var e = e || window.event;
    if (e.altKey && e.which === 67) {
      wait(500);
      switchTheme(e);
      return false;
    }
  };

  const switchTheme = (e) => {
    wait(500);
    if (theme === darkTheme) {
      body.classList.replace(darkTheme, lightTheme);
      e.target.classList.remove(clickedClass);
      localStorage.setItem("theme", "light-mode");
      theme = lightTheme;
    } else {
      body.classList.replace(lightTheme, darkTheme);
      e.target.classList.add(clickedClass);
      localStorage.setItem("theme", "dark-mode");
      theme = darkTheme;
    }
  };

  return (
    <button
      className={theme === "dark-mode" ? clickedClass : ""}
      id="darkMode"
      onClick={(e) => switchTheme(e)}
    ></button>
  );
};

export default Theme;
