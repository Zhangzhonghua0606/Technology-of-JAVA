'use strict'

var app = angular.module('app', []);
app
.controller('MyCtrl',function($scope) {
  $scope.labelName = "New Button";
  $scope.newElement = angular.element('<div>' + $scope.labelName + '</div>');
})

.directive('compileDirective', function ($compile)
{
  return {
    restrict: 'E',
    template: '<div>New compile template</div>',
    controller: 'MyCtrl',
    link: function (scope, elm, attrs)
    {
      var compileIt       = $compile(scope.newElement);
      var content       = compileIt(scope);
      elm.append(content);
    }
  }
})

.directive('pageDirective', function () {
  return {
    restrict: 'AE',
    template: '<div>Here is a new button</div>',
    scope: '=',
    controller: 'MyCtrl',
    compile: function(tElem, tAttrs) {

      console.log('compile it. This is a original compiled DOM.');
      debugger;
      return {
        pre: function preLink(scope, iElem, iAttrs) {
          console.log('pre');
          iElem.html('<div>Now a panel</div>');
          debugger;
        },
        post: function postLink(scope, iElem, iAttrs) {
          console.log('post');
          iElem.append(scope.newElement);
          debugger;
        }
      }
    }
  };
})

.directive('pageDirectiveTwo', function ()
{
  return {
    restrict: 'E',
    template: '<div>Here is a second button</div>',
    controller: 'MyCtrl',
    scope: '=',
    compile: function (tElem, tAttrs)
    {
      console.log('2 compile it. This is the original compiled DOM.');
      debugger;
      return {
        pre: function preLink (scope, iElement, iAttrs)
        {
          console.log('2 pre');
          debugger;
        },
        post: function postLink (scope, iElement, iAttrs)
        {
          console.log('2 post');
          iElement.append(scope.newElement);
          debugger;
        }
      }
    }
  }
})

.directive('pageDirectiveThree', function ()
{
  return {
    restrict: 'E',
    template: '<div>Here is a third button</div>',
    controller: 'MyCtrl',
    scope: '=',
    compile: function (tElem, tAttrs)
    {
      console.log('3 compile it. This is the original compiled DOM.');
      debugger;
      return {
        pre: function preLink (scope, iElement, iAttrs)
        {
          console.log('3 pre');
          debugger;
        },
        post: function postLink (scope, iElement, iAttrs)
        {
          console.log('3 post');
          debugger;
        }
      }
    }
  }
});
