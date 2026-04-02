%{
	#include<stdio.h>
	#include<stdlib.h>
	int yylex(void);
	int yyerror(char* str);
	char GenCode(char first,char second,char op);
	extern FILE *yyin;
	extern FILE *yyout;
	FILE *outfp;
%}

%union {char dval;}
%token <dval> NUM
%type <dval> E
%left '+' '-' 
%left '*' '/'

%%
S: E {printf("\nOutput written in output.txt file\n",$1);} ;

E: 	 E'+'E	{$$=GenCode($1,'+',$3);}
	|E'-'E	{$$=GenCode($1,'-',$3);}
	|E'*'E	{$$=GenCode($1,'*',$3);}
	|E'/'E	{$$=GenCode($1,'/',$3);}
	|'('E')'{$$=$2;}
	|NUM	{$$=$1;}
;
	 
%%
int yywrap(){return 0;}
int yyerror(char* str){printf("\n%s",str);}
int main(int argc, char *argv[])
{
	
	FILE *fp = fopen("input.txt", "r");
	if(fp == NULL)
	{
		printf("\nError: Cannot Open file '%s'\n",argv[1]);
		return 1;
	}
	printf("Reading from input.txt");
	outfp = fopen("output.txt","w");
	if(fp == NULL)
	{
		printf("\nError: Cannot Open file '%s'\n",argv[1]);
		return 1;
	}
	yyin = fp;
	yyparse();
	fclose(fp);
	fclose(outfp);
	return 0;
}
