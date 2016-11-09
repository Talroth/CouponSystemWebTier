var app = angular.module('company', ['ngMaterial','md.data.table','ngFileUpload']);
app.controller('companyController', ['$scope', '$http', '$location', '$mdDialog', '$mdToast','$rootScope','$timeout','$mdDateLocale','Upload', function ($scope, $http, $location, $mdDialog, $mdToast, $rootScope,$timeout,$mdDateLocale,Upload) {
	$scope.couponHeaders = [{"name" : 'Id'},{"name" : 'Title'},{"name" : 'Start Date'},{"name" : 'End Date'},{"name" : 'Amount'},{"name" : 'Coupon Type'},{"name" : 'Message'}, {"name" : 'Price'}];
	var path = 'http://' + $location.host() + ':' + $location.port() + '/CouponSystemWebTier/rest/companyService'; 
	$scope.couponType = ["RESTURANS", "ELECTRICITY", "FOOD", "HEALTH", "SPORTS", "CAMPING", "TRAVELLING", "OTHER"];
	

//	$scope.minDate = new Date(
//		      $scope.myDate.getFullYear(),
//		      $scope.myDate.getMonth() - 2,
//		      $scope.myDate.getDate());
	
	
    $scope.upload = function (file) {
    	$scope.newImage = file.name;
        Upload.upload({
            url: path + '/uploadImage',
            data: {file: file, 'username': $scope.username}
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
            console.log(file);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };
    
    
	$scope.init = function() {
	$scope.getAllCoupons().then(function(response){
		$scope.couponList = response.data;
		$scope.dataMod = [];
		
		
	    for (i = 0; i < $scope.couponList.length; i += 1) {
	        $scope.dataMod.push({
	            dt: new Date($scope.couponList[i].endDate),
	            status: false
	        	
	        });
	           
	    }
	},function(error) {});
	
	$scope.getCompanyDetails().then(function(response){
		$scope.companyDetails = response.data;
	}, function(error) {})
	

	}
	
	$scope.filterByCouponType = function(coupType) {
		$scope.getCouponByType(coupType).then(function(response) {
			$scope.couponList = response.data;
			$scope.dataMod = [];
						
		    for (i = 0; i < $scope.couponList.length; i += 1) {
		        $scope.dataMod.push({
		            dt: new Date($scope.couponList[i].endDate),
		            status: false		        	
		        })}
		    
		}, function(error) {
			
		});
	}
	
	$scope.filterByPrice = function(minPrice,maxPrice) {
		$scope.getCouponByPrice(minPrice,maxPrice).then(function(response) {
			$scope.couponList = response.data;
			$scope.dataMod = [];
						
		    for (i = 0; i < $scope.couponList.length; i += 1) {
		        $scope.dataMod.push({
		            dt: new Date($scope.couponList[i].endDate),
		            status: false		        	
		        })}
		    
		}, function(error) {
			
		});
	}
	
	$scope.filterByEndDate = function(endDateFilter) {
		 $mdDateLocale.formatDateLocal = function(date) {
			    var day = ((date.getDate())>=10)? (date.getDate()) : '0' + (date.getDate());
			    var monthIndex = ((date.getMonth()+1)>=10)? (date.getMonth()) : '0' + (date.getMonth()); 
			    var year = date.getFullYear();

			    return year + '-' + (monthIndex + 1) + '-' + day + 'T00:00:00';
		}; 
		$scope.getCouponByEndDate($mdDateLocale.formatDateLocal(endDateFilter)).then(function(response) {
			$scope.couponList = response.data;
			$scope.dataMod = [];
						
		    for (i = 0; i < $scope.couponList.length; i += 1) {
		        $scope.dataMod.push({
		            dt: new Date($scope.couponList[i].endDate),
		            status: false		        	
		        })}
		    
		}, function(error) {
			
		});
	}
	

	
	
	$scope.getAllCoupons = function() {
		return $http({
		  url: path + '/getAllCoupons', 
		  method: 'GET',  
		    content: 'application/json',
		    accepts: 'application/json'
		  }).success(function(response) {
		     console.log("ok");
		     console.log(response);

		}).error(function(response) {
		     console.log("error occurred."); 
		     $scope.openToast(response.message)
		   });
		}
	
	$scope.createCoupon = function(coupon) {
		return $http({
			  url: path + '/createCoupon', 
			  method: 'POST',  
			  data: coupon,
			    content: 'application/json',
			    accepts: 'application/json'
			  }).success(function(response) {
			     console.log("ok");
			     console.log(response);

			}).error(function(response) {
			     console.log("error occurred."); 
			   });	
	}
	
	$scope.getCompanyDetails = function() {
		return $http({
		  url: path + '/getCompanyDetails', 
		  method: 'GET',  
		    content: 'application/json',
		    accepts: 'application/json'
		  }).success(function(response) {
		     console.log("ok");
		     console.log(response);

		}).error(function(response) {
		     console.log("error occurred."); 
		   });
		}
	
	$scope.updateCoupon = function(coupon,indx) {	

		 $mdDateLocale.formatDateLocal = function(date) {
			    var day = (date.getDate() >= 10) ? date.getDate() : "0" + date.getDate();
			    var monthIndex = date.getMonth();
			    var year = date.getFullYear();

			    return year + '-' + (monthIndex + 1) + '-' + day + 'T00:00';
		};  
		

		coupon.endDate = $mdDateLocale.formatDateLocal($scope.dataMod[indx].dt)

		
		 $http({
		  url: path + '/updateCoupon/', 
		  method: 'PUT',  
		    data: coupon,
		    accepts: 'text/plain'
		  }).success(function(response) {
		     console.log(response); 
		     if (response == "ok")
		    	 {
		    	 $scope.openToast(coupon.title + " was updated");
		    	 }
		     else
		    	 {
		    	 $scope.openToast(response);
		    	 }
			 

		}).error(function(response) {
		     console.log("error occurred."); 
		     console.log(response);
		     $scope.openToast(response.message)
		     $scope.response = response;
		   });   
		 
		}

    
	/* TODO: Add dialog with success option which remove the row in case remove done successfully */
    
	$scope.removeCoupon = function(coupon) {	
		 return $http({
		  url: path + '/removeCoupon/', 
		  method: 'DELETE',  
		    data: coupon,
		    content: 'application/json',
		    accepts: 'text/plain'
		  }).success(function(response) {
		     console.log(response); 
		     console.log(coupon); 

		}).error(function(response) {
		     console.log("error occurred."); 
		     console.log(response);
		   });   
		 
		}
	
	$scope.getCouponByType = function(coupType) {
		return $http({
			  url: path + '/getCouponByType/' + coupType, 
			  method: 'GET',  
			    content: 'application/json',
			    accepts: 'application/json'
			  }).success(function(response) {
			     console.log("ok");
			     console.log(response);

			}).error(function(response) {
			     console.log("error occurred."); 
			   });
	}
	
	$scope.getCouponByPrice = function(minPrice, maxPrice) {
		return $http({
			  url: path + '/getCouponByPrice/' + minPrice + "," + maxPrice, 
			  method: 'GET',  
			    content: 'application/json',
			    accepts: 'application/json'
			  }).success(function(response) {
			     console.log("ok");
			     console.log(response);

			}).error(function(response) {
			     console.log("error occurred."); 
			   });
	}
	
	$scope.getCouponByEndDate = function(endDate) {
		return $http({
			  url: path + '/getCouponByEndDate/' + endDate, 
			  method: 'GET',  
			    content: 'application/json',
			    accepts: 'application/json'
			  }).success(function(response) {
			     console.log("ok");
			     console.log(response);

			}).error(function(response) {
			     console.log("error occurred."); 
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
		  

		  $scope.addDialog = function($event) {
			  $scope.newEndDate = new Date();
			  $scope.newStartDate = new Date();
			  $scope.newAmount = 1;
			  

				
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
		      	'<label>Coupon title</label>'  +
		      	'<input ng-model="newTitle">'  +
		          '</md-input-container>' +
		          '<md-input-container class="md-block" flex-gt-sm>' +
		          '<label>Start date</label>'  +
		           '<md-datepicker ng-model="newStartDate"></md-datepicker>'  +
		          '</md-input-container>' +
		          '<md-input-container class="md-block" flex-gt-sm>' +
		         '<label>End Date</label>'  +
		        	'<md-datepicker ng-model="newEndDate"></md-datepicker>'  +
		          '</md-input-container>'  +
			      	'<md-input-container class="md-block" flex-gt-sm>' +
			      	'<label>Amount</label>'  +
			      	'<input ng-model="newAmount">'  +
			          '</md-input-container>' +
				      	'<md-input-container class="md-block" flex-gt-sm>' +
				      	'<md-select ng-model="newCouponType" placeholder="Coupon Type">' +
			              '<md-option ng-repeat="types in couponType" value="{{ types }}">' +
		                '{{ types }} </md-option>' +
		                '</md-select>' +
				          '</md-input-container>' +
					      	'<md-input-container class="md-block" flex-gt-sm>' +
					      	'<label>Message</label>'  +
					      	'<input ng-model="newMessage">'  +
					          '</md-input-container>' +
						      	'<md-input-container class="md-block" flex-gt-sm>' +
						      	'<label>Price</label>'  +
						      	'<input  type="number" ng-model="newPrice">'  +
						          '</md-input-container>' +
							      	'<md-input-container class="md-block" flex-gt-sm>' +
							      	'<label>Image: {{fileToUpload.name}}</label>'  +
							      	'  <md-dialog-actions>' +
							        '<label for="fileInput" class="md-button md-raised md-primary">Upload image</label>' +
							      '</label>' +
							      	'<input class="ng-hide" id="fileInput" type="file" accept="image/*" ngf-max-size="2MB"  ngf-select ng-model="fileToUpload" />' +
							      	'  </md-dialog-actions>' +
							      	'<img ngf-thumbnail="fileToUpload">' +
							      	'</md-input-container>' +
		          '  </md-dialog-content>' +
		          '  <md-dialog-actions>' +
		          '    <md-button ng-click="closeDialog()" class="md-primary">' +
		          '      Close Dialog' +
		          '    </md-button>' +
		          '    <md-button ng-click="upload(fileToUpload); createNewCoupon()" class="md-primary">' +
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
		     }
		      
		      $scope.createNewCoupon = function()
		      {
					 $mdDateLocale.formatDateLocal = function(date) {
						    var day = (date.getDate() >= 10) ? date.getDate() : "0" + date.getDate();
						    var monthIndex = date.getMonth();
						    var year = date.getFullYear();

						    return year + '-' + (monthIndex + 1) + '-' + day + 'T00:00';
					}; 
					
				  var strEndDate =  $mdDateLocale.formatDateLocal($scope.newEndDate);

				  var strStartDate = $mdDateLocale.formatDateLocal($scope.newStartDate);
		    	  
	    		  	var newCoupon = "{\"amount\" : \"" + $scope.newAmount + "\", \"endDate\" : \"" + strEndDate + "\", \"image\" : \"" +
	    		  	$scope.newImage + "\", \"message\" : \"" + $scope.newMessage + "\", \"price\" : \"" + $scope.newPrice +
	    		  	"\", \"startDate\" : \"" + strStartDate + "\", \"title\" : \"" + $scope.newTitle + "\", \"type\" : \"" + $scope.newCouponType + "\"}";
	    		  	

		    	  $scope.createCoupon(newCoupon).success(function(response){ 
		    		  if (response === "ok") 
		    		  {
		    			  $scope.couponList.push({startDate: $scope.newStartDate , endDate: Date(strEndDate), amount: $scope.newAmount, id: 115, image: $scope.newImage, title: $scope.newTitle, type: $scope.newCouponType, message : $scope.newMessage, price : $scope.newPrice, id: '-'});
		    			  $scope.openToast($scope.newTitle + " was created");
		    			  		    			  
		    			  console.log($scope.couponList);
		    			  
		    			   
			  		       $scope.dataMod.push({
					            dt: new Date($scope.newEndDate),
					            status: false		        	
					        })	
					        
		    			  }
		    		  else 
		    		  {
		    			  $scope.openToast(response)
		    			  }
		    		  },function(error){
		    			  $scope.openToast(error.data.message)
		    			  }
		    		  
		    	  );
		    	  $mdDialog.hide();
		      }
		  }
		  

		  /* Delete coupon dialog */
		  
		  $scope.removeDialog = function($event, Index, coupon, list) {
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
		          '<label>Would you like to delete the coupon?</label>' +
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

		       $scope.closeDialog = function() {
		         $mdDialog.hide();
		       }
		       
		       $scope.Remove = function()
		       {
		    		  	var couponToDel = "{\"amount\" : \"" + coupon.amount + "\", \"endDate\" : \"" + coupon.endDate + "\", \"id\" : \"" + coupon.id + "\", \"image\" : \"" +
		    		  	coupon.image + "\", \"message\" : \"" + coupon.message + "\", \"price\" : \"" + coupon.price +
		    		  	"\", \"startDate\" : \"" + coupon.startDate + "\", \"title\" : \"" + coupon.title + "\", \"type\" : \"" + coupon.type + "\"}";
		    		  	
		    	  var couponTitle = coupon.title; 
			      $scope.removeCoupon(couponToDel).then(function(response){
			    	  
			    	  if (response.data === "ok")
			    		  {
			    		  	list.splice(Index,1);
			    		  	$scope.dataMod.splice(Index,1);
			      			$scope.openToast(couponTitle + " was removed");
			    		  }
			    	  else
			    		  {
			    		  console.log(response.data.data);
			    		  $scope.openToast(response.data.data);
			    		  }
		       },function(error) {
		    	   console.log(response.data.data);
		    	   $scope.openToast(error.data.message);
		       });
		    	  $mdDialog.hide(); 		    	   
		       }

		   }		            
		 }
		  
}]);

app.config(function($mdDateLocaleProvider) {

	 $mdDateLocaleProvider.formatDate = function(date) {
		 
		 	if (date === undefined)
		 		{
		 			return null;
		 		}
		    var day = (date.getDate() >= 10) ? date.getDate() : "0" + date.getDate();
		    var monthIndex = date.getMonth();
		    var year = date.getFullYear();

		    return year + '-' + (monthIndex + 1) + '-' + day;
	    };
	})
	
	// allow angularjs to send for DELETE JSON format
app.config(function($httpProvider) {
	  /**
	   * make delete type json
	   */
	  $httpProvider.defaults.headers["delete"] = {
	    'Content-Type': 'application/json;charset=utf-8'
	  };
	})
	
