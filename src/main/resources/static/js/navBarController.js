(function () {
    'use strict';

    var controllersModul;

    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }


    controllersModul.controller('NavBarController', ['userFactory', '$scope', function (userFactory, $scope) {
        var navBar = $('#navbar');
        navBar.find('a').click(function () {
            if (navBar.hasClass('in')) {
                navBar.collapse('toggle');
            }
        });

        userFactory.getCurrentUser(function (user) {
            $scope.user = user;
        });
    }]);
}());