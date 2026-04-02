%{
	#include "y.tab.h"
	#include <stdio.h>
	#include <stdlib.h>
%}

%%
"if" {return IF;}
"else" {return ELSE;}
"==" {return EQ;}
"(" {return LP;}
")" {return RP;}
";" {return SEMI;}

[0-9]+ {yylval.num = atoi(yytext); return NUM; }
[a-zA-Z]+ {yylval.str = strdup(yytext); return ID; }

[ \t\n]+ ;

. {return yytext[0];}
%%

int yywrap() 
{
    return 1;
}
