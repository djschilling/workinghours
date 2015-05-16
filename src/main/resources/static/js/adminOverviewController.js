(function () {
  'use strict';

  var controllersModul;
  try {
    controllersModul = angular.module('dcc.controller');
  } catch (e) {
    controllersModul = angular.module('dcc.controller', []);
  }

  controllersModul.controller('AdminOverviewController', ['$scope', 'durationFactory', 'userFactory', function ($scope, durationFactory, userFactory) {

    userFactory.getAllUser(function (users) {
      $scope.users = users;
      $scope.users.forEach(function (user) {
        durationFactory.getSumForUser(user.username, function (sum) {
          user.sum = sum;
        });
      });
    });
  }]);
}());
