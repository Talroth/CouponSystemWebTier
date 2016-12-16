var app = angular.module('admin', ['ngMaterial','md.data.table']);
app.controller('adminController', ['$scope', '$http', '$location', '$mdDialog', '$mdToast','$rootScope','$timeout','$window', function ($scope, $http, $location, $mdDialog, $mdToast, $rootScope,$timeout,$window) {

$scope.customerHeaders = [{"name":'Id'},{"name":'Customer name'},{"name":'Password'}];
$scope.companyHeaders = [{"name":'Id'},{"name":'Company name'},{"name":'Password'},{"name":'email'}];
$scope.couponHeaders = [{"name" : 'Id'},{"name" : 'Title'},{"name" : 'Start Date'},{"name" : 'End Date'},{"name" : 'Amount'},{"name" : 'Coupon Type'}];
var path = 'http://' + $location.host() + ':' + $location.port() + '/CouponSystemWebTier/rest/adminService'; 
$scope.showCoupons = false;


$scope.chosenUserType = 'admin';

//Testing

$scope.charging = function() {
	$http({		  
		  url: 'http://localhost:8080/CouponSystemWebTier/rest/adminService/viewAllIncomes', 
		  method: 'GET',  
		    content: 'application/json',
		    accepts: 'application/json'
		  }).success(function(response) {
		    // $scope.customerList = response;
			  $scope.incomesList = response;
			  console.log(response);

		}).error(function(response) {
		     //console.log("error occurred."); 
		     //$scope.openToast("Problem with the server connection, please try to login again")
		   });
	
}

$scope.viewIncomeByCustomer = function(customer) {
	$http({		  
		  url: 'http://localhost:8080/CouponSystemWebTier/rest/adminService/viewIncomeByCustomer', 
		  method: 'POST',  
		  data: customer,
		    content: 'application/json',
		    accepts: 'application/json'
		  }).success(function(response) {
			  $scope.incomesList = response;
			  console.log(response);

		}).error(function(response) {
		     //console.log("error occurred."); 
		     //$scope.openToast("Problem with the server connection, please try to login again")
		   });
	
}
//**********************************************************************

/* Customer section */
$scope.getAllCustomer = function() {
$http({
  url: path + '/getAllCustomer', 
  method: 'GET',  
  header: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
  }).success(function(response) {
     $scope.customerList = response;

}).error(function(response) {
     console.log("error occurred."); 
     $scope.openToast("Problem with the server connection, please try to login again")
   });
}

$scope.updateCustomer = function(customer) {	
 $http({
  url: path + '/updateCustomer/', 
  method: 'PUT',  
    data: customer,
    accepts: 'text/plain',
  }).success(function(response) {
     console.log(response); 
     console.log(customer);
	 $scope.openToast(customer.custName + " was updated")
     $scope.response = response;

}).error(function(response) {
     console.log("error occurred."); 
     $scope.errorToast();
 /*    $scope.response = response; */
   });   
 
}

$scope.removeCustomer = function(customer) {	
	 return $http({
	  url: path + '/removeCustomer/', 
	  method: 'DELETE',  
	    data: customer,
	    content: 'application/json',
	    accepts: 'text/plain'
	  }).success(function(response) {
	     console.log(response); 
	     console.log(customer); 

	}).error(function(response) {
	     console.log("error occurred."); 
	     console.log(customer);
	     $scope.errorToast();
	   });   
	 
	}

/* need to put 'return' at the beginning in order to use promise later */
$scope.createCustomer = function(customer) {	
	 return $http({
	  url: path + '/createCustomer', 
	  method: 'POST',  
	    data: customer,
	    accepts: 'application/json'
	  }).success(function(response) {
	     console.log("response from createCustomer: " + response.id); 
	     console.log("customer from createCustomer: " + customer.id);
	     console.log("data: " + response.id); 
	     
	}).error(function(response) {
	     console.log("error occurred."); 
	     console.log(customer);
	     console.log(response); 
	     $scope.errorToast();
	   }); 
	 
	}


/* Company section */

$scope.getAllCompanies = function() {
	$http({
	  url: path + '/getAllCompanies', 
	  method: 'GET',  
	    content: 'application/json',
	    accepts: 'application/json'
	  }).success(function(response) {
	     $scope.companyList = response;

	}).error(function(response) {
	     console.log("error occurred."); 
	     $scope.errorToast();
	   });
	}

$scope.createCompany = function(company) {	
	 return $http({
	  url: path + '/createCompany', 
	  method: 'POST',  
	    data: company,
	    accepts: 'application/json'
	  }).success(function(response) {
	     console.log("response from createCompany: " + response.id); 
	     console.log("company from createCompany: " + company.id);
	     console.log("data: " + response.id); 
	     
	}).error(function(response) {
	     console.log("error occurred."); 
	     console.log(company);
	     console.log(response); 
	     $scope.errorToast();
	   }); 
	 
	}

$scope.removeCompany = function(company) {	
	 return $http({
	  url: path + '/removeCompany/', 
	  method: 'DELETE',  
	    data: company,
	    content: 'application/json',
	    accepts: 'text/plain'
	  }).success(function(response) {
	     console.log(response); 
	     console.log(company); 

	}).error(function(response) {
	     console.log("error occurred."); 
	     console.log(company);
	     $scope.errorToast();
	   });   
	 
	}

$scope.updateCompany = function(company) {	
	 $http({
	  url: path + '/updateCompany/', 
	  method: 'PUT',  
	    data: company,
	    accepts: 'text/plain'
	  }).success(function(response) {
	     console.log(response); 
	     console.log(company);

	    $scope.openToast(company.compName + " was updated");

	}).error(function(response) {
	     console.log("error occurred."); 
	     $scope.errorToast();

	   });   
	 
	}

/* Coupons */

$scope.getCoupons = function(customer) {
	return $http({
	  url: path + '/getCoupons/', 
	  method: 'POST',  
	  data: customer,
	    content: 'application/json',
	    accepts: 'application/json'
	  }).success(function(response) {
	     console.log("ok");
	     console.log(response);

	}).error(function(response) {
	     console.log("error occurred."); 
	     $scope.errorToast();
	   });
	}

/* Dialog */


  $scope.showCreateDialog = function($event,customerOrCompany) {
	  if (customerOrCompany === 'Customer')
		  {
      var parentEl = angular.element(document.body);
      $mdDialog.show({
        parent: parentEl,
        scope: $scope,
        preserveScope: true,
        targetEvent: $event,
        template:
          '<md-dialog aria-label="List dialog">' +
          '  <md-dialog-content>'+
      	'<md-input-container class="md-block" flex-gt-sm>' +
      	'<label>New customer name</label>'  +
      	'<input ng-model="newCustName">'  +
          '</md-input-container>' +
          '<md-input-container class="md-block" flex-gt-sm>' +
          '<label>New customer password</label>'  +
        		  '<input ng-model="newCustPassword">'  +
          '</md-input-container>' +
          '  </md-dialog-content>' +
          '  <md-dialog-actions>' +
          '    <md-button ng-click="closeDialog()" class="md-primary">' +
          '      Close Dialog' +
          '    </md-button>' +
          '    <md-button ng-click="AddCustomer(newCustName,newCustPassword)" class="md-primary">' +
          '      Add' +
          '    </md-button>' +
          '  </md-dialog-actions>' +
          '</md-dialog>',

        controller: DialogController
     })
     
		  }
	  else if (customerOrCompany === 'Company')
		  {
	      var parentEl = angular.element(document.body);
	      $mdDialog.show({
	        parent: parentEl,
	        scope: $scope,
	        preserveScope: true,
	        targetEvent: $event,
	        template:
	          '<md-dialog aria-label="List dialog">' +
	          '  <md-dialog-content>'+
	      	'<md-input-container class="md-block" flex-gt-sm>' +
	      	'<label>New company name</label>'  +
	      	'<input ng-model="newCompName">'  +
	          '</md-input-container>' +
	          '<md-input-container class="md-block" flex-gt-sm>' +
	          '<label>New company password</label>'  +
	        		  '<input ng-model="newCompPassword">'  +
	          '</md-input-container>' +
	          '<md-input-container class="md-block" flex-gt-sm>' +
	         '<label>New company email</label>'  +
	        	'<input ng-model="newCompEmail">'  +
	          '</md-input-container>'  +
	          '  </md-dialog-content>' +
	          '  <md-dialog-actions>' +
	          '    <md-button ng-click="closeDialog()" class="md-primary">' +
	          '      Close Dialog' +
	          '    </md-button>' +
	          '    <md-button ng-click="AddCompany(newCompName,newCompPassword,newCompEmail)" class="md-primary">' +
	          '      Add' +
	          '    </md-button>' +
	          '  </md-dialog-actions>' +
	          '</md-dialog>',

	        controller: DialogController
	     })
		  }
     function DialogController($scope, $mdDialog) {

	      
       $scope.closeDialog = function() {
         $mdDialog.hide();
       }
       $scope.AddCustomer = function(newCustName,newCustPassword)
       {
    	      this.newLocalCustomer = {custName : newCustName, password : newCustPassword, id : '0'}; 
    	      console.log("+ " + this.newLocalCustomer.custName + "," + this.newLocalCustomer.password);
    	         	       
    	      	  $scope.createCustomer(this.newLocalCustomer).then(function(response) {
    	      		$scope.customerList = $scope.customerList.concat(response.data);	
    	      		$scope.openToast(response.data.custName + " was added");
    	      	  },function(error) { 
       	      		  if (error.data.message === undefined)
   	      			  {
   	      			$scope.errorToast();
   	      			  }
   	      		  else
   	      			  {
    	      		  console.log(error.data.message);
    	      		  $scope.openToast(error.data.message);
   	      			  }
    	      		  });
    	    	   
    		       $scope.newCustPassword = '';
    	  	       $scope.newCustName = ''; 
    	  	       $mdDialog.hide(); 
       }
    	      
    	          $scope.AddCompany = function(newCompName,newCompPassword,newCompEmail)
    	          {
    	        	  console.log("Enter addcompany method");
    	       	      this.newLocalCompany = {compName : newCompName, password : newCompPassword, email : newCompEmail, id : '0'}; 
    	       	      console.log("+ " + this.newLocalCompany.compName + "," + this.newLocalCompany.password);
    	       	         	       
    	       	      	  $scope.createCompany(this.newLocalCompany).then(function(response) {
    	       	      		$scope.companyList = $scope.companyList.concat(response.data);	
    	       	      		$scope.openToast(response.data.compName + " was added");
    	       	      	  },function(error) { 
    	       	      		  if (error.data.message === undefined)
    	       	      			  {
    	       	      			$scope.errorToast();
    	       	      			  }
    	       	      		  else
    	       	      			  {
    	       	      		  console.log(error.data.message);
    	       	      		  $scope.openToast(error.data.message);
    	       	      	  }
    	       	      		  });  
    	       	      	  
    	       	      	$scope.newCompName = '';
    	       	      	$scope.newCompPassword = '';
    	       	      	$scope.newCompEmail = '';
    	       	      	$mdDialog.hide(); 
    	          }

   }
            
 }

 /* Delete customer dialog */
  
	  $scope.removeDialog = function($event, Index, individual, list,customerOrCompany) {
	      var parentEl = angular.element(document.body);
	      $mdDialog.show({
	        parent: parentEl,
	        scope: $scope,
	        preserveScope: true,
	        targetEvent: $event,
	        template:
	          '<md-dialog aria-label="List dialog">' +
	          '  <md-dialog-content>'+
	      	'<md-input-container class="md-block" flex-gt-sm>' +
	          '<label>Would you like to delete the customer?</label>' +
	          '  <md-dialog-actions>' +
	          '    <md-button ng-click="closeDialog()" class="md-primary">' +
	          '      No' +
	          '    </md-button>' +
	          '    <md-button ng-click="Remove()" class="md-primary">' +
	          '      Yes' +
	          '    </md-button>' +
	          '  </md-dialog-actions>' +
	          '</md-dialog>',
	        controller: DialogController
	     })
	     
	      
	     function DialogController($scope, $mdDialog) {

	       var removing;  
	       var individualText;
	       $scope.closeDialog = function() {
	         $mdDialog.hide();
	       }
	       
	       $scope.Remove = function()
	       {
	    	  if (customerOrCompany === 'Customer')
	    		  {
	    		  	this.plannedRemoveIndividual = "{\"custName\" : \"" + individual.custName + "\", \"password\" : \"" + individual.password + "\", \"id\" : \"" + individual.id.toString() + "\"}";
	    		  	removing = $scope.removeCustomer;
	    		  	individualText = individual.custName;
	    		  }
	    	  else if (customerOrCompany === 'Company')
	    		  {
	    		  	this.plannedRemoveIndividual = "{\"compName\" : \"" + individual.compName + "\", \"password\" : \"" + individual.password + "\", \"email\" : \"" + individual.email + "\", \"id\" : \"" + individual.id.toString() + "\"}";
	    		  	removing = $scope.removeCompany;
	    		  	individualText = individual.compName;
	    		  }
	    	  
	    	   
	    	  
	 	      console.log("-`-`-`");
		      console.log(this.plannedRemoveIndividual);
		      console.log("-`-`-`");
		      removing(this.plannedRemoveIndividual).then(function(response){
		      list.splice(Index,1);
		      $scope.openToast(individualText + " was removed");
	       },function(error) {
	      		  if (error.data.message === undefined)
	      			  {
	      			$scope.errorToast();
	      			  }
	      		  else
	      			  {
	    	   $scope.openToast(error.data.message);
	      			  }
	       });
	    	  $mdDialog.hide(); 
	    	   
	       }

	   }
	            
	 }

	  $scope.logout = function() {
		    $http({
		        url: "http://" + $location.host() + ":" + $location.port() + "/CouponSystemWebTier/rest/adminService/logout",
		        method: 'POST'
		    }).success(function(response) {
		    	$window.location.href = 'http://' + $location.host() + ':' + $location.port() + '/CouponSystemWebTier/views/login.html';
		    }).error(function (response) {console.log("error occurred."); 
		                                 });
		}
	  
/* Toast affect function */
  $scope.openToast = function(msg) {
      $mdToast.show(
              $mdToast.simple()
                 .textContent(msg)                       
                 .hideDelay(3000)
           );
	  };
	

  $scope.openCouponsTable = function(customer)
  {
	  $scope.showCoupons = true;
	  $scope.getCoupons(customer).then(function(response) {
		  $scope.couponList = response.data;
		  console.log("** " + response.data.id);
		  });
	  
  }
  
  $scope.errorToast = function() {
	  $scope.openToast("Problem with the server connection, please try to login again");
  }
  
}]);

// allow angularjs to send for DELETE JSON format
app.config(function($httpProvider) {
	  /**
	   * make delete type json
	   */
	  $httpProvider.defaults.headers["delete"] = {
	    'Content-Type': 'application/json;charset=utf-8'
	  };
	}

)
