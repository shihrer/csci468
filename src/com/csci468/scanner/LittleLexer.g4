lexer grammar LittleLexer;

KEYWORD
    : 'PROGRAM'
    | 'BEGIN'
    | 'END'
    | 'FUNCTION'
    | 'READ'
    | 'WRITE'
    | 'IF'
    | 'ELSE'
    | 'ENDIF'
    | 'WHILE'
    | 'ENDWHILE'
    | 'CONTINUE'
    | 'BREAK'
    | 'RETURN'
    | 'INT'
    | 'VOID'
    | 'STRING'
    | 'FLOAT'
    ;

COMMENT
    : '--'.*?
    ;

OPERATOR
    : ':='
    | '+'
    | '-'
    | '*'
    | '/'
    | '='
    | '!='
    | '<'
    | '>'
    | '('
    | ')'
    | ';'
    | ','
    | '<='
    | '>='
    ;

INTLITERAL
    : [0-9]+
    ;
FLOATLITERAL
    : [0-9]*.[0-9]+
    ;
STRINGLITERAL
    : '"'~('"')*'"'
    ;

/* Identifiers */
IDENTIFIER
    : NAME
    ;

NAME
    : [a-zA-Z][a-zA-Z0-9]*
    ;

WS
    : [ \t\r\n] -> skip
    ;