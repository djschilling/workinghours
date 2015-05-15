var CustomDate = function () {
    this.date = new Date();
};

CustomDate.prototype.getDay = function () {
  return ("0" + (this.date.getDate())).slice(-2);
};

CustomDate.prototype.getMonth = function () {
  return ("0" + (this.date.getMonth() + 1)).slice(-2);
};

CustomDate.prototype.getYear = function () {
  return this.date.getFullYear();
};

CustomDate.prototype.getHours = function () {
  return this.date.getHours();
};

CustomDate.prototype.getMinutes = function () {
    return ("0" + (this.date.getMinutes())).slice(-2);
};

