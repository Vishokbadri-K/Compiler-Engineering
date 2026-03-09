%{
	#include "y.tab.h"
	#include <stdio.h>
%}
%%
"switch" {return SWITCH;}
"case" {return CASE;}
"default" {return DEFAULT;}
"break"	{return BREAK;}
[a-zA-Z][a-zA-z0-9]* {return ID;}
[0-9]+ {return NUM;}
"+" {return PL;}
"-" {return MI;}
"*" {return ML;}
"/" {return DI;}
"=" {return EQ;}
" " ;
[\t\n]+ ;
. {return yytext[0];}
%%
