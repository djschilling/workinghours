(function () {
  'use strict';

  var controllersModul;
  try {
    controllersModul = angular.module('dcc.controller');
  } catch (e) {
    controllersModul = angular.module('dcc.controller', []);
  }

  controllersModul.controller('DurationController', ['$scope', function ($scope) {
    $scope.foo = "bar";
  }]);
}());
