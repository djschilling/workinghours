(function () {
    'use strict';

    var controllersModul;

    try {
        controllersModul = angular.module('dcc.controller');
    } catch (e) {
        controllersModul = angular.module('dcc.controller', []);
    }


    controllersModul.controller('NavBarController', [function () {
        var navBar = $('#navbar');
        navBar.find('a').click(function () {
            if (navBar.hasClass('in')) {
                navBar.collapse('toggle');
            }
        });
    }]);
}());