# A general java project makefile
# Author: Songhui Yue (syue2@crimson.ua.edu)
# create: 2014-10-04

# Set the file name of your jar package:
JAR_PKG = lexer.jar

# Set your entry point of your java app:
ENTRY_POINT0 = PrintSource
ENTRY_POINT1 = Scanner
ENTRY_POINT2 = InterpreterTest
ENTRY_POINT3 = PrettyPrinter
ENTRY_POINT4 = EnvironmentTest

RES_DIR = no
# Set your source file

SOURCE_FILES = \
Types.java \
Lexeme.java \
Lexer.java \
Scanner.java \
Parser1122.java \
PrettyPrinter.java \
Environment.java \
EnvironmentTest.java \
Interpreter.java \
InterpreterTest.java \
PrintSource.java

# Set your java compiler here:
JAVAC = javac
# 
JFLAGS = -encoding UTF-8 

# Guide：
# make new: 
# ├── a.jar
# ├── bin
# │     └── test
# │             ├── A.class
# │             └── B.class
# ├── makefile
# ├── res
# │     └── doc
# │            └── readme.txt
# └── src
#        └── test
#                ├── A.java
#                └── B.java

# make build: in bin, generate java classes
# make clean: 
# make rebuild: 
# make run: run test
# make jar: generate executable jar package

#############Do not change below codes####################

vpath %.class bin
vpath %.java src

# show help message by default
Default:$(SOURCE_FILES:.java=.class)

# pattern rule
%.class: %.java
	$(JAVAC)   $(JFLAGS) $<

rebuild: clean build

.PHONY: new clean run jar

new:
ifeq ($(RES_DIR),yes)
	mkdir -pv  bin res
else
	mkdir -pv  bin
endif

clean:
	rm -frv *.class

run:
	java  $(ENTRY_POINT1) program2.txt

prettyPrinter:
	java  $(ENTRY_POINT3) program2.txt

source-arrays:
	java  $(ENTRY_POINT0) program_arrays.txt
arrays:
	java  $(ENTRY_POINT2) program_arrays.txt

source-conditionals:
	java  $(ENTRY_POINT0) program_conditionals.txt
conditionals:
	java  $(ENTRY_POINT2) program_conditionals.txt

source-recursion:
	java  $(ENTRY_POINT0) program_recursion.txt
recursion:
	java  $(ENTRY_POINT2) program_recursion.txt

source-iteration:
	java  $(ENTRY_POINT0) program_iteration.txt
iteration:
	java  $(ENTRY_POINT2) program_iteration.txt

source-functions:
	java  $(ENTRY_POINT0) program_functions.txt
functions:
	java  $(ENTRY_POINT2) program_functions.txt

source-delays:
	java  $(ENTRY_POINT0) program_delays.txt
delays:
	java  $(ENTRY_POINT2) program_delays.txt

source-objects:
	java  $(ENTRY_POINT0) program_objects.txt
objects:
	java  $(ENTRY_POINT2) program_objects.txt

source-lists:
	java  $(ENTRY_POINT0) program_lists.txt
lists:
	java  $(ENTRY_POINT2) program_lists.txt

environmentTest:
	java  $(ENTRY_POINT4)


source-calculator1:
	java  $(ENTRY_POINT0) program_c1.txt
hardCodeCalculator1:
	java  $(ENTRY_POINT2) program_c1.txt
source-calculator2:
	java  $(ENTRY_POINT0) program_c2.txt
hardCodeCalculator2:
	java  $(ENTRY_POINT2) program_c2.txt
	
jar:
ifeq ($(RES_DIR),yes)
	jar cvfe $(JAR_PKG) $(ENTRY_POINT)  -C bin . res
else
	jar cvfe $(JAR_PKG) $(ENTRY_POINT) -C bin .
endif
