# Hello Spring

This is a Spring Boot application for demonstrating various Spring features.

## Security Configuration

The application has been configured with proper security settings to eliminate the warning about generated security passwords. The configuration includes:

1. A custom `SecurityConfig` class that defines:
   - A `SecurityFilterChain` bean that configures HTTP security
   - A `UserDetailsService` bean that provides a custom user with a fixed password
   - A `PasswordEncoder` bean that uses BCrypt for password encoding

2. Security properties in `application.properties`:
   - `spring.security.user.name=user`
   - `spring.security.user.password=secure-password`

## Java Agent Warning

The Java agent warning has been addressed by adding the following JVM argument in `application.properties`:
- `spring.jvm.args=-XX:+EnableDynamicAgentLoading`

This flag enables dynamic agent loading, which suppresses the warning message about Java agents being loaded dynamically.

## Running the Application

To run the application:

```bash
./mvnw spring:run
```

Or with Maven:

```bash
mvn spring:run
```