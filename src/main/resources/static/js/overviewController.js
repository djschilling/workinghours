(function () {
    'use strict';

    var controllersModul;
    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }

    controllersModul.controller('OverviewController', ['$scope', 'durationFactory', '$rootScope', 'CheckInFactory', 'DateHelper',
        function ($scope, durationFactory, $rootScope, CheckInFactory, DateHelper) {

            var monthShift = 0;

            function init() {
                $scope.year = DateHelper.getMonthShiftedYear(monthShift);
                $scope.month = DateHelper.getMonthShiftedMonth(monthShift);
                CheckInFactory.isCheckedIn(function (isCheckedIn) {
                    $scope.isCheckedIn = isCheckedIn;
                    if (isCheckedIn) {
                        $scope.status = 'success';
                        $scope.buttonText = 'Zeitmessung abschließen';
                    } else {
                        $scope.status = 'warning';
                        $scope.buttonText = 'Zeitmessung starten';
                    }
                });

                durationFactory.getSum($scope.year, $scope.month, function (sum) {
                    $scope.sum = sum;
                });
                durationFactory.get($scope.year, $scope.month, function (durations) {
                    $scope.durations = durations;
                });
            }

            $scope.delete = function (id) {
                durationFactory.delete(id, function () {
                    init();
                    $rootScope.notifications.push({
                        message: 'Löschen erfolgreich',
                        timestamp: Date.now(),
                        status: 'success'
                    });
                });
            };
            $scope.nextMonth = function () {
                monthShift++;
                init();
            };
            $scope.previousMonth = function () {
                monthShift--;
                init();
            };
            init();
        }]);
}());
