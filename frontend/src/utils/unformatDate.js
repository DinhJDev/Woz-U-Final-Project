function unformatDate(someDate) {
  const newDate =
    someDate == null
      ? new Date().toLocaleDateString()
      : new Date(someDate).toLocaleDateString();
  return newDate;
}

export default unformatDate;
