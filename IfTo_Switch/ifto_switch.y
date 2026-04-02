%{
	#include<stdio.h>
	#include<stdlib.h>
	#include<string.h>

	void yyerror(const char *s);
	int yylex();
	char var[20];
%}

%union
{
	int num;
	char* str;
}

%token IF ELSE EQ LP RP SEMI
%token <num> NUM
%token <str> ID

%type <str> stmt cond else_part action
%%

stmt:
	IF LP cond RP action else_part
	{
		printf("\nConverted Switch-Case:\n");
		printf("switch(%s)\n{\n", var);
		printf("%s%sbreak;\n", $3, $5);
		printf("%s", $6);
		printf("}\n");
		exit(1);
	}
;

cond:
	ID EQ NUM
	{
		strcpy(var, $1);
		char *temp = malloc(100);
		sprintf(temp, "case %d: ", $3);
		$$ = temp;
	}
;

else_part:
	ELSE IF LP cond RP action else_part
	{
		char *temp = malloc(200);
		sprintf(temp, "%s%sbreak;\n%s", $4, $6, $7);
		$$ = temp;
	}
	| ELSE action
	{
		char *temp = malloc(100);
		sprintf(temp, "default: %s\n", $2);
		$$ = temp;
	}
;

action:
	ID SEMI
	{
		char *temp = malloc(50);
		sprintf(temp, "%s; ", $1);
		$$ = temp;
	}
;

%%

void yyerror(const char *s) 
{
    printf("Error: %s\n", s);
}

int main() 
{
    printf("Enter if-else statement:\n");
    yyparse();
    return 0;
}
