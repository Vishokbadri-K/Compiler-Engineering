%{
	#include "y.tab.h"
	#include <stdio.h>
%}
%%
"if" {return IF;}
"then" {return THEN;}
[a-zA-Z]+ {return ID;}
"+" {return PL;}
"-" {return MI;}
"<=" {return LE;}
">=" {return GE;}
"<" {return LT;}
">" {return GT;}
"=" {return EQ;}
" " ;
[\t\n]+ {return 0;}
. {return yytext[0];}
%%
