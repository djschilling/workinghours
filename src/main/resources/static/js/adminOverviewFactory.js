(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }


    factoriesModul.factory('adminOverviewFactory', ['userFactory', 'durationFactory', function (userFactory, durationFactory) {
        var factory = {};

        factory.getWorkingHoursForLastMonths = function (monthsCount, callback) {

            var results = [];
            var resultCounter = 0;
            for (var i = 0; i < monthsCount; i++) {
                (function () {
                    var innercounter = i;
                    var result = {sums: []};
                    var currentDate = new Date();
                    currentDate.setMonth(currentDate.getMonth() - i);
                    result.month = currentDate.getMonth() + 1;
                    result.year = currentDate.getFullYear();
                    userFactory.getAllUser(function (users) {
                        users.forEach(function (user) {
                            durationFactory.getSumForUserAndMonth(user.username, result.month, result.year, function (sum) {
                                result.sums.push({username: user.username, sum: sum});
                                resultCounter++;
                                if (result.sums.length === users.length) {
                                    results[innercounter] = result;
                                }
                                if (resultCounter === monthsCount * users.length) {
                                    callback(results);
                                }
                            });
                        });
                    });
                })();
            }
        };

        return factory;
    }]);
}());
