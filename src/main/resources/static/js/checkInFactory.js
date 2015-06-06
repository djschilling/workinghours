(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }


    factoriesModul.factory('CheckInFactory', ['$http', 'userFactory', function ($http, userFactory) {
        var factory = {};
        factory.isCheckedIn = function (success) {
            userFactory.getCurrentUser(function (user) {
                $http.get('/durations', {params: {username: user.username}}).success(
                    function(durations){
                        if(durations.length === 0) {
                            success(false);
                        } else {
                            var lastDuration = durations[0];
                            if(lastDuration.endTime === null) {
                                success(true, lastDuration);
                            } else {
                                success(false);
                            }
                        }
                    }
                );
            });
        };
        return factory;
    }]);
}());
