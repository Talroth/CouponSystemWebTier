
var app = angular.module('login', ['ngMaterial']);
app.controller("loginController", ['$scope', '$http', '$window', '$location', function ($scope, $http, $window, $location) {

$scope.login = function() {
    $http({
       //  url: 'http://localhost:8080/CouponSystemWebTier/rest/customerService/login?user=Bill Joe&password=bj',  
       //  url: 'http://localhost:8080/CouponSystemWebTier/rest/adminService/login?user=admin&password=1234',      
          url: 'http://localhost:8080/CouponSystemWebTier/rest/companyService/login?user=McHoland&password=f12e12',   
        method: 'POST'
    }).success(function(response){
        $scope.test = response;
       // $window.location.href = "http://" + $location.host() + ":" + $location.port() + "/CouponSystemWebTier/views/customer.html";  
       // $window.location.href = "http://" + $location.host() + ":" + $location.port() + "/CouponSystemWebTier/views/admin.html";     
          $window.location.href = "http://" + $location.host() + ":" + $location.port() + "/CouponSystemWebTier/views/company.html";   
        /* "/customer.html"; */
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

