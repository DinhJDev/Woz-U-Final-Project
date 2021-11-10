function formatDate(someDate) {
  let date = `${someDate}`;
  date = date.split("/").reverse().join("/");
  return date;
}

export default formatDate;
