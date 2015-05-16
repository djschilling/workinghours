(function() {
  'use strict';

  var controllersModul;

  try {
    controllersModul = angular.module('dcc.controller');
  } catch (e) {
    controllersModul = angular.module('dcc.controller', []);
  }


  controllersModul.controller('LoginController', ['$http', '$scope', '$rootScope', '$location', 'authenticationFactory',
    function($http, $scope, $rootScope, $location, authenticationFactory) {

    $scope.credentials = {};
    $scope.login = function() {
      authenticationFactory.authenticate($scope.credentials, function() {
        if ($rootScope.authenticated) {
          $location.path("/");
          $scope.error = false;
        } else {
          $scope.error = true;
        }
      });
    };
  }]);
}());
