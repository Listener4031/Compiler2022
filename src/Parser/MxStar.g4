grammar MxStar;

program: (globalvariableDefStatement | functionDef | classDef)*;

// construct types
basicType: Int | Bool | String;
typeName: basicType | Identifier; //int , string, MyClass
variableType: typeName ('[' ']')* ;    // int ,bool[][], MyClass[]
newVar: typeName ('(' ')' | ('[' expression? ']')*) ;  //  int(), MyClass[][1+2][]
functionType: Void | variableType;

//
variableDef: variableType variableDeclaration (',' variableDeclaration)*;  // int a; string[] b,c;
variableDeclaration: Identifier ('=' expression)?;     // bool v = false, v2;

parameter: variableType Identifier ;
functionParameterDef: ( parameter (',' parameter)* )?;
functionDef: functionType Identifier '(' functionParameterDef ')' statementBlock; //  MyClass[] func(int a, bool b){...}

classConstructor: Identifier '(' ')' statementBlock;
classDef: Class Identifier '{' (variableDefStatement | functionDef)* classConstructor? (variableDefStatement | functionDef)* '}' ';';

statementBlock: '{' statement* '}';

// if
ifStatement: If '(' expression ')' trueStatement=statement (Else falseStatement=statement)? ;

// for while
forInit: variableDef | expression;
forCondition: expression;
stepping: expression;  // for(int i = 1; i <= 10; i++)
forStatement: For '(' forInit? ';' forCondition? ';' stepping? ')' statement ;
whileStatement: While '(' expression ')' statement;
loopStatement: forStatement | whileStatement ;
breakStatement: Break ';';
continueStatement: Continue ';';
returnStatement: Return expression? ';';
controlStatement: breakStatement | continueStatement | returnStatement ;

statement: statementBlock
         | variableDefStatement
         | ifStatement
         | loopStatement
         | controlStatement
         | expression ';'
         | ';'
         ;

globalvariableDefStatement: variableDef ';';
variableDefStatement: variableDef ';';

lambdaStatement: '[&]' '(' functionParameterDef ')' '->' statementBlock expressionList;

atomExpression: This
              | Null
              | UnsignedInteger
              | BoolLiteral
              | StringObject
              | Identifier
              ;
expressionList:'(' (expression (',' expression)*)? ')';
expression: expression expressionList                                                 #listExpr
          | lambdaStatement                                                           #lambdaExpr
          | expression Dot expression                                                 #dotExpr
          | expression '[' expression ']'                                             #arrayExpr
          | <assoc=right> (SelfPlus | SelfMinus) expression                           #selfplusExpr
          | expression (SelfPlus | SelfMinus)                                         #plusselfExpr
          | <assoc=right> (Plus | Minus) expression                                   #minusExpr
          | <assoc=right> Tilde expression                                            #tidleExpr
          | <assoc=right> Not expression                                              #notExpr
          | <assoc=right> New newVar                                                  #newExpr
          | expression op = (Multiply | Divide | Mod) expression                      #mutiExpr
          | expression op = (Plus | Minus) expression                                 #plusminusExpr
          | expression op = (LeftShift | RightShift) expression                       #listExpr
          | expression op = (Less | LessEqual | Greater | GreaterEqual) expression    #compareExpr
          | expression op = (Equal | NotEqual) expression                             #equalExpr
          | expression op = And expression                                            #andExpr
          | expression op = Caret expression                                          #caretExpr
          | expression op = Or expression                                             #orExpr
          | expression op = AND expression                                            #andandExpr
          | expression op = OR expression                                             #ororExpr
          | <assoc=right> expression Assign expression                                #assignExpr
          | '(' expression ')'                                                        #bracketExpr
          | atomExpression                                                            #atomExpr
          ;

//关键字
This: 'this';
Int: 'int';
Bool: 'bool';
String: 'string';
Void: 'void';
Null: 'null';
fragment True: 'true';
fragment False: 'false';
If: 'if';
Else: 'else';
For: 'for';
While: 'while';
Break: 'break';
Continue: 'continue';
Return: 'return';
New: 'new';
Class: 'class';

//括号
LeftParenthese: '(';
RightParenthese: ')';
LeftBracket: '[';
RightBracket: ']';
LeftBrace: '{';
RightBrace: '}';

//compare
Less: '<';
LessEqual: '<=';
Greater: '>';
GreaterEqual: '>=';
Equal: '==';
NotEqual: '!=';

//operation
LeftShift: '<<';
RightShift: '>>';
Plus: '+';
SelfPlus: '++';
Minus: '-';
SelfMinus: '--';
Multiply: '*';
Divide: '/';
Mod: '%';
//logic
And: '&';
Or: '|';
Caret: '^';
Tilde: '~';
Not: '!';
AND: '&&';
OR: '||';

Question: '?';
Colon: ':';
Semicolon: ';';
Comma: ',';
Dot: '.';
Assign: '=';

UnsignedInteger: [1-9][0-9]*  // 19260817
               | '0'
               ;

BoolLiteral: True | False;

fragment EscapeCharacter: ["nr\\];
fragment PrintableCharacter: ~[\\\r\n\f"];
StringObject: '"' (' ' | PrintableCharacter | '\\' EscapeCharacter)* '"';

Identifier: [a-zA-Z][a-zA-Z0-9_]*; // defined by myself

BlankSpace: [ \t]+ -> skip;

LineFeed: ('\r' '\n'? | '\n') -> skip;

AnnotationBlock: '/*' .*? '*/' -> skip;

AnnotationLine: '//' ~[\r\n]* -> skip;
