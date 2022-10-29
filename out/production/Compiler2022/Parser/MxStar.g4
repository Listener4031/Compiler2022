grammar MxStar;

program: definition*;
definition: globalvariableDefStatement | functionDef | classDef;

// construct types
basicType: Int | Bool | String;
typeName: basicType | Identifier; //int , string, MyClass
variableType: typeName ('[' ']')* ;    // int ,bool[][], MyClass[]
bracketExpression: '[' expression? ']';
newVar: typeName ('(' ')' | (bracketExpression*));   //  int(), MyClass[][1+2][]
functionType: Void | variableType;

variableDeclaration: Identifier ('=' expression)?;     // bool v = false, v2;
variableDef: variableType variableDeclaration (',' variableDeclaration)*;  // int a; string[] b,c;

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

atomExpr: This
        | Null
        | UnsignedInteger
        | BoolLiteral
        | StringObject
        | Identifier
        ;
expressionList:'(' (expression (',' expression)*)? ')';
expression: lambdaStatement                                                           #lambdaExpression
          | expression Dot expression                                                 #binaryExpression
          | expression expressionList                                                 #functionCallExpression
          | expression '[' expression ']'                                             #arrayExpression
          | <assoc=right> (SelfPlus | SelfMinus) expression                           #prefixExpression
          | expression (SelfPlus | SelfMinus)                                         #postfixExpression
          | <assoc=right> (Plus | Minus) expression                                   #unaryExpression
          | <assoc=right> Tilde expression                                            #unaryExpression
          | <assoc=right> Not expression                                              #unaryExpression
          | <assoc=right> New newVar                                                  #newExpression
          | expression op = (Multiply | Divide | Mod) expression                      #binaryExpression
          | expression op = (Plus | Minus) expression                                 #binaryExpression
          | expression op = (LeftShift | RightShift) expression                       #binaryExpression
          | expression op = (Less | LessEqual | Greater | GreaterEqual) expression    #binaryExpression
          | expression op = (Equal | NotEqual) expression                             #binaryExpression
          | expression op = And expression                                            #binaryExpression
          | expression op = Caret expression                                          #binaryExpression
          | expression op = Or expression                                             #binaryExpression
          | expression op = AND expression                                            #binaryExpression
          | expression op = OR expression                                             #binaryExpression
          | <assoc=right> expression Assign expression                                #binaryExpression
          | '(' expression ')'                                                        #parentheseExpression
          | atomExpr                                                                  #atomExpression
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
