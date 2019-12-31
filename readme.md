# Expressions

This library implements a simple language that features:

* Primitive data types: numbers, strings.
* Arithmetic operations: addition, subtraction, division, multiplication, exponentiation.
* Boolean operations: and, or, not.
* Functions.

It is aimed at being embedded in an application and run from Java code.

## The language

The language is very simple. It allows you to define functions, and an expression using those functions.
Each program is composed of a set of functions, and then the expression.

### Data types

* string: `"this is a string"`
* number: `100.23`
* date: `date("2019-01-01")`
* set: `{1,2,3,4}`
* map: `["a"<-1, "b"<-2]`

### Operators

* boolean operators: `and`, `or`, `not`.
* comparation operators: `>`, `<`, `=`.
* arithmentic operators: `^` (exponentiation) `/`, `*`, `+`, `-`.
* collection operators: `in`.

### Values

Values are immutable. You can define them in the program, or pass them as parameters. Values
defined in the program cannot be referenced from any function; there's no such thing as global value.
You define a value with like this:

    def aText <- "abc";
    def aNumber <- 1234;
    def aDate <- date('2019-01-01')
    def aSet <- {1,2,3}
    def aMap <- ["a"<-1, "b"<-2]

### Defining functions

A function has a name, an optional list of parameters, statements, and a
final return expression. If your function is simple, and has no statements, you can use this simplified form:
 
    def f(x) <- x+1;
    
If you need to define values, you must use the extended form, using braces:

    def g(x,y) <- {
        def z = x+y;
        x+1
    }

### Expressions

You can use operators, values, and function calls to create expressions. Here are some examples:

* `a = b` evaluates to `true` or `false`.
* `3 + 10 / 2` evaluates to `8` and is equivalent to `3+(10/2)`.
* `def f(x) <- x+1; f(2+2)` evaluates to `5`

### Standard functions

#### Numeric functions

* `random()`: returns a random number.

#### Date functions

* `date(value)`: creates a date. The format is the always sensible `yyyy-mm-dd`. Example: `date("2019-12-31")`.
* `period(amount, unit)`: creates a time period. Unit can be `days`, `months`, or `years`. Example: `period(1, months)`.
* `add(date, period)`: adds a period to a date. Example: `add(date("2019-12-31"), period(1, month))`.
* `subtract(date, period)`: subtracts a period to a date. Example: `subtract(date("2019-12-31"), period(1, month))`.

#### String functions

* `length(value)`: returns the length of a string.
* `lowercase(value)`: converts a string to lowecase.
* `uppercase(value)`: converts a string to uppercase.
* `matches(value, regexp)`: determines if a string matches a regular expression.
* `split(value, regexp)`: splits a string with a regular expression.

## Usage

The library provides:

* A parser: ```expressions.AntlrParser```.
* An evaluator: ```expressions.SimpleEvaluator```.

## How to use the ```expressions.evaluator.SimpleEvaluator```

    Map<String, Object> context = new HashMap<>();
    context.put("a", 1);
    context.put("b", 2);
    Map<String, Object>> result = new SimpleEvaluator("a+b",context);
    System.out.println(result.get("result"));

