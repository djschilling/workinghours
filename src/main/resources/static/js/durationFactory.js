(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }

    factoriesModul.factory('durationFactory', ['$http', 'userFactory',function ($http, userFactory) {
        var factory = {};

        factory.getSum = function (success) {
            userFactory.getCurrentUser(function (user) {
                $http.get('/durations/sum', {params: {username: user.username}}).success(success);
            });
        };
        return factory;
    }]);
}());