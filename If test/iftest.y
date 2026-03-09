%{
	#include<stdio.h>
	#include<stdlib.h>
	int yylex(void);
	int yyerror(const char *s);
%}
%token IF THEN ID PL MI LE GE LT GT EQ
%left "-" "+"
%right "<=" "<" "="
%%
st: Ifst {printf("Success\n"); exit(0);}
Ifst: IF'('cond')' THEN STMT;
cond: ID LE ID
| ID LT ID
;
STMT: ID EQ ID PL ID
| ID EQ ID MI ID
;
%%
void main()
{
	yyparse();
}
int yywrap()
{
	return 0;
}
int yyerror(const char *s)
{
	printf("error\n");
}
