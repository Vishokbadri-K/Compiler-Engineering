%{ 
	#include<stdio.h>
%}

%%
"int"|"float"|"long"|"for"|"if"|"else" {printf("\nKeyword");}
[a-zA-Z]([a-zA-Z])* {printf("\nIdentifier");}
"+"|"-"|"*"|"/"|"="  {printf("\nOperator");}
[0-9]+ {printf("\nNumber");}
";"|":"|"?"|"("|")"  {printf("\nDelimiter");}
\n\t ;
. ;
%%

int main()
{
	printf("Enter expression:\n");
	yylex();
	return 0;
}

int yywrap()
{}






