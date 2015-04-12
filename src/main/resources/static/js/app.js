(function () {

    'use strict';

    var dccModul = angular.module('workinghours', ['dcc.controller', 'ngCookies', 'ngRoute']);


    dccModul.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/duration/new', {templateUrl: '/partials/durationNew.html', controller: 'DurationController'});
        $routeProvider.otherwise({template: "/partials/foo.html"});
    }]);
}());