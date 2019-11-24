grammar Function;
file : expression* EOF;

expression
   :  expression (EQ | GT | LT) expression # BooleanExpression
   |  expression  POW expression # Power
   |  expression  (TIMES | DIV)  expression # TimesOrDivision
   |  expression  (PLUS | MINUS) expression # PlusOrMinus
   |  LPAREN expression RPAREN # ParenthesisExpression
   |  (PLUS | MINUS)* atom # Unary
   ;

atom
   : variable
   ;

variable
   : VARIABLE
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

fragment NUMBER
   : ('0' .. '9') + ('.' ('0' .. '9') +)?
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