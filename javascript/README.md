# JavaScript 学习笔记

## slice
`slice` is a method in JavaScript that “selects a part of an array, and returns the new array.” (W3CSchool). It can have two arguments : start-index(required), end-index.

**Array.prototype.slice.apply(arguments) or [].slice.apply(arguments)**

Ok, arguments, you know, it’s the implicit JS variable created when you invoke a function, containing the arguments of a function. You’re expecting this variable to be an Array,? Well, it’s not, it’s similar, but it’s still an object.
use `[].slice.apply(arguments)` converts arguments into an ARRAY
