(function () {
  'use strict';

  var controllersModul;
  try {
    controllersModul = angular.module('dcc.controller');
  } catch (e) {
    controllersModul = angular.module('dcc.controller', []);
  }

  controllersModul.controller('NotificationController', ['$scope', '$rootScope', function ($scope, $rootScope) {
    $rootScope.notifications = [];
    window.setInterval(function(){
      $rootScope.notifications.forEach(function (notification, index) {
        var duration = (Date.now() - notification.timestamp);
        if((( duration) / 1000) > 3){
          $rootScope.notifications.splice(index, 1);
        }
      });
      $scope.$apply();
    }, 1000);
  }]);
}());
