
var app = angular.module('customer', ['ngMaterial']);
app.controller('customerController', ['$scope', '$http', '$location', '$mdDialog','$mdToast','$window', function ($scope, $http, $location, $mdDialog, $mdToast, $window) {

var path = 'http://' + $location.host() + ':' + $location.port() + '/CouponSystemWebTier/rest/customerService';    
$scope.init = function() {
    $scope.isAll = false;
    $scope.switchTitle = "Gel all coupons"
    
 
    $scope.couponType = ["RESTURANS", "ELECTRICITY", "FOOD", "HEALTH", "SPORTS", "CAMPING", "TRAVELLING", "OTHER"];
    $scope.couponList = this.getAllPurchasedCoupons();
}   


// purchase new coupon right after execute the popConfirmation method - need to be fixed
$scope.popConfirmation = function($event,coupon) {

    var parentEl = angular.element(document.body);
    $mdDialog.show({
      parent: parentEl,
      scope: $scope,
      preserveScope: true,
      targetEvent: $event,
      template:
        '<md-dialog md-theme="default" aria-label="Lucky day" ng-class="dialog.css" class="_md md-default-theme md-transition-in" role="dialog" tabindex="-1" aria-describedby="dialogContent_12">' +
        '  <md-dialog-content class="md-dialog-content" role="document" tabindex="-1" id="dialogContent_12">'+
        '<h2 class="md-title ng-binding">Are you sure you want to purchase this coupon ?</h2>' +
        '<p class="ng-binding">Approve the purchasing process for ' + coupon.title + ' coupon</p>' +
        '  </md-dialog-content>' +
        '  <md-dialog-actions>' +
        '    <md-button ng-click="closeDialog()" class="md-primary md-button ng-scope md-default-theme md-ink-ripple">' +
        '      Close Dialog' +
        '    </md-button>' +
        '    <md-button ng-click="customerBuyCoupon()" class="md-primary md-button md-default-theme md-ink-ripple">' +
        '      Add' +
        '    </md-button>' +
        '  </md-dialog-actions>' +
        '</md-dialog>',

      controller: DialogController
   })
   
   function DialogController($scope, $mdDialog) {
	      
     $scope.closeDialog = function() {
       $mdDialog.hide();
     }
     $scope.customerBuyCoupon = function() {
    	 console.log(coupon);
         $scope.purchaseCoupon(coupon).then(function(response){
       	  console.log(response);
       	  console.log(response.data);
       	  console.log(response.data=="ok");
       	  if (response.data=="ok")
       		  {
       		 console.log("okkkkk");
       		$mdDialog.hide();
       		 $scope.purchaseSuccess($event,coupon.title);
       		  }
       	  else
       		  {
       		  console.log("not okkkkk");
       		
      		$scope.openToast(response.data);       		
       		  }
       	
         },function(error){
       	  console.log("error");
       	  console.log(response);
       	  $scope.errorToast();
         });
         $mdDialog.hide();
    }
}
}

  	      

$scope.couponListSwitch = function() {
    if ($scope.isAll)
        {
            $scope.getAllPurchasedCoupons();
            $scope.switchTitle = "Gel all coupons"
        }
    else
        {
            $scope.getAllCoupons();  
            $scope.switchTitle = "Gel only purchased coupons"
        }
    $scope.isAll = !$scope.isAll;
}   

$scope.getAllCoupons = function() {
$http({
  url: path + '/getAllCoupons', 
  method: 'GET',  
    accepts: 'application/json'
  }).success(function(response) {
     $scope.couponList = response;

}).error(function(response) {
     console.log("error occurred."); 
   });
}

$scope.getCoupon = function(id) {
$http({
  url: path + '/getCoupon/' + id, 
  method: 'GET',  
    accepts: 'application/json'
  }).success(function(response) {
     $scope.couponList = response;

}).error(function(response) {
     console.log("error occurred."); 
   });
}

$scope.getAllPurchasedCoupons = function() {
$http({
  url: path + '/getAllPurchasedCoupons', 
  method: 'GET',  
    accepts: 'application/json'
  }).success(function(response) {
     $scope.couponList = response;

}).error(function(response) {
     console.log("error occurred."); 
   });
}

$scope.getAllPurchasedCouponsByType = function(couponType) {
$http({
  url: path + '/getAllPurchasedCouponsByType/' + couponType, 
  method: 'GET',  
    accepts: 'application/json'
  }).success(function(response) {
     $scope.couponList = response;

}).error(function(response) {
     console.log("error occurred."); 
     $scope.openToast("You must set type");
   });
}

$scope.getAllPurchasedCouponsByPrice = function(price) {
$http({
  url: path + '/getAllPurchasedCouponsByPrice/' + price, 
  method: 'GET',  
    content: 'application/json',
    accepts: 'application/json'
  }).success(function(response) {
     $scope.couponList = response;

}).error(function(response) {
     console.log("error occurred."); 

	 	$scope.openToast("You must set max price");

   });
}

$scope.purchaseCoupon = function(coupon) {
return $http({
  url: path + '/purchaseCoupon/', 
  method: 'POST',  
    data: coupon,
    accepts: 'text/plain'
  }).success(function(response) {
     console.log(response); 
     
     
}).error(function(response) {
     console.log("error occurred."); 
   });
}

$scope.getUserName = function() {
$http({
  url: path + '/getUserName/', 
  method: 'GET',  
    accepts: 'text/plain'
  }).success(function(response) {
    console.log(response);
    return response;
      

}).error(function(response) {
     console.log("error occurred."); 
   });
}


$scope.purchaseSuccess = function(ev,couponName) {
  // Appending dialog to document.body to cover sidenav in docs app
  // Modal dialogs should fully cover application
  // to prevent interaction outside of dialog
  $mdDialog.show(
    $mdDialog.alert()
      .parent(angular.element(document.body))
      .clickOutsideToClose(true)
      .title('Purchasing was performed successfully')
      .textContent('Thank you for purchase ' + couponName + ' coupon, we sure you will enjoy')
      .ok('OK')
      .targetEvent(ev)
  );
};

$scope.logout = function() {
    $http({
        url: "http://" + $location.host() + ":" + $location.port() + "/CouponSystemWebTier/rest/customerService/logout",
        method: 'POST'
    }).success(function(response) {
    	$window.location.href = 'http://' + $location.host() + ':' + $location.port() + '/CouponSystemWebTier/views/login.html';
    }).error(function (response) {console.log("error occurred."); 
                                 });
}
    
$scope.errorToast = function() {
	  $scope.openToast("Problem with the server connection, please try to login again");
}

$scope.openToast = function(msg) {
    $mdToast.show(
            $mdToast.simple()
               .textContent(msg)                       
               .hideDelay(3000)
         );
	  };
	  
}]);

app.directive('onErrorSrc', function() {
    return {
        link: function(scope, element, attrs) {
          element.bind('error', function() {
            if (attrs.src != attrs.onErrorSrc) {
              attrs.$set('src', attrs.onErrorSrc);
            }
          });
        }
    }
});
                                   
