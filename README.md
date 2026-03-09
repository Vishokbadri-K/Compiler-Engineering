# Compiler Engineering Programs Repository  

## 📌 Overview  

This repository contains a collection of **Compiler Engineering programs** that demonstrate fundamental concepts involved in the design and implementation of compilers.

The programs help illustrate important stages of a compiler such as **lexical analysis, syntax analysis, token recognition, parsing, and grammar analysis**. These implementations are useful for students studying compiler design and for understanding how programming languages are processed internally.

Some programs may use tools such as **Lex and Yacc**, which are primarily supported in **Linux/Ubuntu environments**.

---

## 🎯 Objectives  

- Understand the **basic phases of a compiler**
- Learn how **tokens and lexical analysis** work
- Practice **syntax analysis and grammar parsing**
- Implement algorithms used in **compiler design**
- Gain hands-on experience with **Lex and Yacc tools**

---

## 🧠 Topics Covered  

The repository includes programs related to:

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

### 🔹 LL1 Java
- Implementation of **LL(1) parsing concepts**  
- Construction or usage of predictive parsing techniques  

### 🔹 Looping Statements
- Recognition or implementation of loop constructs  
- Demonstration of control flow handling in compilers  

### 🔹 Procedure Calls & Arrays
- Handling procedure calls  
- Array-related grammar handling  

### 🔹 Switch
- Demonstration of **switch-case constructs**  
- Parsing or evaluation logic  

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
 ├── Calculator Prog/
 ├── First & Follow using Java/
 ├── If else/
 ├── If test/
 ├── LL1_Java/
 ├── Looping Statements/
 ├── Proc calls & Arrays/
 ├── Switch/
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