grammar Micro;

/* Program */
program             : PROGRAM ID BEGIN body END;

/* Program Body */
body                : decl funcDeclarations;
decl                : stringDecl decl |
                      varDecl decl |;

/* Global String Declaration */
stringDecl          : STRING ID ASSIGNOP STRINGLITERAL ';';

/* Variable Declaration */
varDecl             : varType idList ';';
varType             : FLOAT |
                      INT;
anyType             : varType |
                      VOID;
idList              : ID idTail;
idTail              : ',' ID idTail |;

/* Function Paramater List */
paramDeclList       : paramDecl paramDeclTail |;
paramDecl           : varType ID;
paramDeclTail       : ',' paramDecl paramDeclTail |;

/* Function Declarations */
funcDeclarations    : funcDecl funcDeclarations |;
funcDecl            : FUNCTION anyType ID '(' paramDeclList ')' BEGIN funcBody END;
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
assignExpr          : ID ASSIGNOP expr;
readStmt            : READ '(' idList ')' ';';
writeStmt           : WRITE '(' idList ')' ';';
returnStmt          : RETURN expr ';';

/* Expressions */
expr                : exprPrefix factor;
exprPrefix          : exprPrefix factor ADDOP # ADDOP | # emptyExprPrefix;
factor              : factorPrefix postfixExpr;
factorPrefix        : factorPrefix postfixExpr MULOP # MULOP | # emptyFactorPrefix;
postfixExpr         : primary |
                      callExpr;
callExpr            : ID '(' exprList ')';
exprList            : expr exprListTail |;
exprListTail        : ',' expr exprListTail |;
primary             : '(' expr ')'  # paranths|
                      ID            # primaryID|
                      INTLITERAL    # primaryINT|
                      FLOATLITERAL  # primaryFLOAT;

/* Complex Statements and Condition */
ifStmt              : IF '(' cond ')' decl stmtList elsePart ENDIF;
elsePart            : ELSE decl stmtList # else | # emptyElse;
cond                : expr COMPOP expr;

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
ASSIGNOP : ':=';
COMPOP              : '<' | '>' | '=' | '!=' | '<=' | '>=';
ADDOP               : '+' | '-' ;
MULOP               : '*' | '/' ;
OPERATOR
    : '('
    | ')'
    | ';'
    | ','
    ;

STRINGLITERAL
    : '"'~('"')*'"'
    ;

ID
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