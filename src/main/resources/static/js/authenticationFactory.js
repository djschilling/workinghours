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
      }).success(function(data) {
        if (data.username) {
          $rootScope.authenticated = true;
					$rootScope.user.username = data.username;
        } else {
          $rootScope.authenticated = false;
        }
        callback && callback();
      }).error(function() {
        $rootScope.authenticated = false;
        callback && callback();
      });
		};

    factory.isAuthenticated = function (callback) {
      $http.get('whoami')
      .success(function(data) {
        if (data.username) {
          $rootScope.authenticated = true;
        } else {
          $rootScope.authenticated = false;
        }
        callback && callback();
      }).error(function() {
        $rootScope.authenticated = false;
        callback && callback();
      });
    };

    factory.logout = function(callback) {
      $http.post('logout', {}).success(function() {
        $rootScope.authenticated = false;
        callback && callback();
      }).error(function(data) {
        $rootScope.authenticated = false;
        callback && callback();
      });
    };

		return factory;
	}]);
}());
