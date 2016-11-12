/**
 * 
 */
/* Toast affect function */
var app = angular.module('general', ['ngMaterial','md.data.table']);
app.controller('generalController', ['$scope', '$http', '$location', '$mdDialog', '$mdToast','$timeout', function ($scope, $http, $location, $mdDialog, $mdToast,$timeout) {
  $scope.openToast = function(msg) {
      $mdToast.show(
              $mdToast.simple()
                 .textContent(msg)                       
                 .hideDelay(3000)
           );
	  };
}