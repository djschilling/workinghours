(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }

    factoriesModul.factory('authenticationFactory', ['$http', '$rootScope', function ($http, $rootScope) {
        var factory = {};

        $rootScope.user = {};

        factory.authenticate = function (credentials, callback) {
            var headers = credentials ? {
                authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('whoami', {
                headers: headers
            }).success(function (data) {
                if (data.username) {
                    $rootScope.authenticated = true;
                    $rootScope.user.username = data.username;
                    $rootScope.user.role = data.role;
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });
        };

        factory.isAuthenticated = function (callback) {
            $http.get('whoami')
                .success(function (data) {
                    if (data.username) {
                        $rootScope.authenticated = true;
                        $rootScope.user.username = data.username;
                        $rootScope.user.role = data.role;
                    } else {
                        $rootScope.authenticated = false;
                    }
                    callback && callback();
                }).error(function () {
                    $rootScope.authenticated = false;
                    callback && callback();
                });
        };

        factory.logout = function (callback) {
            $http.post('logout', {}).success(function () {
                $rootScope.authenticated = false;
                $rootScope.user = {};
                callback && callback();
            }).error(function (data) {
                $rootScope.authenticated = false;
                $rootScope.user = {};
                callback && callback();
            });
        };

        $rootScope.isAdmin = function () {
            return $rootScope.authenticated && $rootScope.user.role === 'admin';
        }

        return factory;
    }]);
}());
