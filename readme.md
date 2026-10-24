# 🖋️ SEL Compiler

**SEL Compiler** (Small Experimental Language compiler) This project is part of the **Language Theory course INFO—F403** at `ULB`. It implements the **lexical analysis** phase for a toy programming language called **SEL**, turning source code into a stream of tokens for later parsing.

## 📜 Description

This Java project provides:

- A **lexer** generated with **JFlex**.
- Recognition of **keywords**, **identifiers**, **numbers**, **operators**, and **comments**.

## ⚙️ Installation

1. Clone the repository:

    ```sh
    git clone git@github.com:mbontrid/SEL-Compiler.git
    cd SEL-Compiler
    ```

2. Make sure you have **Java JDK 17+** installed and **JFlex**. Those can be installed using `pacman` and `yay`:

    ```sh
    sudo pacman -S jdk17-openjdk
    yay -S jflex
    ```

3. Compile the project using the provided `Makefile`:

    ```sh
    make
    ```

Alternatively, you can compile the project directly using `javac`.

## 🛠️ Usage

### Running the Lexer

To run the lexer on a source file, use:

```sh
java -jar dist/part1.jar test/<sourcefile>.ycc
```

```sh
java -jar dist/part1.jar test/<sourcefile>.ycc
```

Example:

```sh
java -jar dist/part1.jar test/Euclid.sel
```

## 📚 Documentation

The complete **Javadoc** is hosted on Github Pages. It is available here:

👉 [View the Javadoc online](https://mbontrid.github.io/SEL-Compiler/package-summary.html)

## ✨ Generating Javadoc

To generate documentation:

```sh
javadoc -d doc src/*.java  # use -private flag to show `all members`
```

- `-d doc`: output folder for HTML doc files.
- `src/*.java`: java files to generate doc from.

Once generated, open `doc/index.html` in a browser to explore the documentation.

## 🧪 Running Unit Tests

To ensure the project works as expected, you can run the **JUnit 5 tests** for the SEL Compiler included in the repository.

The `Makefile` provides a `test` target. When executed, it will:

- Ensure the **`lib/`** folder exists and download the **JUnit 5 standalone JAR** if missing.
- Compile all test files located in `test/java/`.
- Run all tests automatically.

Simply run:

```sh
make test
```
