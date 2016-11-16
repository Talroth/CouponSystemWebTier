
var app = angular.module('login', ['ngMaterial']);
app.controller("loginController", ['$scope', '$http', '$window', '$location', function ($scope, $http, $window, $location) {

//$scope.chosenUserType = 'customer';
//$scope.userName = 'Bill Joe';
//$scope.password = 'bj';
//
$scope.chosenUserType = 'admin';
//$scope.userName = 'admin';
//$scope.password = '1234';

//$scope.chosenUserType = 'company';
//$scope.userName = 'McHoland';
//$scope.password = 'f12e12';

$scope.login = function() {
    $http({
    	 url: "http://" + $location.host() + ":" + $location.port() + "/CouponSystemWebTier/rest/" + $scope.chosenUserType + "Service/login?user=" + $scope.userName + "&password=" + $scope.password,    
        method: 'POST'
    }).success(function(response){
        $scope.logStat = response;
        if (response == 'ok')
        	{
        		$window.location.href = "http://" + $location.host() + ":" + $location.port() + "/CouponSystemWebTier/views/" + $scope.chosenUserType + ".html";
        	}
        else
        	{
        	
        	}

    })
        .error(function(response){console.log("error occurred."); 
                                 });
}

$scope.logout = function() {
    $http({
        url: "http://" + $location.host() + ":" + $location.port() + "/CouponSystemWebTier/rest/customerService/logout",
        method: 'POST'
    }).success(function(response) {
        $scope.test = response;
    }).error(function (response) {console.log("error occurred."); 
                                 });
}

}]);

