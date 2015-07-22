'use strict'

var app = angular.module('snail-app', []);
app
.controller('MainCtrl',function($scope) {
  $scope.color = "";
})

.directive('helloUpsnail', function () {
  return {
    restrict: 'AE',
    replace: true,
    scope: {},
    template: '<p style="background-color:{{color}}">Hello World</p>',
    link: function (scope, element, attrs) {
      element.bind('click', function () {
        element.css('background-color', 'white');
        scope.$apply(function () {
          scope.color = "white";
        });
      });

      element.bind('mouseover', function () {
        element.css('cursor', 'pointer');
      });
    }
  };
});
