angular.module('myComponent', []) 
 this.headers = ['Id','Customer name','Password']; 

this.header = "<table>";
for (i = 0; i < headers.length; i++) {
    this.header = this.header + "<th>" + headers[i] + "</th>";
}

this.header = this.header + "</table>";
    
app.directive("trTable", function() {
    return {
        restrict : "AE",
        link: function(scope, element, attrs)
        {
            console.log(attrs);
        },
        template : "<p></p>"
    };
});
