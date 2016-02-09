lexer grammar MicroLexer;



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
    : DIGIT+
    ;
FLOATLITERAL
    : DIGIT*'.'DIGIT+
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

COMMENT
    : '--'.*?(WINEOL | UNIEOL) -> skip
    ;

WS
    : (' ' | '\t' | '\r' | '\n')+ -> skip
    ;

fragment WINEOL: ('\r\n');
fragment UNIEOL: ('\n');
fragment DIGIT: [0-9];