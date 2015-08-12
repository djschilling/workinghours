(function () {
    'use strict';

    var controllersModul;
    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }

    controllersModul.controller('CheckInController', ['$scope', 'CheckInFactory', 'durationFactory', '$location', '$rootScope', 'durationValidationFactory',
        function ($scope, CheckInFactory, durationFactory, $location, $rootScope, durationValidationFactory) {
            var now = new CustomDate();
            $scope.day = now.getDay() + "-" + now.getMonth() + "-" + now.getYear();


            CheckInFactory.isCheckedIn(function (isCheckedIn, lastDuration) {
                $scope.isCheckedIn = isCheckedIn;
                if (isCheckedIn) {
                    $scope.from = lastDuration.startTime.getHours() + ":" + lastDuration.startTime.getMinutes();
                    $scope.to = now.getHours() + ":" + now.getMinutes();
                } else {
                    $scope.from = now.getHours() + ":" + now.getMinutes();
                }
                $scope.checkOut = function () {
                    var from = durationFactory.convertToDateObject($scope.day, $scope.from);
                    var to = durationFactory.convertToDateObject($scope.day, $scope.to);
                    if (!durationValidationFactory.isValidTime($scope.from) || !durationValidationFactory.isValidTime($scope.to)){
                        $rootScope.notifications.push({
                            message: 'Von und Bis muss in folgendem Format sein: HH-MM',
                            timestamp: Date.now(),
                            status: 'danger'
                        });
                        return;
                    }

                    durationFactory.update(from, to, lastDuration.id, function () {
                        $rootScope.notifications.push({
                            message: 'Checkout erfolgreich.',
                            timestamp: Date.now(),
                            status: 'success'
                        });
                        $location.path('/');
                    });

                };
            });

            $scope.checkIn = function () {
                if(!durationValidationFactory.isValidTime($scope.from)){
                    $rootScope.notifications.push({
                        message: 'Von muss in folgendem Format sein: HH-MM',
                        timestamp: Date.now(),
                        status: 'danger'
                    });
                }
                var from = durationFactory.convertToDateObject($scope.day, $scope.from);
                durationFactory.create(from, null, function () {
                    $rootScope.notifications.push({
                        message: 'Checkin erfolgreich.',
                        timestamp: Date.now(),
                        status: 'success'
                    });
                    $location.path('/');
                });
            };
        }]);
}());
