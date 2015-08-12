(function () {
    'use strict';

    var factoriesModul;
    try {
        factoriesModul = angular.module('dcc.factories');
    } catch (e) {
        factoriesModul = angular.module('dcc.factories', []);
    }

    factoriesModul.factory('durationValidationFactory', [function () {
        var factory = {};

        factory.isValidTime = function (time) {
            var timeRegex = /^\d\d:\d\d$/;
            return !!timeRegex.test(time);
        };

        factory.isValidDay = function (day) {
            var dayRegex = /^\d\d-\d\d-\d\d\d\d$/;
            return !!dayRegex.test(day);
        };

        return factory;
    }]);
}());
