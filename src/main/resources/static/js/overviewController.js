(function () {
    'use strict';

    var controllersModul;
    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }

    controllersModul.controller('OverviewController', ['$scope', 'durationFactory', '$rootScope', 'CheckInFactory',
        function ($scope, durationFactory, $rootScope, CheckInFactory) {
            function init() {
                CheckInFactory.isCheckedIn(function (isCheckedIn) {
                    $scope.isCheckedIn = isCheckedIn;
                    if (isCheckedIn) {
                        $scope.status = 'success';
                        $scope.buttonText = 'Zeitmessung abschließen';
                    } else {
                        $scope.status = 'warning';
                        $scope.buttonText = 'Zeitmessung starten';
                    }
                    var currentDate = new Date(Date.now());
                    $scope.currentMonth = currentDate.getMonth() + 1;
                    $scope.currentYear =  currentDate.getFullYear();

                });
                durationFactory.getSum(function (sum) {
                    $scope.sum = sum;
                });
                durationFactory.getForCurrentMonth(function (durations) {
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
            init();
        }]);
}());
