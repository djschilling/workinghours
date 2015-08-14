(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }

    factoriesModul.factory('durationFactory', ['$http', 'userFactory', 'DateHelper', function ($http, userFactory, DateHelper) {
        var factory = {};



        factory.getSum = function (year, month, success) {
            userFactory.getCurrentUser(function (user) {
                $http.get('/durations/sum', {params: {username: user.username, year: year, month: month}}).success(success);
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

        factory.create = function (from, to, success, error) {
            userFactory.getCurrentUser(function (user) {
                $http.post('/durations', {
                    startTime: from,
                    endTime: to,
                    username: user.username
                }, {params: {username: user.username}}).success(success).error(error);
            });
        };

        factory.update = function (from, to, id, success, error) {
            $http.put('/durations/' + id, {startTime: from, endTime: to}).success(success).error(error);

        };
        factory.get = function (year, month, success) {
            userFactory.getCurrentUser(function (user) {
                $http.get('/durations', {params: {username: user.username, year: year, month: month}}).success(function (durations) {
                    durations.forEach(function (duration) {
                        duration.startTime = DateHelper.convertDateArrayToObject(duration.startTime);
                        duration.endTime = DateHelper.convertDateArrayToObject(duration.endTime);
                    });
                    success(durations);
                });
            });
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
            userFactory.getCurrentUser(function () {
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
        factory.convertToDateArray = function (date) {
            var datetime = [];
            datetime.push(date.getFullYear());
            datetime.push(date.getMonth() + 1);
            datetime.push(date.getDate());
            datetime.push(date.getHours());
            datetime.push(date.getMinutes());
            return datetime;
        };
        factory.convertToDateObject  = function(day, time) {
            return new Date(parseInt(day.substr(6, 4)),
                            parseInt(day.substr(3, 2)) - 1,
                            parseInt(day.substr(0, 2)),
                parseInt(time.substr(0, 2)),
                parseInt(time.substr(3, 2)));
        };
        return factory;
    }]);
}());
