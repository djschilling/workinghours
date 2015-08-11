(function () {
    'use strict';

    var filterModule;
    try {
        filterModule = angular.module('dcc.filter');
    } catch (e) {
        filterModule = angular.module('dcc.filter', []);
    }

    filterModule.filter('time', function () {
        return function (customDate) {
            if (customDate === null) {
                return "--:--"
            }
            var hour = customDate.getHours();
            var minute = customDate.getMinutes();
            var output = hour + ":" + minute;
            return output;
        };
    });

    filterModule.filter('date', function () {
        return function (date) {
            if (date === null) {
                return "--:--:----"
            }
            var day = date.getDay();
            var month = date.getMonth();
            var year = date.getYear();
            return day + "-" + month + "-" + year;
        };
    });

    filterModule.filter('month', function () {
        return function (month) {
            if (month === null) {
                return "NaD"
            }
            var monthOfYear = ["Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "Sebtember",
                "Oktober", "November", "Dezember"];

            return monthOfYear[month - 1];
        };
    });
}());
