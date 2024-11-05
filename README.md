# Intranet Youcode

## Project Context
This is a Spring Boot-based application designed to manage users, classrooms, and training programs within an organization. The application supports different user roles, specifically Learner and Instructor, with unique properties such as levels for learners and specialties for instructors.

## Key Features
- Single Table Inheritance for user roles (Learner, Instructor).
- Services for managing user, classroom, and training entities.
- Validation and exception handling using custom exceptions.
- Integration with a database for persistent storage using Spring Data JPA.

## Main Class Structure
- **User**: Represents a user in the system with shared attributes like `firstName`, `lastName`, `email`, and specific properties depending on the user role (Learner or Instructor).
- **Learner**: Inherits from `User` and adds attributes specific to a learner, such as `level` (e.g., beginner, intermediate, advanced).
- **Instructor**: Inherits from `User` and includes attributes specific to an instructor, such as `specialty` (e.g., subject expertise).
- **Classroom**: Represents a physical or virtual classroom with attributes like `name`, `roomNumber`, and `capacity`.
- **Training**: Represents a training program with attributes like `title`, `level`, `startDate`, `endDate`, `minCapacity`, `maxCapacity`, `status` (e.g., PLANNED, IN_PROGRESS, COMPLETED, CANCELLED), and related `classroom` and `users`.

## Technologies Used
- **Java 8**: The project is developed using Java 8, leveraging its features such as Stream API, Lambda expressions, Java Time API, and Optional.
- **Spring Boot**: The project is built with Spring Boot to simplify the development of web applications, providing features like dependency injection, RESTful APIs, and automatic configuration.
- **Maven**: Used for dependency management to streamline project configuration and build processes.
- **JUnit & Mockito**: Employed for creating unit tests to ensure the reliability and correctness of the application.
- **Design Patterns**:
  - **Repository Pattern**: Implemented for data access abstraction.
  - **Singleton Pattern**: Used to manage shared resources in the application.
- **Logging**: Integrated a logging system using SLF4J as the logging framework.
- **Hibernate Validations**: Implemented necessary validations using annotations like `@NotNull`, `@Size`, etc., to enforce data integrity.
- **Spring Data JPA**: Used to simplify database operations and manage entities, reducing boilerplate code for common database queries.

## Tools
- **Git**: Used for version control to manage the project, utilizing branches for feature development and collaboration.
- **Integrated Development Environment (IDE)**: Use any IDE of your choice to facilitate coding and debugging.
- **JIRA**: Employed for project management, using the Scrum methodology to plan and track progress, ensuring proper linkage with Git for issue tracking and feature development.

## Diagrams
- **Use Case Diagram**: ![Use Case Diagram](/resources/UML/classDiagram.png)

## Author
- [Mostafa Meskine](https://github.com/MesVortex)
