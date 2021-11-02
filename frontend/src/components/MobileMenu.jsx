import React from "react";
import wait from "../utils/wait";

function MobileMenu() {
  let clickedClass = "clicked";
  const body = document.body;
  const closeNav = "close-nav";
  const openNav = "open-nav";
  let openState;
  if (openState === closeNav || openState === openNav) {
    body.classList.add(openState);
  } else {
    body.classList.add(closeNav);
  }

  const switchOpenState = (e) => {
    wait(500);
    if (openState === openNav) {
      body.classList.replace(openNav, closeNav);
      e.target.classList.remove(clickedClass);
      openState = closeNav;
    } else {
      body.classList.replace(closeNav, openNav);
      e.target.classList.add(clickedClass);
      openState = openNav;
    }
  };

  const activeNavLink = document.querySelector("a.nav-link.active");
  const navLink = document.querySelector("a.nav-link");
  if (navLink || activeNavLink) {
    body.classList.replace(openNav, closeNav);
    navLink.onClick = function (e) {
      body.classList.replace(openNav, closeNav);
    };
  }

  return (
    <button
      style={{ WebkitUserSelect: "text" }}
      aria-label="menu"
      role="button"
      aria-controls="app-nav-overlay-0"
      aria-haspopup="menu"
      aria-expanded="false"
      className={openState === "open-nav" ? clickedClass : ""}
      id="mobile-nav-button"
      onClick={(e) => switchOpenState(e)}
    ></button>
  );
}

export default MobileMenu;
