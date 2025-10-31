# Project 2: Nondeterministic Finite Automata

* Author: Kevin Gutierrez, Evan Lauer
* Class: CS361 Section #002
* Semester: Fall 2025

## Overview

P2-NFA can create an NFA based on states and an alphabet that can be provided by a programmer.

## Reflection

This project was not too difficult to get started and implement the basic methods as there was a lot of similarities
between this and the previous project. However, implementing some of the methods unique to an NFA was a little bit of a
challenge. The debug functionality in IntelliJ makes debugging so much easier. Being able to slowly walk
through each test and see what is going wrong is invaluable compared to just examining code. Another thing that both of
us could improve is better documentation. When we implement methods seperately it can be hard to tell exactly how a
method functions with no documentation which makes things much harder. 

We did well with the test driven development this time. Last time we wrote tests before writing other code, but we just
implemented all the methods and then started testing. This made debugging take a lot longer, and we spent a long time 
trying to fix things to get tests to pass. This time, after writing a few tests, we implemented one method, then tested,
then implemented another method, and tested and so on. This made debugging go by a lot faster, and we really only got
stuck debugging from a few easy fixes.

## Compiling and Using

To run tests, use IntelliJ IDE and select the run button.

To run tests on onyx use the following commands:

[you@onyx]$ javac -cp .:/usr/share/java/junit.jar ./test/nfa/NFATest.java

[you@onyx]$ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar
org.junit.runner.JUnitCore test.nfa.NFATest

## Sources used

No external sources used.