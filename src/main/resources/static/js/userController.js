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
                $rootScope.notifications.push({
                    message: 'Erfolgreich angelegt.',
                    timestamp: Date.now(),
                    status: 'success'
                });
                $location.path('/');
            });
        }
    }]);
    controllersModul.controller('ChangePasswordController', ['$scope', '$rootScope', 'userFactory', '$location', function ($scope, $rootScope, userFactory, $location) {
        $scope.changePassword = function () {
            userFactory.changePassword($rootScope.user.username, $scope.password, function () {
                $rootScope.notifications.push({
                    message: 'Passwort Erfolgreich geändert.',
                    timestamp: Date.now(),
                    status: 'success'
                });
                $location.path('/');
            })
        }
    }]);
}());
