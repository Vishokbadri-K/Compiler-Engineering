%{
	#include<stdio.h>
	#include<stdlib.h>
	int yylex(void);
	int yyerror(const char *s);
%}
%token SWITCH CASE DEFAULT BREAK ID NUM PL MI ML DI EQ
%left "-" "+" "*" "/"
%right "="
%%

st: SWST {printf("Input accepted successfully\n"); exit(0);};

SWST: SWITCH'('ID')' '{' CASTMT '}' ;

CASTMT: 
	CASE ID':' STMT';' BREAK';' CASTMT | 
	CASE NUM':' STMT';' BREAK';' CASTMT |
	DEFAULT':' STMT';' |
	' '
;

STMT:	
	ID EQ ID PL ID|
	ID EQ ID MI ID|
	ID EQ ID ML ID|
	ID EQ ID DI ID
;

%%

int main()
{
	printf("Enter the Expression\n");
	yyparse();
	return 0;
}

int yywrap(void)
{
	return 0;
}

int yyerror(const char *s)
{
	printf("Error\n");
	return 0;
}
