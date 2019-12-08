grammar Function;
file : function? expression EOF;

function
   : DEF name=VARIABLE LPAREN parameters+=expression RPAREN ASSIGN expression SEMICOLON;

expression
   :  NOT expression # Not
   |  expression (EQ | GT | LT) expression # BooleanExpression
   |  expression (AND | OR) expression # AndOr
   |  expression IN set # In 
   |  expression POW expression # Power
   |  expression (TIMES | DIV)  expression # TimesOrDivision
   |  expression (PLUS | MINUS) expression # PlusOrMinus
   |  prefix=(PLUS | MINUS)? atom # Unary
   |  functionName=VARIABLE LPAREN parameters+=expression RPAREN # FunctionCall
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

atom
   : variable
   | literal
   ;

variable
   : VARIABLE
   ;

literal
   : NUMBER
   | STRING 
   ;

set
   : LBRACKET (expression (COMMA expression)*)? RBRACKET
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