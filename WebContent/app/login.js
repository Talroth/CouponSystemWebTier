
var app = angular.module('login', ['ngMaterial']);
app.controller("loginController", ['$scope', '$http', '$window', '$location', function ($scope, $http, $window, $location) {



$scope.chosenUserType = 'admin';


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
        		$scope.logStat = "Please contact admin for furher assistance";
        		
        	}

    })
        .error(function(response){console.log("error occurred."); 
        						$scope.logStat = "Please contact admin for furher assistance";
                                 });
}



}]);

