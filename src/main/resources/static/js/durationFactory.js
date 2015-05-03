(function () {
  'use strict';

  var factoriesModul;
  try {
    factoriesModul = angular.module('dcc.factories');
  } catch (e) {
    factoriesModul = angular.module('dcc.factories', []);
  }

  factoriesModul.factory('durationFactory', ['$http', 'userFactory', function ($http, userFactory) {
    var factory = {};

    factory.getSum = function (success) {
      userFactory.getCurrentUser(function (user) {
        $http.get('/durations/sum', {params: {username: user.username}}).success(success);
      });
    };
    factory.save = function (from, to, success) {
      userFactory.getCurrentUser(function (user) {
        $http.post('/durations', {startTime: from, endTime: to, username: user.username}, {params: {username: user.username}}).success(success);
      });
    };
    factory.getForCurrentMonth = function (success) {
      userFactory.getCurrentUser(function (user) {
        $http.get('/durations', {params: {username: user.username}}).success(success);
      });

    };
    factory.convertToDateObject = function (day, time) {
      var datetime = [];
      datetime.push(parseInt(day.substr(0, 4)));
      datetime.push(parseInt(day.substr(5, 2)));
      datetime.push(parseInt(day.substr(8, 2)));
      datetime.push(parseInt(time.substr(0, 2)));
      datetime.push(parseInt(time.substr(3, 2)));
      return datetime;
    };
    return factory;
  }]);
}());
