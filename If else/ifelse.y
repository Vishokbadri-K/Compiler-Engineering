%{
	#include<stdio.h>
	#include<stdlib.h>
	int yylex(void);
	int yyerror(const char *s);
%}
%token IF ELSE THEN ID NUM PL MI MUL LE GE LT GT EQEQ NEQ EQ
%left "+" "-"
%left '*' '/'
%left "<=" ">=" "<" ">" "==" "!="
%right "="
%%

st: Ifst {printf("\nInput  accepted\n"); exit(0);}
Ifst: 
	IF'('cond')' THEN STMT';'|
	IF'('cond')' THEN STMT';' ELSE STMT';';

cond: 
	ID LE ID|
	ID LT ID|
	ID GE ID|
	ID GT ID|
	ID EQEQ ID|
	ID NEQ ID;

;

STMT: 
	ID EQ ID|
	ID EQ NUM|
	ID EQ ID PL ID|
	ID EQ ID PL NUM|
	ID EQ ID MI ID|
	ID EQ ID MI NUM|
	ID EQ ID MUL ID|
	ID EQ ID MUL NUM
;

%%
void main()
{
	printf("\nEnter expression:\n");
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

