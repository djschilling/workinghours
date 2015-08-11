(function () {
    'use strict';

    var controllersModul;
    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }

    controllersModul.controller('DurationController', ['$scope', 'durationFactory', '$rootScope', '$location', '$routeParams',
        function ($scope, durationFactory, $rootScope, $location, $routeParams) {

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
                if ($scope.update) {
                    durationFactory.update(from, to, $routeParams.id, success);
                } else {
                    durationFactory.create(from, to, success);
                }
            };
        }]);
}());
