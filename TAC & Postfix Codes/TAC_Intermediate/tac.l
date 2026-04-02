%{
	#include<stdio.h>
	#include<stdlib.h>
	#include "y.tab.h"
	char ch = 'a';
	extern FILE *outfp;
%}

%%
[0-9]+ {yylval.dval = yytext[0]; return NUM;}
\n {return 0;}
. {return yytext[0];}
%%

char GenCode(char first,char second,char op)
{
	fprintf(outfp,"\n%c = %c%c%c",ch,first,second,op);
	return ch++;
}
