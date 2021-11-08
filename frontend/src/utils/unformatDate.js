function unformatDate(someDate) {
  const newDate = new Date(someDate).toLocaleDateString();
  return newDate;
}

export default unformatDate;
