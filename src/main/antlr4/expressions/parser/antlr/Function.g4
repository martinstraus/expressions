grammar Function;
@header {
import  expressions.evaluator.date.Unit;
import  expressions.evaluator.date.Units;
}
file : function* expression EOF;

function
   : DEF name=IDENTIFIER LPAREN parameters+=IDENTIFIER (COMMA parameters+=IDENTIFIER)* RPAREN ASSIGN expression SEMICOLON;

expression
   :  NOT expression # UnaryOperation
   |  left=expression (EQ | GT | LT) right=expression # BinaryOperation
   |  left=expression (AND | OR) right=expression # BinaryOperation
   |  left=expression IN right=expression # BinaryOperation
   |  left=expression POW right=expression # BinaryOperation
   |  left=expression (TIMES | DIV)  right=expression # BinaryOperation
   |  left=expression (PLUS | MINUS) right=expression # BinaryOperation
   |  value=expression LBRACKET key=expression RBRACKET #MapReference
   |  prefix=(PLUS | MINUS)? atom # UnaryOperation
   |  LPAREN expression RPAREN # NestedExpression
   ;

atom
   : functionName=IDENTIFIER LPAREN parameters+=expression (COMMA parameters+=expression)* RPAREN #FunctionCall
   | LBRACE (expression (COMMA expression)*)? RBRACE #Set
   | LBRACKET entries+=mapEntry (COMMA entries+=mapEntry)* RBRACKET #Map
   | name=IDENTIFIER #ValueLiteral
   | NUMBER #NumberLiteral
   | STRING #StringLiteral
   | dateUnit #DateLiteral
   ;

dateUnit
    locals [
        Unit unit=null;
    ]
    : DAYS   {$unit=Units.DAYS;} 
    | MONTHS {$unit=Units.MONTHS;} 
    | YEARS  {$unit=Units.YEARS;}
    ;

mapEntry: key=expression ASSIGN value=expression;

DEF: 'def';
FUNCTION: 'function';
IN: 'in';
NOT: 'not';
AND: 'and';
OR: 'or';
DAYS: 'days';
MONTHS: 'months';
YEARS: 'years';
IDENTIFIER: VALID_ID_START VALID_ID_CHAR*;
NUMBER: (DIGIT)+ (POINT (DIGIT)+)?;
STRING: '"' (.|ESC)*? '"';
ASSIGN: '<-';
SEMICOLON: ';';
LPAREN: '(';
RPAREN: ')';
PLUS: '+';
MINUS: '-';
TIMES: '*';
DIV: '/';
GT: '>';
LT: '<';
EQ: '=';
POINT: '.';
POW: '^';
LBRACKET: '[';
RBRACKET: ']';
LBRACE: '{';
RBRACE: '}';
COMMA: ',';
fragment DIGIT: '0' .. '9';
fragment VALID_ID_START: ('a' .. 'z') | ('A' .. 'Z') | '_';
fragment VALID_ID_CHAR: VALID_ID_START | DIGIT;
fragment ESC: '\\"' | '\\\\';
WS: [ \r\n\t] + -> skip;