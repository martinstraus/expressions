grammar Function;
file : expression* EOF;

expression
   :  prefix=(PLUS | MINUS)? atom # Unary
   |  expression POW expression # Power
   |  expression (TIMES | DIV)  expression # TimesOrDivision
   |  expression (PLUS | MINUS) expression # PlusOrMinus
   |  expression (EQ | GT | LT) expression # BooleanExpression
   |  LPAREN expression RPAREN # ParenthesisExpression  
   ;

atom
   : variable
   | literal
   ;

variable
   : VARIABLE
   ;

literal
   : NUMBER
   ;

VARIABLE
   : VALID_ID_START VALID_ID_CHAR*
   ;


fragment VALID_ID_START
   : ('a' .. 'z') | ('A' .. 'Z') | '_'
   ;


fragment VALID_ID_CHAR
   : VALID_ID_START | ('0' .. '9')
   ;

NUMBER
   : ('0' .. '9')+ ('.' ('0' .. '9')+)?
   ;

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


WS
   : [ \r\n\t] + -> skip
   ;