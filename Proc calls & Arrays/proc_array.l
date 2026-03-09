%{
	#include<stdio.h>
	#include "y.tab.h"
%}

%%
void|int|float|char {return TYPE;}
[0-9]* {return NUM;}
[a-zA-Z]([a-zA-Z0-9])* {return ID;}
"(" {return LP;}
")" {return RP;}
"[" {return LS;}
"]" {return RS;}

" " ;
[\t\n]+ ;
. {return yytext[0];}
%%
