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


            CheckInFactory.isCheckedIn(function (isCheckedIn, checkIn) {
                $scope.isCheckedIn = isCheckedIn;
                if (isCheckedIn) {
                    $scope.from = checkIn.startTime.getHours() + ":" + checkIn.startTime.getMinutes();
                    $scope.to = now.getHours() + ":" + now.getMinutes();
                } else {
                    $scope.from = now.getHours() + ":" + now.getMinutes();
                }
                $scope.checkOut = function () {
                    var to = durationFactory.convertToDateArray(durationFactory.convertToDateObject($scope.day, $scope.to));
                    if (!durationValidationFactory.isValidTime($scope.to)) {
                        $rootScope.notifications.push({
                            message: 'Von und Bis muss in folgendem Format sein: HH-MM',
                            timestamp: Date.now(),
                            status: 'danger'
                        });
                        return;
                    }

                    CheckInFactory.checkOut(to, function () {
                        $rootScope.notifications.push({
                            message: 'Checkout erfolgreich.',
                            timestamp: Date.now(),
                            status: 'success'
                        });
                        $location.path('/');
                    }, function (response) {
                        $rootScope.notifications.push({
                            message: response,
                            timestamp: Date.now(),
                            status: 'danger'
                        });
                    });

                };
            });

            $scope.checkIn = function () {
                if (!durationValidationFactory.isValidTime($scope.from)) {
                    $rootScope.notifications.push({
                        message: 'Von muss in folgendem Format sein: HH-MM',
                        timestamp: Date.now(),
                        status: 'danger'
                    });
                }
                var from = durationFactory.convertToDateArray(durationFactory.convertToDateObject($scope.day, $scope.from));
                CheckInFactory.checkIn(from, function () {
                    $rootScope.notifications.push({
                        message: 'Checkin erfolgreich.',
                        timestamp: Date.now(),
                        status: 'success'
                    });
                    $location.path('/');
                }, function errorCreateCheckIn(response) {
                    $rootScope.notifications.push({
                        message: response,
                        timestamp: Date.now(),
                        status: 'danger'
                    });
                });
            };
        }]);
}());
