package maco.habit_backend;

public class Lab2LFTC {
    private static final String HW = """
            %{
            #include <stdio.h>
            #include <string.h>
            #include <stdlib.h>
            
            int lineno = 1;           // Track line numbers for error reporting
            int pos = 1;              // Track position within the line
            
            typedef struct {
                char identifier[32];
                int type;             // Custom type identifier
            } SymbolTableEntry;
            
            #define MAX_SYMBOLS 256
            
            SymbolTableEntry symbolTable[MAX_SYMBOLS];  // Symbol Table (ST)
            int symbolTableIndex = 0;
            
            typedef struct {
                int token_id;
                int symbol_table_index;
            } ProgramInternalFormEntry;
            
            ProgramInternalFormEntry programInternalForm[MAX_SYMBOLS]; // PIF
            int pifIndex = 0;
            
            // Function to insert identifier into the Symbol Table
            int insertSymbolTable(const char *identifier, int type) {
                for (int i = 0; i < symbolTableIndex; i++) {
                    if (strcmp(symbolTable[i].identifier, identifier) == 0) {
                        return i; // Return existing index if found
                    }
                }
                if (symbolTableIndex < MAX_SYMBOLS) {
                    strcpy(symbolTable[symbolTableIndex].identifier, identifier);
                    symbolTable[symbolTableIndex].type = type;
                    return symbolTableIndex++;  // Return new index
                } else {
                    fprintf(stderr, "Symbol table overflow\\n");
                    exit(1);
                }
            }
            
            // Function to add to Program Internal Form
            void addToPIF(int token_id, int st_index) {
                if (pifIndex < MAX_SYMBOLS) {
                    programInternalForm[pifIndex].token_id = token_id;
                    programInternalForm[pifIndex].symbol_table_index = st_index;
                    pifIndex++;
                } else {
                    fprintf(stderr, "PIF overflow\\n");
                    exit(1);
                }
            }
            
            %}
            
            %option noyywrap
            
            %%
            "int"               { addToPIF(1, -1); }       // Data type
            "boolean"           { addToPIF(2, -1); }
            "float"             { addToPIF(3, -1); }
            "String"            { addToPIF(4, -1); }
            "class"             { addToPIF(5, -1); }
            "if"                { addToPIF(6, -1); }
            "else"              { addToPIF(7, -1); }
            "while"             { addToPIF(8, -1); }
            "for"               { addToPIF(9, -1); }
            "public"            { addToPIF(10, -1); }
            "static"            { addToPIF(11, -1); }
            "void"              { addToPIF(12, -1); }
            "final"             { addToPIF(13, -1); }
            "System\\.out\\.println" { addToPIF(14, -1); }
            
            [0-9]+              { int index = insertSymbolTable(yytext, 20); addToPIF(20, index); }  // Integers
            \\"[^\\"]*\\"          { int index = insertSymbolTable(yytext, 21); addToPIF(21, index); }  // String literals
            [a-zA-Z_][a-zA-Z0-9_]* { int index = insertSymbolTable(yytext, 22); addToPIF(22, index); } // Identifiers
            
            "+"                 { addToPIF(30, -1); }   // Operators
            "-"                 { addToPIF(31, -1); }
            "*"                 { addToPIF(32, -1); }
            "/"                 { addToPIF(33, -1); }
            "=="                { addToPIF(34, -1); }
            "!="                { addToPIF(35, -1); }
            "<="                { addToPIF(36, -1); }
            ">="                { addToPIF(37, -1); }
            "<"                 { addToPIF(38, -1); }
            ">"                 { addToPIF(39, -1); }
            
            "="                 { addToPIF(40, -1); }   // Assignment
            ";"                 { addToPIF(41, -1); }   // Delimiters
            ","                 { addToPIF(42, -1); }
            "("                 { addToPIF(43, -1); }
            ")"                 { addToPIF(44, -1); }
            "{"                 { addToPIF(45, -1); }
            "}"                 { addToPIF(46, -1); }
            
            [\\n]                { lineno++; pos = 1; }  // Track new lines for error reporting
            [ \\t]               ;                       // Ignore whitespace
            .                   { printf("Lexical Error at line %d, position %d: %s\\n", lineno, pos, yytext); }
            
            %%
            
            int main(int argc, char *argv[]) {
                if (argc > 1) {
                    FILE *input_file = fopen(argv[1], "r");
                    if (!input_file) {
                        fprintf(stderr, "Cannot open file %s\\n", argv[1]);
                        return 1;
                    }
                    yyin = input_file;
                }
            
                yylex();
            
                // Print Symbol Table (ST)
                printf("Symbol Table:\\n");
                for (int i = 0; i < symbolTableIndex; i++) {
                    printf("%d: %s, Type: %d\\n", i, symbolTable[i].identifier, symbolTable[i].type);
                }
            
                // Print Program Internal Form (PIF)
                printf("\\nProgram Internal Form:\\n");
                for (int i = 0; i < pifIndex; i++) {
                    printf("Token ID: %d, ST Index: %d\\n", programInternalForm[i].token_id, programInternalForm[i].symbol_table_index);
                }
            
                return 0;
            }
            
            """;
}
