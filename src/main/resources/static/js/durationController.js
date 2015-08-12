(function () {
    'use strict';

    var controllersModul;
    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }

    controllersModul.controller('DurationController', ['$scope', 'durationFactory', '$rootScope', '$location', '$routeParams', 'durationValidationFactory',
        function ($scope, durationFactory, $rootScope, $location, $routeParams, durationValidationFactory) {

            if ($routeParams.id) {
                $scope.update = true;
                $scope.title = "Zeiterfassung anpassen";
                durationFactory.getById($routeParams.id, function (duration) {
                    $scope.day = duration.startTime.getDay() + "-" + duration.startTime.getMonth() + "-" + duration.startTime.getYear();
                    $scope.from = duration.startTime.getHours() + ":" + duration.startTime.getMinutes();
                    $scope.to = duration.endTime.getHours() + ":" + duration.endTime.getMinutes();
                });

            } else {
                $scope.update = false;
                $scope.title = "Neue Zeiterfassung anlegen";
                var now = new CustomDate();
                $scope.day = now.getDay() + "-" + now.getMonth() + "-" + now.getYear();

                $scope.from = now.getHours() + ":" + now.getMinutes();
                $scope.to = now.getHours() + ":" + now.getMinutes();

            }
            $scope.submit = function () {

                if(!durationValidationFactory.isValidDay($scope.day)){
                    $rootScope.notifications.push({
                        message: 'Tag muss in folgendem Format sein: DD-MM-YYY',
                        timestamp: Date.now(),
                        status: 'danger'
                    });
                    return;
                }
                if (!durationValidationFactory.isValidTime($scope.from) || !durationValidationFactory.isValidTime($scope.to)){
                    $rootScope.notifications.push({
                        message: 'Von und Bis muss in folgendem Format sein: HH-MM',
                        timestamp: Date.now(),
                        status: 'danger'
                    });
                    return;
                }
                if(!durationValidationFactory.isToAfterFrom($scope.day, $scope.to, $scope.from)){
                    $rootScope.notifications.push({
                        message: "'Bis' muss nach 'Von' sein",
                        timestamp: Date.now(),
                        status: 'danger'
                    });
                    return;
                }
                var from = durationFactory.convertToDateObject($scope.day, $scope.from);
                var to = durationFactory.convertToDateObject($scope.day, $scope.to);

                var success = function () {
                    $rootScope.notifications.push({
                        message: 'Erfolgreich gespeichert.',
                        timestamp: Date.now(),
                        status: 'success'
                    });
                    $location.path('/');
                };
                var error = function (response) {
                    $rootScope.notifications.push({
                        message: response,
                        timestamp: Date.now(),
                        status: 'danger'
                    });
                };
                if ($scope.update) {
                    durationFactory.update(from, to, $routeParams.id, success);
                } else {
                    durationFactory.create(from, to, success, error);
                }
            };
        }]);
}());
