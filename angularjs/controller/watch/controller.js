"use strict"

var app = angular.module('app', [])
app.controller('MyCtrl', function($scope) {
  $scope.name = "Foo";
  $scope.user = { name: "Fox" };
  $scope.updated = 0;
  $scope.foo = 0;
  $scope.bar = 0;

  $scope.$watch('user', function(newValue, oldValue) {
    if (newValue === oldValue) { return; }
    $scope.updated++;
  }, true); // true indicate that compare the value of the objects instead of the reference

  $scope.changeFoo = function() {
    $scope.name = "Bar";
  };
})

app.directive('clickable', function() {
  return {
    restrict: "A",
    scope: {
      foo: '=',
      bar: '='
    },
    template: '<ul style="background-color: lightblue"><li>{{foo}}</li><li>{{bar}}</li></ul>',
    link: function(scope, element, attrs) {
      element.bind('click', function() {
        scope.$apply(function() {
          scope.foo++;
          scope.bar++;
        });
      });
    }
  }
});
