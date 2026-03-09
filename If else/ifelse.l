%{
	#include "y.tab.h"
	#include <stdio.h>
%}
%%
"if" {return IF;}
"else" {return ELSE;}
"then" {return THEN;}
[a-zA-Z]+ {return ID;}
"+" {return PL;}
"-" {return MI;}
"*" {return MUL;}
"<=" {return LE;}
">=" {return GE;}
"<" {return LT;}
">" {return GT;}
"==" {return EQEQ;}
"!=" {return NEQ;}
"=" {return EQ;}
" " ;
[\t\n]+ {return 0;}
. {return yytext[0];}
%%

