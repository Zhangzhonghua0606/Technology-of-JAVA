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
   

## Different types of service

`provider`, `value`, `constant`, `factory`, `service`  

`$povider.decorater`: 用于为已经存在的`service`(除`constant`处)扩展额外的功能。


In fact, internally a `factory` is a `provider` with only the `$get` function.
We can inject everything but providers on a `factory` and we can inject it everywhere except on the provider constructor and config functions.

The `service` works much the same as the `factory` one. The difference is simple: The `factory` receives a function that gets called when we create it and the `service` receives a constructor function where we do a new on it (actually internally is uses `Object.create` instead of `new`).

A `constant` can be injected everywhere and that includes `provider` constructor and `config` functions. That is why we use `constant` services to create default configuration for directives, because we can modify those configuration on our `config` functions.

`$provide` is what Angular uses internally to create all the services. We can use it to create new services if we want but also to decorate existing services. `$provide` has a method called decorator that allows us to do that. decorator receives the name of the service and a callback function that receives a `$delegate` parameter. That `$delegate` parameter is our original `service`instance.

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
## NgModel: NgModelController

- Execution suquence of NgModelController's method:

    ```js
    // when change $modelValue:
    $render -> $formatters -> $setViewValue -> $parsers
    // when change $viewValue:
    $setViewValue -> $parsers
    ```

- Refer to: https://docs.angularjs.org/api/ng/type/ngModel.NgModelController
