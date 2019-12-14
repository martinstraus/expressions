# Expressions

This library implements a simple language that features:

* Primitive data types: numbers, strings.
* Arithmetic operations: addition, subtraction, division, multiplication, exponentiation.
* Boolean operations: and, or, not.
* Functions.

It is aimed at being embedded in an application.

## The language

This will evaluate to ```4```.

    def f(x) <- x+1;
    f(1+2)

This will evaluate to ```false```.

    def f(x, y) <- lenth(x) > y;
    f("abcde", 10)

## Components

The library provides:

* A parser: ```expressions.AntlrParser```.
* An evaluator: ```expressions.SimpleEvaluator```.

## How to use the ```expressions.evaluator.SimpleEvaluator```

    Map<String, Object> context = new HashMap<>();
    context.put("a", 1);
    context.put("b", 2);
    Map<String, Object>> result = new SimpleEvaluator("a+b",context);
    System.out.println(result.get("result"));



    

