# Contributing to IPO Tracker

Thank you for your interest in contributing to the IPO Tracker project! This document provides guidelines for contributing.

## Development Setup

### Prerequisites
- Java 21 or later
- Maven 3.6 or later
- MySQL 8.0 or later (for production)
- Git

### Local Development
1. Clone the repository
2. Configure your database in `application.yml`
3. Run tests: `mvn test`
4. Start the application: `mvn spring-boot:run`

## Code Style

- Follow standard Java conventions
- Use meaningful variable and method names
- Add comments for complex business logic
- Write unit tests for new features

## Submitting Changes

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass: `mvn test`
6. Commit your changes: `git commit -m "Add your descriptive commit message"`
7. Push to your fork: `git push origin feature/your-feature-name`
8. Create a Pull Request

## Pull Request Guidelines

- Provide a clear description of the changes
- Include the motivation and context for the change
- Reference any related issues
- Ensure CI/CD pipeline passes
- Request review from maintainers

## Testing

- Write unit tests for new features
- Ensure existing tests still pass
- Test with both H2 (for tests) and MySQL (for integration)

## Reporting Issues

When reporting issues, please include:
- Steps to reproduce the issue
- Expected behavior
- Actual behavior
- Environment details (Java version, OS, etc.)

## Code of Conduct

Please be respectful and professional in all interactions.