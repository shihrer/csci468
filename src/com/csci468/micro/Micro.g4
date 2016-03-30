grammar Micro;

/* Program */
program             : PROGRAM id BEGIN body END;
id                  : IDENTIFIER;

/* Program Body */
body                : decl funcDeclarations;
decl                : stringDecl decl |
                      varDecl decl |;

/* Global String Declaration */
stringDecl          : STRING id ':=' str ';';
str                 : STRINGLITERAL;

/* Variable Declaration */
varDecl             : varType idList ';';
varType             : FLOAT |
                      INT;
anyType             : varType |
                      VOID;
idList              : id idTail;
idTail              : ',' id idTail |;

/* Function Paramater List */
paramDeclList       : paramDecl paramDeclTail |;
paramDecl           : varType id;
paramDeclTail       : ',' paramDecl paramDeclTail |;

/* Function Declarations */
funcDeclarations    : funcDecl funcDeclarations |;
funcDecl            : FUNCTION anyType id '(' paramDeclList ')' BEGIN funcBody END;
funcBody            : decl stmtList;

/* Statement List */
stmtList            : stmt stmtList |
                    ;
stmt                : baseStmt  |
                      ifStmt    |
                      whileStmt
                    ;
baseStmt            : assignStmt    |
                      readStmt      |
                      writeStmt     |
                      returnStmt
                    ;

/* Basic Statements */
assignStmt          : assignExpr ';';
assignExpr          : id ':=' expr;
readStmt            : READ '(' idList ')' ';';
writeStmt           : WRITE '(' idList ')' ';';
returnStmt          : RETURN expr ';';

/* Expressions */
expr                : exprPrefix factor;
exprPrefix          : exprPrefix factor addop |;
factor              : factorPrefix postfixExpr;
factorPrefix        : factorPrefix postfixExpr mulop |;
postfixExpr         : primary |
                      callExpr;
callExpr            : id '(' exprList ')';
exprList            : expr exprListTail |;
exprListTail        : ',' expr exprListTail |;
primary             : '(' expr ')'  |
                      id            |
                      INTLITERAL    |
                      FLOATLITERAL;
addop               : '+' | '-' ;
mulop               : '*' | '/' ;

/* Complex Statements and Condition */
ifStmt              : IF '(' cond ')' decl stmtList elsePart ENDIF;
elsePart            : ELSE decl stmtList |;
cond                : expr compop expr;
compop              : '<' | '>' | '=' | '!=' | '<=' | '>=';

/* While statements */
whileStmt           : WHILE '(' cond ')' decl stmtList ENDWHILE;


/* Lexer Grammer */
/* Keywords */
PROGRAM     : 'PROGRAM' ;
BEGIN       : 'BEGIN'   ;
END         : 'END'     ;
FUNCTION    : 'FUNCTION';
READ        : 'READ'    ;
WRITE       : 'WRITE'   ;
IF          : 'IF'      ;
ELSE        : 'ELSE'    ;
ENDIF       : 'ENDIF'   ;
WHILE       : 'WHILE'   ;
ENDWHILE    : 'ENDWHILE';
CONTINUE    : 'CONTINUE';
BREAK       : 'BREAK'   ;
RETURN      : 'RETURN'  ;
INT         : 'INT'     ;
VOID        : 'VOID'    ;
STRING      : 'STRING'  ;
FLOAT       : 'FLOAT'   ;

/*Doesn't really do anything...*/
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

STRINGLITERAL
    : '"'~('"')*'"'
    ;

IDENTIFIER
    : [a-zA-Z][a-zA-Z0-9]*
    ;

INTLITERAL
    : DIGIT+
    ;

FLOATLITERAL
    : DIGIT*'.'DIGIT+
    ;

COMMENT
    : '--'.*?(WINEOL | UNIEOL) -> skip
    ;

WS
    : [ \t\n\r]+ -> skip
    ;

fragment WINEOL : ('\r\n');
fragment UNIEOL : ('\n');
fragment DIGIT  : [0-9];