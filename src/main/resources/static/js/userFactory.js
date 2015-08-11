(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }


    factoriesModul.factory('userFactory', ['$http', function ($http) {
        var factory = {};

        factory.getCurrentUser = function (success) {
            $http.get('/whoami').success(success);
        };
        factory.getAllUser = function (success) {
            $http.get('/users').success(success);
        };
        factory.createUser = function (user, success) {
            $http.post('/users', user).success(success);
        }
        factory.changePassword = function (username, newPassword, success) {
            $http.post('/users/' + username + '/changepassword', newPassword).success(success);
        }
        return factory;
    }]);
}());
