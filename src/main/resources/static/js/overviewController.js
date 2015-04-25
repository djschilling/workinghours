(function () {
  'use strict';

  var controllersModul;
  try {
    controllersModul = angular.module('dcc.controller');
  } catch (e) {
    controllersModul = angular.module('dcc.controller', []);
  }

  controllersModul.controller('OverviewController', ['$scope', 'durationFactory', function ($scope, durationFactory) {
      durationFactory.getSum(function (sum) {
          $scope.sum = sum;
      });
  }]);
}());
