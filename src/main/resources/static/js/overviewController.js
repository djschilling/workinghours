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
            CheckInFactory.isCheckedIn(function(isCheckedIn) {
               $scope.isCheckedIn = isCheckedIn;
               if(isCheckedIn) {
                   $scope.status = 'success';
                   $scope.buttonText = 'Laufende Zeitmessung abschließen';
               } else {
                   $scope.status = 'warning';
                   $scope.buttonText = 'Neue Zeitmessung starten';
               }

            });
            durationFactory.getSum(function (sum) {
                $scope.sum = sum;
            });
            durationFactory.getForCurrentMonth(function (durations) {
                $scope.durations = durations;
            });
        }]);
}());
