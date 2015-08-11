(function () {
    'use strict';

    var controllersModul;
    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }

    controllersModul.controller('AdminOverviewController', ['$scope', 'adminOverviewFactory', function ($scope, adminOverviewFactory) {

        var monthsToDisplay = 2;
        adminOverviewFactory.getWorkingHoursForLastMonths(monthsToDisplay, function (results) {
            $scope.results = results;
        });

        $scope.loadMore = function () {
            monthsToDisplay++;
            adminOverviewFactory.getWorkingHoursForLastMonths(monthsToDisplay, function (results) {
                $scope.results = results;
            });
        }
    }]);
}());
