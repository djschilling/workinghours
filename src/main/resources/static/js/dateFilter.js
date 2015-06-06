(function () {
    'use strict';

    var filterModule;
    try {
        filterModule = angular.module('dcc.filter');
    } catch (e) {
        filterModule = angular.module('dcc.filter', []);
    }

    filterModule.filter('time', function() {
        return function(time) {
            if(time === null) {
                return "--:--"
            }
            var hour = time[3];
            var minute = time[4];
            var output = ("0" + hour).slice(-2) + ":" + ("0" + minute).slice(-2);
            return output;
        };
    });

    filterModule.filter('date', function() {
        return function(date) {
            if(date === null) {
                return "--:--:----"
            }
            var day = date[2];
            var month = date[1];
            var year = date[0];
            var output = ("0" + day).slice(-2) + "-" + ("0" + month).slice(-2) + "-" + year;
            return output;
        };
    });

    filterModule.filter('month', function() {
        return function(month) {
            if(month === null) {
                return "NaD"
            }
            var monthOfYear = ["Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "Sebtember",
                "Oktober", "November", "Dezember"];

            var output = monthOfYear[month-1];
            return output;
        };
    });


}());
