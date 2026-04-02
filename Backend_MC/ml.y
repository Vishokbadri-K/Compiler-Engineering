%{
	#include<stdio.h>
	#include<ctype.h>
	#include<string.h>
	FILE *fout;
	int yylex();
%}

%union
{
	char var[20];
}

%token<var> NAME 
%token PLUS EQUAL MULT DIVI SUBT
%type<var> exp

%right EQUAL
%left PLUS SUBT
%left MULT DIVI
%%
input: 	input line
		|/*empty*/
;

line:  NAME EQUAL exp '\n'{fprintf(fout, "MOV %s,AX\n",$1);}
      |NAME EQUAL exp{fprintf(fout, "MOV %s,AX\n",$1);}
;

exp:  NAME PLUS NAME {fprintf(fout,"MOV AX,%s\nADD AX,%s\n",$1,$3);strcpy($$,$1);}
     |NAME SUBT NAME {fprintf(fout,"MOV AX,%s\nSUB AX,%s\n",$1,$3);strcpy($$,$1);}
     |NAME MULT NAME {fprintf(fout,"MOV AX,%s\nMULT AX,%s\n",$1,$3);strcpy($$,$1);}
     |NAME DIVI NAME {fprintf(fout,"MOV AX,%s\nDIV AX,%s\n",$1,$3);strcpy($$,$1);}
     |NAME {strcpy($$,$1);}
;

%%
extern int yylineno;
int yyerror(char *s)
{
	printf("\nCheck output.txt file\n");
	return 0;
}

extern FILE *yyin;
int main()
{
	FILE *fin;
	fin=fopen("input.txt","r");
	fout=fopen("output.txt","w");
	yyin=fin;
	yyparse();
	fclose(fin);
	fclose(fout);
	return 0;
}
