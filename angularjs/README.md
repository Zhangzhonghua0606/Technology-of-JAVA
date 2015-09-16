#AngularJs 学习笔记

## Foreach 方法
```js
angular.forEach(todoList.todos, function(todo) {
  count += todo.done ? 0 : 1;
});
```

## Json 数据格式
    ```json
    [
      {
        text:'learn angular',
        done:true
      }
      ,
      {
        text:'build an angular app',
        done:false
      }
    ]
    ```

## $watch $apply $digest

- Angular的事件类型会自动触发`$apply`方法，然后`$digest`逐个检查`$watch`所监视的
值是否发生改变

    ```js
    var stop = $scope.$watch(“expression”， function(newVal, oldVal){})
    // 执行时会返回一个函数, 当调用这个函数的时候会将watch所绑定的数据解绑。
    stop();
    ```

- 手动执行`$apply`方法：`$scope.$apply()`或`$scope.$apply(function(){})`

- 监听当一个对象或数据发生改变时执行额外的方法:

    ```js
    $scope.$watch("arg1"，function(newValue, oldValue){}, arg3)
    ```

    - arg1: 表达式或函数，接受对象类型

    - arg3: 这个参数为`bool`类型，当为`true`的时候，`$watch`将比较对象的值而不是对象
          的引用，默认为`false`.

- Angular internally creates a `$watch` for each `ng-*` directive in order to keep the data up to date

## Bindonce
- 用于在第一次加载数据时将数据绑定的model上并渲染页面，解绑`$watch`,之后将不再进行动态更新。
- 适合只加载一次数据，之后不再改变的数据。

## NgModel: NgModelController

- Execution suquence of NgModelController's method:

    ```js
    // when change $modelValue:
    $render -> $formatters -> $setViewValue -> $parsers
    // when change $viewValue:
    $setViewValue -> $parsers
    ```

- Refer to: https://docs.angularjs.org/api/ng/type/ngModel.NgModelController

## Different types of service

`provider`, `value`, `constant`, `factory`, `service`

`$povider.decorater`: 用于为已经存在的`service`(除`constant`处)扩展额外的功能。


In fact, internally a factory is a provider with only the `$get` function.
We can inject everything but providers on a factory and we can inject it everywhere except on the provider constructor and config functions.

The service works much the same as the factory one. The difference is simple: The factory receives a function that gets called when we create it and the service receives a constructor function where we do a new on it (actually internally is uses **Object.create** instead of **new**).

A constant can be injected everywhere and that includes provider constructor and config functions. That is why we use constant services to create default configuration for directives, because we can modify those configuration on our `config` functions.

`$provide` is what Angular uses internally to create all the services. We can use it to create new services if we want but also to decorate existing services. `$provide` has a method called decorator that allows us to do that. decorator receives the name of the service and a callback function that receives a `$delegate` parameter. That `$delegate` parameter is our original service instance.

*Note: The constant service cannot be decorated.*

- 使用`service`的方法  （Best Prictice），在声明service中返回一个对象

    ```js
    app.module('app',[]).service('MyService', function(){
       service = {};
       var private = "private"  // 这是私有的属性
       service.isPrivate = function(){
         return private == "private";
    };
       service.doThing = function(){};
       return service;
    })
    ```

  根据JavaScript的性质， 值传递和对象传递， `service中`返回一个对象，在传递(赋值)给controller时采用引用传递。这样便保证了数据操作的一致性。

    ```js
    app.module('app',[]).controller('MyCtrl', function($scope, MyService){
       $scope.doExtraThings = function(){
          // Do something before service method.
          MyService.doThings();
          // Do something after service method.
       }
       $scope.isPrivate = MyService.isPrivate; // 引用传递
    })
    ```

## $Complile

Compiles an HTML string or DOM into a template and produces a template function, which can then be used to *link scope and the template together*.

The compilation is a process of walking the DOM tree and matching DOM elements to directives.

- priority
    Directives with greater numerical priority are compiled first. Pre-link functions are also run in priority order, but post-link functions are run in reverse order. The order of directives with the same priority is undefined. The default priority is 0.

- link
    This property is used only if the `compile` property is not defined.

    The `link` function is responsible for registering DOM listeners as well as updating the DOM. It is executed after the template has been cloned. This is where most of the directive logic will be put.

- Transclusion Scopes

    When you call a `transclude` function it returns a DOM fragment that is pre-bound to a **transclusion scope**. This scope is special, in that it is a child of the directive's scope (and so gets destroyed when the directive's scope gets destroyed) but it inherits the properties of the scope from which it was taken.

    For example consider a directive that uses transclusion and isolated scope. The DOM hierarchy might look like this:
    ```html
    <div ng-app>
      <div isolate>
        <div transclusion>
        </div>
      </div>
    </div>
    ```

    The `$parent` scope hierarchy will look like this:

    ```shell
    * $rootScope
    * isolate
      * transclusion
    ```

    but the scopes will inherit prototypically from different scopes to their `$parent`.

    ```shell
    * $rootScope
      * transclusion
    * isolate
    ```

- $set(name, value)     name为规范化的名称 如：ngModel

- $attr(name, value)    name为非规范化的名称 如：ng-model

    ```js
    function linkingFn(scope, elm, attrs, ctrl) {
      // get the attribute value
      console.log(attrs.ngModel);

      // change the attribute
      attrs.$set('ngModel', 'new value');

      // observe changes to interpolated attribute
      attrs.$observe('ngModel', function(value) {
        console.log('ngModel has changed value to ' + value);
      });
    }
    ```

## $Injector

There are three ways in which the function can be annotated with the needed dependencies.

- **Argument names**

    The simplest form is to extract the dependencies from the arguments of the function. This is done by converting the function into a string using toString() method and extracting the argument names.

    ```js
    // Given
    function MyController($scope, $route) {
      // ...
    }

    // Then
    expect(injector.annotate(MyController)).toEqual(['$scope', '$route']);
    ```
    You can disallow this method by using strict injection mode.
    This method does not work with code minification / obfuscation. For this reason the following annotation strategies are supported.

- **The $inject property**

    If a function has an $inject property and its value is an array of strings, then the strings represent names of services to be injected into the function.

    ```js
    // Given
    var MyController = function(obfuscatedScope, obfuscatedRoute) {
      // ...
    }
    // Define function dependencies
    MyController['$inject'] = ['$scope', '$route'];

    // Then
    expect(injector.annotate(MyController)).toEqual(['$scope', '$route']);
    ```

- **The array notation**

    It is often desirable to inline Injected functions and that's when setting the `$inject` property is very inconvenient. In these situations using the array notation to specify the dependencies in a way that survives minification is a better choice:

    ```js
      // We wish to write this (not minification / obfuscation safe)
    injector.invoke(function($compile, $rootScope) {
      // ...
    });

    // We are forced to write break inlining
    var tmpFn = function(obfuscatedCompile, obfuscatedRootScope) {
      // ...
    };
    tmpFn.$inject = ['$compile', '$rootScope'];
    injector.invoke(tmpFn);

    // To better support inline function the inline annotation is supported
    injector.invoke(['$compile', '$rootScope', function(obfCompile, obfRootScope) {
      // ...
    }]);

    // Therefore
    expect(injector.annotate(
       ['$compile', '$rootScope', function(obfus_$compile, obfus_$rootScope) {}])
     ).toEqual(['$compile', '$rootScope']);
    ```
