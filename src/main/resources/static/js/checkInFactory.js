(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }


    factoriesModul.factory('CheckInFactory', ['$http', 'durationFactory', 'userFactory', 'DateHelper', function ($http, durationFactory, userFactory, DateHelper) {
        var factory = {};
        factory.isCheckedIn = function (success) {
            userFactory.getCurrentUser(function (user) {
                $http.get('/checkins/' + user.username).success(function (checkIn) {
                    checkIn.startTime = DateHelper.convertDateArrayToObject(checkIn.startTime);
                    success(true, checkIn);
                }).error(function () {
                    success(false);
                })
            });
        };
        factory.checkIn = function (startTime, success, error) {
            userFactory.getCurrentUser(function (user) {
                $http.post('/checkins', {
                    username: user.username,
                    startTime: startTime
                }).success(success).error(error);
            });
        };
        factory.checkOut = function (endTime, success, error) {
            userFactory.getCurrentUser(function (user) {
                $http.post('/checkouts', {
                    username: user.username,
                    endTime: endTime
                }).success(success).error(error);
            });
        };
        return factory;
    }]);
}());
