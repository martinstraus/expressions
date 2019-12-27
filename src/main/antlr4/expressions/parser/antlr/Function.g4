grammar Function;
@header {
import  expressions.evaluator.date.Unit;
import  expressions.evaluator.date.Units;
}
file : function* expression EOF;

function
   : DEF name=VARIABLE LPAREN parameters+=VARIABLE (COMMA parameters+=VARIABLE)* RPAREN ASSIGN expression SEMICOLON;

expression
   :  NOT expression # Not
   |  expression (EQ | GT | LT) expression # BooleanExpression
   |  expression (AND | OR) expression # AndOr
   |  expression IN set=expression # In 
   |  expression POW expression # Power
   |  expression (TIMES | DIV)  expression # TimesOrDivision
   |  expression (PLUS | MINUS) expression # PlusOrMinus
   |  LBRACKET (expression (COMMA expression)*)? RBRACKET # Set
   |  functionName=VARIABLE LPAREN parameters+=expression (COMMA parameters+=expression)* RPAREN # FunctionCall
   |  prefix=(PLUS | MINUS)? atom # Unary
   |  LPAREN expression RPAREN # ParenthesisExpression
   ;

DEF: 'def';
FUNCTION: 'function';

ASSIGN: '<-';

SEMICOLON: ';';

IN: 'in';

NOT: 'not';

AND: 'and';

OR: 'or';

DAYS: 'days';

MONTHS: 'months';

YEARS: 'years';

atom
   : value
   | literal
   ;

value
   : VARIABLE
   ;

literal
   : NUMBER
   | STRING 
   | dateUnit
   ;

dateUnit
    locals [
        Unit unit=null;
    ]
    : DAYS   {$unit=Units.DAYS;} 
    | MONTHS {$unit=Units.MONTHS;} 
    | YEARS  {$unit=Units.YEARS;}
    ;

fragment VALID_ID_START
   : ('a' .. 'z') | ('A' .. 'Z') | '_'
   ;


fragment VALID_ID_CHAR
   : VALID_ID_START | ('0' .. '9')
   ;

VARIABLE
   : VALID_ID_START VALID_ID_CHAR*
   ;

NUMBER
   : ('0' .. '9')+ ('.' ('0' .. '9')+)?
   ;

STRING: '"' (.|ESC)*? '"';
fragment ESC: '\\"' | '\\\\';

fragment UNSIGNED_INTEGER
   : ('0' .. '9')+
   ;

fragment E
   : 'E' | 'e'
   ;


fragment SIGN
   : ('+' | '-')
   ;


LPAREN
   : '('
   ;


RPAREN
   : ')'
   ;


PLUS
   : '+'
   ;


MINUS
   : '-'
   ;


TIMES
   : '*'
   ;


DIV
   : '/'
   ;


GT
   : '>'
   ;


LT
   : '<'
   ;


EQ
   : '='
   ;


POINT
   : '.'
   ;


POW
   : '^'
   ;

LBRACKET
   : '['
   ;

RBRACKET
   : ']'
   ;

COMMA
   : ','
   ;

WS
   : [ \r\n\t] + -> skip
   ;