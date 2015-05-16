(function () {
    'use strict';

    var controllersModul;
    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }

    controllersModul.controller('NewUserController', ['$scope', '$rootScope', 'userFactory', '$location', function ($scope, $rootScope, userFactory, $location) {
      $scope.user = {};
      $scope.submit = function () {
        userFactory.createUser($scope.user, function () {
          $rootScope.notifications.push({message: 'Erfolgreich angelegt.', timestamp: Date.now(), status: 'success'});
          $location.path('/');
        });
      }
    }]);
}());
