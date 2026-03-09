%{
	#include<stdio.h>
	#include<stdlib.h>
	int yylex(void);
	int yyerror(const char *s);
%}
%token FOR WHILE DO ID NUM OP LE GE LT GT EQ NE
%left "-" "+" "*" "/"
%left "<="  ">=" "<" ">"
%right "!=" "="
%%
st: lpst {printf("Success");exit(0);};
lpst: FOR'('base';'cond';'iter')' '{'CS'}' |
		WHILE'('cond')''{'CS'}' |
		DO'{'CS'}'WHILE'('cond')'';'|
		;
ipst: lpst | ';';

base: ID EQ NUM | ID EQ ID ;
cond: ID LE ID |
			ID LT ID |
			ID GE ID |
			ID GT ID |
			ID EQ ID|
			ID NE ID |
			ID LE NUM |
			ID LT NUM |
			ID GE NUM |
			ID GT NUM |
			ID EQ NUM|
			ID NE NUM
			;
iter: ID OP OP;
STMT: ID EQ ID OP ID
				|ID EQ ID OP NUM
				;
CS:ipst
		|STMT';'iter';'
		|STMT';'iter';'ipst
		|STMT';'ipst
		|STMT';'CS
		|';'
;
%%

int main()
{
	printf("Enter the Looping expr\n");
	yyparse();
	return 0;
}

int yywrap(void)
{
	return 0;
}

int yyerror(const char *s)
{
	printf("error\n");
	return 0;
}

