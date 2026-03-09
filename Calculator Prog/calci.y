%{
	#include<stdio.h>
	#include<stdlib.h>
	int yylex(void);
	int yyerror(const char *s);
%}
%token NUM
%left '+' '-'
%right '*' '/'
%%
start: exp {printf("%d\n",$$);}
exp:exp'+'exp {$$=$1+$3;}
|exp'-'exp {$$=$1-$3;}
|exp'*'exp {$$=$1*$3;}
|exp'/'exp 
{
	if($3==0)
		yyerror("error");
	else
	{
		$$=$1/$3;
	}
}
|'('exp')' {$$=$2;}
|NUM {$$=$1;}
;
%%

int main()
{
	printf("Enter the Expression in terms of integers\n");
	if(yyparse()==0)
	printf("Success\n");
	return 0;
}

int yywrap(void)
{
	return 0;
}
int yyerror(const char *s)
{
	printf("error\n");
}
