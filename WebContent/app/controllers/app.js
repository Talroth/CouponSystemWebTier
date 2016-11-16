/**
 * 
 */

var app = angular.module('main',  ['ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
    .when("/admin", {
        templateUrl : "admin.html"
    })
    .when("/company", {
        templateUrl : "company.html"
    })
    .when("/customer", {
        templateUrl : "customer.html"
    });
});