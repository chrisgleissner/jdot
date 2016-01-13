Java Domain Object Templater (JDot) is a code generator which keeps domain classes concise like a dot. The generation is driven by annotations on your domain classes or interfaces and uses the compile-time Java 6 Annotation Processor.

## Generating Code ##
The code generation may be driven by:
  * Interface implemented by domain class and DTO
  * Abstract domain class

## Generated Code ##
The following code artifacts are generated:
  * Data Transfer Objects (DTOs) with Java Bean getters and setters
  * Builders for immutable domain objects
  * Mappers between domain and DTO classes
  * Standard java.lang.Object methods hashCode and equals for configurable fields