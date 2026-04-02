%{
	#include<stdio.h>
	#include<string.h>
	#include "y.tab.h"
%}

%%
[a-z][a-z0-9]* {strcpy(yylval.var,yytext);return NAME;}
[+] {strcpy(yylval.var,yytext); return PLUS;}
[=] {strcpy(yylval.var,yytext); return EQUAL;}
[-] {strcpy(yylval.var,yytext); return SUBT;}
[*] {strcpy(yylval.var,yytext); return MULT;}
[/] {strcpy(yylval.var,yytext); return DIVI;}
[\t] ;
\n {yylineno++;return '\n';}
%%
int yywrap(){return 1;}
