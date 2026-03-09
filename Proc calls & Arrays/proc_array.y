%{
	#include<stdio.h>
	#include<stdlib.h>	
	int yylex(void);
	int yyerror(const char *s);
%}

%token NUM ID TYPE LP RP LS RS
%left "+" "-" "*" "/"
%right "="
%%

s: var {printf("Accepted\n");exit(0);};

var: 	 TYPE ID LP RP ST
	|TYPE ID LP TYPE ID LS RS RP ST
	|TYPE ID LP TYPE ID LS NUM RS RP ST;

ST: 	 '{' '}'
	|'{' ST1 '}';

ST1: 	 ID '=' ID '+' ID C
	|ID '=' ID '-' ID C
	|ID '=' ID '*' ID C
	|ID '=' ID '/' ID C;

C: 	 ';'
	|';' ID ';';
%%

int yyerror(const char *s) {}

int yywrap()
{
	return 1;
}

int main()
{
	printf("Enter the expression:\n");
	if(yyparse() == 0)
		printf("Success");
	return 0;
}

