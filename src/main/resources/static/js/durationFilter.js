(function () {
    'use strict';

    var filterModule;
    try {
        filterModule = angular.module('dcc.filter');
    } catch (e) {
        filterModule = angular.module('dcc.filter', []);
    }

    filterModule.filter('duration', function () {
        return function (minutes) {
            var hours = Math.floor(minutes / 60);
            var remainingMinutes = minutes % 60;
            var output;
            if (hours > 0) {
                output = hours + ' Stunden, ' + remainingMinutes + ' Minuten';
            } else {
                output = remainingMinutes + ' Minuten';
            }
            return output;
        };
    });
}());
