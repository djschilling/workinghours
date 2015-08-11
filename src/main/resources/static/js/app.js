(function () {

    'use strict';

    var dccModul = angular.module('workinghours', ['dcc.controller', 'dcc.factories', 'dcc.filter', 'ngCookies', 'ngRoute']);


    dccModul.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
        $routeProvider.when('/duration/new', {
            templateUrl: '/partials/durationNew.html',
            controller: 'DurationController'
        });
        $routeProvider.when('/duration/:id', {
            templateUrl: '/partials/durationNew.html',
            controller: 'DurationController'
        });
        $routeProvider.when('/login', {templateUrl: '/partials/login.html', controller: 'LoginController'});
        $routeProvider.when('/adminoverview', {
            templateUrl: '/partials/adminoverview.html',
            controller: 'AdminOverviewController'
        });
        $routeProvider.when('/users/new', {templateUrl: '/partials/userNew.html', controller: 'NewUserController'});
        $routeProvider.when('/checkin', {templateUrl: '/partials/checkIn.html', controller: 'CheckInController'});
        $routeProvider.when('/changepassword', {
            templateUrl: '/partials/changepassword.html',
            controller: 'ChangePasswordController'
        });
        $routeProvider.otherwise({templateUrl: '/partials/overview.html', controller: 'OverviewController'});

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]);
}());
