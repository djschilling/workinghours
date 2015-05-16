(function () {
    'use strict';

    var controllersModul;

    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }


    controllersModul.controller('NavBarController', ['userFactory', '$scope', 'authenticationFactory', '$rootScope', '$location',
      function (userFactory, $scope, authenticationFactory, $rootScope, $location) {
        var navBar = $('#navbar');
        navBar.find('a').click(function () {
            if (navBar.hasClass('in')) {
                navBar.collapse('toggle');
            }
        });

        authenticationFactory.isAuthenticated(function () {
          if (!$rootScope.authenticated) {
            $location.path("/login");
            $scope.error = true;
          }
        });

        $scope.logout = function () {
          authenticationFactory.logout(function () {
            $location.path("/login");
          });
        };
    }]);
}());
