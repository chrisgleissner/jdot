![jdot logo](logo.png)

# jdot

Java Domain Object Templater (JDot) is a code generator which keeps domain classes concise like a dot. 
The generation is driven by annotations on your domain classes or interfaces and uses the compile-time Java 6 Annotation Processor.

## Requirements
- Oracle JDK 7

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
