# jdot

[![Build Status](https://travis-ci.org/chrisgleissner/jdot.svg?branch=master)](https://travis-ci.org/chrisgleissner/jdot)
[![Coverage Status](https://coveralls.io/repos/github/chrisgleissner/jdot/badge.svg?branch=master)](https://coveralls.io/github/chrisgleissner/jdot?branch=master)

Java Domain Object Templater (JDot) is a code generator which keeps domain classes concise like a dot. 
The generation is driven by annotations on your domain classes or interfaces and uses the compile-time javac annotation processor.

## Code Generation Driver
The code generation may be driven by:
- Interface implemented by domain class and DTO
- Abstract domain class
- Generated Code

## Generated Artefacts
- Data Transfer Objects (DTOs) with Java Bean getters and setters
- Builders for immutable domain objects
- Mappers between domain and DTO classes
- Standard java.lang.Object methods hashCode and equals for configurable fields
