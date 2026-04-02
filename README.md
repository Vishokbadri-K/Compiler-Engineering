# Compiler Engineering Programs Repository  

## 📌 Overview  

This repository contains a collection of **Compiler Engineering programs** that demonstrate fundamental concepts involved in the design and implementation of compilers.

The programs help illustrate important stages of a compiler such as **lexical analysis, syntax analysis, token recognition, parsing, and grammar analysis**. These implementations are useful for students studying compiler design and for understanding how programming languages are processed internally.

Some programs may use tools such as **Lex and Yacc**, which are primarily supported in **Linux/Ubuntu environments**.

---

## 🧠 Topics Covered  

The repository includes programs related to:

### 🔹 Backend Machine Code Generation
- Reads Input expression and computes Three Address Code (TAC)
- Generate x86 Machine code for the TAC

### 🔹 Calculator Program
- Simple expression evaluation  
- Arithmetic operations implementation  

### 🔹 First & Follow using Java
- Computation of **FIRST sets**  
- Computation of **FOLLOW sets**  
- Used in predictive parsing and grammar analysis  

### 🔹 If–Else
- Recognition or implementation of conditional constructs  
- Demonstration of control structure parsing concepts  

### 🔹 If Test
- Testing conditional statements within parsing or grammar contexts  

### 🔹 If to Switch Statement
- Reads input 'if' statement from User
- Generate equivalent Switch statement code

### 🔹 LL1 Java
- Implementation of **LL(1) parsing concepts**  
- Construction or usage of predictive parsing techniques  

### 🔹 Looping Statements
- Recognition or implementation of loop constructs  
- Demonstration of control flow handling in compilers  

### 🔹 LR1 Java
- Implementation of **LR(1) parsing concepts**  
- Construction or usage of predictive parsing techniques

### 🔹 Procedure Calls & Arrays
- Handling procedure calls  
- Array-related grammar handling  

### 🔹 Common Sub-Expression Elimination Java
- Reads input Three Address Code from User  
- Checks for Common Sub-Expression among given TACs
- Eliminates (Replaces) Common Sub-Expressions found

### 🔹 Switch
- Demonstration of **switch-case constructs**  
- Parsing or evaluation logic

### 🔹 TAC & Postfix Intermediate Codes
- Reads input expression from File (File Handling)  
- Construction of intermediate TAC and postfix codes

### 🔹 Tokens
- Lexical analysis concepts  
- Token identification and classification  

---

## 🛠 Requirements  

To compile and run these programs, the following are recommended:

### For Java Programs
- **JDK (Java Development Kit)**
- Any IDE or editor such as:
  - VS Code  
  - IntelliJ IDEA  
  - Eclipse  
  - NetBeans  

### For Lex and Yacc Programs
- **Linux / Ubuntu Operating System**
- **Flex (Lex implementation)**
- **Bison (Yacc implementation)**
- **GCC Compiler**

These tools are commonly used in compiler construction and are **natively supported in Linux environments**.

---

## ▶️ How to Compile and Run  

### Running Java Programs

Compile:

```bash
javac ProgramName.java
```

Run:

```bash
java ProgramName
```

Example:

```bash
javac FirstFollow.java
java FirstFollow
```

---

### Running Lex Programs

Compile using Flex:

```bash
lex -d program.l
gcc lex.yy.c -o program
./program
```
or
```bash
lex -d filename.l
gcc lex.yy.c
./a.out
```

---

### Running Lex + Yacc Programs

```bash

lex -d program.l
yacc -d program.y
gcc y.tab.c lex.yy.c -o program
./program
```
or
```bash
lex -d filename.l
yacc -d filename.y
gcc lex.yy.c y.tab.c
./a.out
```

---

## 🪟 Alternatives for Windows Users  

Since **Lex and Yacc are primarily supported in Linux**, Windows users can use the following alternatives:

- **WSL (Windows Subsystem for Linux)** – Recommended  
- **Cygwin** – Provides a Unix-like environment on Windows  
- **MinGW + Flex/Bison**  
- **Git Bash with Flex/Bison installed**  
- **Virtual Machine running Ubuntu**

Among these, **WSL with Ubuntu is the most reliable option** for running Lex and Yacc based compiler programs.

---

## 📂 Repository Structure  

```
/Compiler-Engineering
 ├── Backend Machine Code Generaation/
 ├── Calculator Prog/
 ├── First & Follow using Java/
 ├── If else/
 ├── If test/
 ├── If Statement to Switch Statement/
 ├── LL1_Java/
 ├── LR1_Java/
 ├── Looping Statements/
 ├── Proc calls & Arrays/
 ├── Common Sub-Expression Elimination/ 
 ├── Switch/
 ├── Three Address and Postfix Intermediate Codes/ 
 ├── Tokens/
 └── README.md
```

---

## 🎓 Who This Is For  

✔ Students studying **Compiler Design / Compiler Engineering**  
✔ Beginners learning **lexical and syntax analysis**  
✔ Students preparing for **compiler design labs**  
✔ Anyone interested in **programming language implementation**

---

## 🤝 Contributions  

Contributions are welcome!

If you'd like to improve or add programs:
1. Fork the repository  
2. Create a new branch  
3. Commit your changes  
4. Submit a pull request  
---

## 📜 License  

This repository is open-source and free to use for **educational purposes**.

---

## ⭐ Support  

If you find this repository helpful, consider giving it a ⭐.
Happy Learning and Compiler Building! 🚀