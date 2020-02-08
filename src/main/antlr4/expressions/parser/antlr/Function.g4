grammar Function;
@header {
import  expressions.evaluator.date.Unit;
import  expressions.evaluator.date.Units;
}

program : function* statements+=defineValue* expression EOF;

function
   : DEF name=IDENTIFIER
       LPAREN parameters+=IDENTIFIER (COMMA parameters+=IDENTIFIER)* RPAREN ASSIGN
       (result=expression SEMICOLON  | LBRACE (statements+=defineValue)* result=expression RBRACE)
   ;

expression
   :  NOT notValue=expression # UnaryOperation
   |  left=expression (EQ | GT | LT) right=expression # BinaryOperation
   |  left=expression (AND | OR) right=expression # BinaryOperation
   |  left=expression POW right=expression # BinaryOperation
   |  left=expression (TIMES | DIV)  right=expression # BinaryOperation
   |  left=expression (PLUS | MINUS) right=expression # BinaryOperation
   |  value=expression LBRACKET key=expression RBRACKET #IndexedReference
   |  value=expression (POINT properties+=IDENTIFIER)+ #PropertyReference
   |  prefix=(PLUS | MINUS)? atomValue=atom # UnaryOperation
   |  LPAREN nestedValue=expression RPAREN # NestedExpression
   ;

atom
   locals [
        boolean booleanValue=false;
   ]
   : functionName=IDENTIFIER LPAREN parameters+=expression (COMMA parameters+=expression)* RPAREN #FunctionCall
   | LBRACE (expression (COMMA expression)*)? RBRACE #Set
   | LBRACKET entries+=mapEntry (COMMA entries+=mapEntry)* RBRACKET #Map
   | LBRACKET (values+=expression (COMMA values+=expression)*)? RBRACKET #Array
   | name=IDENTIFIER #ValueLiteral
   | NUMBER #NumberLiteral
   | STRING #StringLiteral
   | (TRUE {$booleanValue=true;} | FALSE {$booleanValue=false;}) #BooleanLiteral
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

defineValue: DEF name=IDENTIFIER ASSIGN expression;

TRUE: 'true';
FALSE: 'false';
DEF: 'def';
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