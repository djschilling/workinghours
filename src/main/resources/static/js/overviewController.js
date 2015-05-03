(function () {
  'use strict';

  var controllersModul;
  try {
    controllersModul = angular.module('dcc.controller');
  } catch (e) {
    controllersModul = angular.module('dcc.controller', []);
  }

  controllersModul.controller('OverviewController', ['$scope', 'durationFactory', '$rootScope', function ($scope, durationFactory, $rootScope) {
    durationFactory.getSum(function (sum) {
      $scope.sum = sum;
    });
    durationFactory.getForCurrentMonth(function (durations) {
      $scope.durations = durations;
    });
  }]);
}());
