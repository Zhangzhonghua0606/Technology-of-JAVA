alert "hello coffeescript";

num = 5

add = (a, b) ->
  a + b

method =
  root: root
  square: square
  cube : (x) -> x + 1


fill = (container, liquid = "coffee") ->
  "Filling the #{container} with #{liquid}..."

fill2 = (container, liquid) ->
  "Filling the #{container} with #{liquid}..."

singers =
  jagger: "Rock"
  Elvis: "Roll"

singers2 = {Jagger: "Rock", Elvis: "Roll"}

$('div').attr class:'active'

outer = 1

changeNumbers = ->
  inner = -1
  outer = 10

date = if friday then sue else jill

if abc and def
  showDiff()
else
  showSame()

temp = ok if abc and def

nums = [1..10]

countdown = (num for num in [10..1] by 2)

grade = (student) ->
  if student.excellentWord
    "A"
  else if student.okayStuff
    if student.tryHard then "B" else "B-"
  else
    "c"

@property = 3

