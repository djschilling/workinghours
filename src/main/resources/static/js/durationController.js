(function () {
    'use strict';

    var controllersModul;
    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }

    controllersModul.controller('DurationController', ['$scope', 'durationFactory', '$rootScope', '$location', function ($scope, durationFactory, $rootScope, $location) {

        var now = new CustomDate();
        $scope.day = now.getYear() + "-" + now.getMonth() + "-" + now.getDay();

        $scope.from = now.getHours() + ":" + now.getMinutes();
        $scope.to = now.getHours() + ":" + now.getMinutes();

        $scope.submit = function () {

            var from = durationFactory.convertToDateObject($scope.day, $scope.from);
            var to = durationFactory.convertToDateObject($scope.day, $scope.to);

            durationFactory.save(from, to, function () {
                $rootScope.notifications.push({message: 'Erfolgreich gespeichert.', timestamp: Date.now(), status: 'success'});
                $location.path('/');
            });
        };
    }]);
}());
