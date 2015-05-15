(function () {

    'use strict';

    var dccModul = angular.module('workinghours', ['dcc.controller', 'dcc.factories', 'dcc.filter', 'ngCookies', 'ngRoute']);


    dccModul.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/duration/new', {templateUrl: '/partials/durationNew.html', controller: 'DurationController'});
        $routeProvider.otherwise({templateUrl: '/partials/overview.html', controller: 'OverviewController'});
    }]);
}());