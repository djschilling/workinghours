(function () {
    'use strict';

    var filterModule;
    try {
        filterModule = angular.module('dcc.filter');
    } catch (e) {
        filterModule = angular.module('dcc.filter', []);
    }

    filterModule.filter('durationType', function () {
        return function (durationType) {
            var durationMapping = {
                ILL: 'Krank',
                WORK: 'Arbeit',
                HOLIDAY: 'Urlaub'
            };
            return durationMapping[durationType];
        };
    });
}());
