(function () {
  'use strict';

  var controllersModul;
  try {
    controllersModul = angular.module('dcc.controller');
  } catch (e) {
    controllersModul = angular.module('dcc.controller', []);
  }

  controllersModul.controller('DurationController', ['$scope', '$routeParams', function ($scope, $routeParams) {
    $scope.foo = 'bar';
  }]);
}());
