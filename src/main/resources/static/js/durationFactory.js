(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }

    factoriesModul.factory('durationFactory', ['$http', 'userFactory', function ($http, userFactory) {
        var factory = {};

        function convertDateArrayToObject(dateArray) {
            if (dateArray) {
                return new CustomDate(new Date(dateArray[0], dateArray[1] - 1, dateArray[2], dateArray[3], dateArray[4], 0, 0));
            }
            return null;

        }

        factory.getSum = function (success) {
            userFactory.getCurrentUser(function (user) {
                $http.get('/durations/sum', {params: {username: user.username}}).success(success);
            });
        };
        factory.getSumForUser = function (username, success) {
            $http.get('/durations/sum', {params: {username: username}}).success(success);
        };

        factory.getSumForUserAndMonth = function (username, month, year, success) {
            $http.get('/durations/sum', {
                params: {
                    username: username,
                    month: month,
                    year: year
                }
            }).success(success);
        };

        factory.create = function (from, to, success) {
            userFactory.getCurrentUser(function (user) {
                $http.post('/durations', {
                    startTime: from,
                    endTime: to,
                    username: user.username
                }, {params: {username: user.username}}).success(success);
            });
        };

        factory.update = function (from, to, id, success) {
            $http.put('/durations/' + id, {startTime: from, endTime: to}).success(success);

        };
        factory.getForCurrentMonth = function (success) {
            userFactory.getCurrentUser(function (user) {
                $http.get('/durations', {params: {username: user.username}}).success(function (durations) {
                    durations.forEach(function (duration) {
                        duration.startTime = convertDateArrayToObject(duration.startTime);
                        duration.endTime = convertDateArrayToObject(duration.endTime);
                    });
                    success(durations);
                });
            });
        };
        factory.getById = function (id, success) {
            userFactory.getCurrentUser(function (user) {
                $http.get('/durations/' + id).success(function (duration) {
                    duration.startTime = convertDateArrayToObject(duration.startTime);
                    duration.endTime = convertDateArrayToObject(duration.endTime);
                    success(duration);
                });
            });
        };
        factory.delete = function (id, success) {
            $http.delete('/durations/' + id).success(success);
        };
        factory.convertToDateObject = function (day, time) {
            var datetime = [];
            datetime.push(parseInt(day.substr(6, 4)));
            datetime.push(parseInt(day.substr(3, 2)));
            datetime.push(parseInt(day.substr(0, 2)));
            datetime.push(parseInt(time.substr(0, 2)));
            datetime.push(parseInt(time.substr(3, 2)));
            return datetime;
        };
        return factory;
    }]);
}());
