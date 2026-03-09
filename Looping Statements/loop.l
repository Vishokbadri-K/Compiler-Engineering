%{
    #include"y.tab.h"
    #include<stdio.h>
%}
%%
"for"	{return FOR;}
"while"	{return WHILE;}
"do"	{return DO;}

[a-zA-Z][a-zA-z0-9]*	{return ID;}
[0-9]+		{return NUM;}

"+"|"-"|"*"|"/"	{return OP;}

"<="	{return LE;}
">="	{return GE;}
"<"	{return LT;}
">"	{return GT;}
"="	{return EQ;}
"!="	{return NE;}

" "	;
[\t\n]+ ;
.	{return yytext[0];}
%%
