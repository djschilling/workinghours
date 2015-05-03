(function () {
  'use strict';

  var controllersModul;
  try {
    controllersModul = angular.module('dcc.controller');
  } catch (e) {
    controllersModul = angular.module('dcc.controller', []);
  }

  controllersModul.controller('DurationController', ['$scope', 'durationFactory', function ($scope, durationFactory) {

    $scope.submit = function () {
      var from = durationFactory.convertToDateObject($scope.day, $scope.from);
      var to = durationFactory.convertToDateObject($scope.day, $scope.to);
      durationFactory.save(from, to, function () {
        console.log('Saved duration');
      });
    };

  }]);
}());
