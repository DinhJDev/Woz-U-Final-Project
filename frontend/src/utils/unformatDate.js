function unformatDate(someDate) {
  const newDate =
    someDate == null
      ? new Date().toLocaleDateString() + `\t` + new Date().toLocaleTimeString()
      : new Date(someDate).toLocaleDateString() +
        `\t` +
        new Date(someDate).toLocaleTimeString();
  return newDate;
}

export default unformatDate;
