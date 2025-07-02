# Library Management System

A simple console-based library management system built with Java to demonstrate Object-Oriented Programming concepts and best practices for beginners.

## 🎯 Learning Objectives

This project helps you learn:
- **Object-Oriented Programming**: Classes, inheritance, encapsulation, and polymorphism
- **Collections Framework**: Using List and Map data structures
- **Exception Handling**: Proper error handling techniques
- **Unit Testing**: Writing JUnit tests for your code
- **Code Quality**: Using SonarLint for code analysis
- **Maven**: Project structure and dependency management

## 🚀 Features

- ✅ Add new books to the library catalog
- ✅ Register new library members
- ✅ Borrow and return books
- ✅ Search books by title, author, or category
- ✅ Track borrowed books and due dates
- ✅ Generate various reports (popular books, overdue books, statistics)
- ✅ Comprehensive unit tests
- ✅ Code quality checks with SonarLint

## 📁 Project Structure

```
library-management-system/
├── src/
│   ├── main/java/com/library/
│   │   ├── model/              # Data models
│   │   │   ├── Book.java       # Book entity
│   │   │   ├── Member.java     # Library member entity
│   │   │   └── Transaction.java # Borrowing transaction
│   │   ├── service/            # Business logic
│   │   │   ├── LibraryService.java  # Main library operations
│   │   │   └── ReportService.java   # Report generation
│   │   └── LibraryManagementApp.java # Main application
│   └── test/java/com/library/  # Unit tests
│       ├── model/
│       └── service/
├── pom.xml                     # Maven configuration
├── sonar-project.properties    # SonarLint configuration
└── README.md                   # This file
```

## 🛠️ Prerequisites

- Java 11 or higher
- Maven 3.6+
- IDE with SonarLint plugin (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Getting Started

### 1. Clone or Download the Project

Create the project structure and copy all the provided Java files into their respective directories.

### 2. Build the Project

```bash
# Navigate to project directory
cd library-management-system

# Clean and compile
mvn clean compile

# Run tests
mvn test

# Generate test coverage report
mvn jacoco:report

# Package the application
mvn package
```

### 3. Run the Application

```bash
# Method 1: Using Maven
mvn exec:java -Dexec.mainClass="com.library.LibraryManagementApp"

# Method 2: Using compiled JAR
java -jar target/library-management-system-1.0.0.jar

# Method 3: From your IDE
# Run LibraryManagementApp.java directly
```

### 4. Set Up SonarLint

#### IntelliJ IDEA:
1. Go to `File → Settings → Plugins`
2. Search for "SonarLint" and install it
3. Restart your IDE
4. SonarLint will automatically analyze your code

#### Eclipse:
1. Go to `Help → Eclipse Marketplace`
2. Search for "SonarLint" and install it
3. Restart Eclipse
4. Right-click project → SonarLint → Analyze

#### VS Code:
1. Open Extensions panel (`Ctrl+Shift+X`)
2. Search for "SonarLint" and install it
3. The extension will automatically analyze your code

## 🎮 Demo Usage

The application comes with sample data pre-loaded. When you run it, you'll see:

### Sample Books:
- Effective Java by Joshua Bloch
- Head First Design Patterns by Eric Freeman
- Effective C++ by Scott Meyers
- Clean Code by Robert Martin
- The Pragmatic Programmer by Andrew Hunt

### Sample Members:
- John Doe (M001)
- Jane Smith (M002)
- Bob Johnson (M003)

### Demo Transactions:
The app automatically runs a demo showing:
1. John borrowing "Effective Java"
2. Jane borrowing "Clean Code" 
3. Bob borrowing "Head First Design Patterns"
4. John returning "Effective Java"

## 📋 Available Operations

### Main Menu Options:
1. **Add New Book** - Add books to the library catalog
2. **Add New Member** - Register new library members
3. **Borrow Book** - Allow members to borrow available books
4. **Return Book** - Process book returns
5. **Search Books** - Find books by title, author, or category
6. **Show Available Books** - List all currently available books
7. **Generate Reports** - Various reporting options
8. **Exit** - Close the application

### Report Options:
1. **Popular Books Report** - Books sorted by borrow count
2. **Overdue Books Report** - Books past their due date
3. **Library Statistics** - Overall library stats
4. **Member Activity Report** - Member borrowing activity
5. **Books by Category Report** - Books grouped by category

## 🧪 Running Tests

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn clean test jacoco:report

# Run specific test class
mvn test -Dtest=LibraryServiceTest

# Run tests and check coverage thresholds
mvn clean test -Pcoverage
```

### Viewing Test Results:
- **Test Reports**: `target/surefire-reports/`
- **Coverage Reports**: `target/site/jacoco/index.html`

## 📊 Code Quality with SonarLint

SonarLint will help you identify:
- **Code Smells**: Maintainability issues
- **Bugs**: Potential runtime errors
- **Security Vulnerabilities**: Security issues
- **Duplicated Code**: Code that can be refactored

### Common Issues for Beginners:
1. **Unused imports** - Remove imports you don't use
2. **Magic numbers** - Use constants instead of hardcoded numbers
3. **Long methods** - Break down complex methods
4. **Naming conventions** - Use descriptive variable names
5. **Exception handling** - Proper try-catch usage

## 🎓 Key OOP Concepts Demonstrated

### 1. Encapsulation
- Private fields with public getters/setters
- Data hiding in model classes
- Controlled access to internal state

### 2. Inheritance
- Common object behaviors
- Method overriding (toString, equals, hashCode)

### 3. Polymorphism
- Interface implementations
- Method overloading

### 4. Abstraction
- Service layer separation
- Clear class responsibilities

## 🔄 Extending the Project

### Easy Extensions:
- Add book categories (Fiction, Non-Fiction, Science, etc.)
- Implement fine calculation for overdue books
- Add book reservation system
- Email notifications for due dates

### Medium Extensions:
- Database integration (H2, MySQL)
- REST API with Spring Boot
- Web interface with Spring MVC
- Book recommendation system

### Advanced Extensions:
- Multi-library system
- User authentication and authorization
- Microservices architecture
- Integration with external book APIs

## 🐛 Troubleshooting

### Common Issues:

**Maven not found:**
```bash
# Check if Maven is installed
mvn --version

# Install Maven on Ubuntu/Debian
sudo apt install maven

# Install Maven on macOS
brew install maven
```

**Java version issues:**
```bash
# Check Java version
java --version

# Set JAVA_HOME if needed
export JAVA_HOME=/path/to/your/java11
```

**Tests failing:**
- Make sure all dependencies are installed: `mvn clean install`
- Check if test files are in correct directories
- Verify JUnit 5 is being used

**SonarLint not working:**
- Restart your IDE after installation
- Check if the plugin is enabled
- Verify Java project is properly configured

## 📚 Learning Resources

### Java & OOP:
- [Oracle Java Documentation](https://docs.oracle.com/javase/11/)
- [Java OOP Tutorial](https://www.w3schools.com/java/java_oop.asp)

### Testing:
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Testing Best Practices](https://phauer.com/2019/modern-best-practices-testing-java/)

### Code Quality:
- [SonarLint Documentation](https://www.sonarlint.org/)
- [Clean Code Principles](https://clean-code-developer.com/)

### Maven:
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [Maven by Example](https://books.sonatype.com/mvnex-book/reference/)

## 📝 Assignment Ideas

1. **Add new features** (difficulty: beginner)
   - Implement book categories
   - Add member types (Student, Faculty, Staff)
   - Create a simple GUI

2. **Improve code quality** (difficulty: intermediate)
   - Achieve 90%+ test coverage
   - Fix all SonarLint issues
   - Add comprehensive error handling

3. **Extend functionality** (difficulty: advanced)
   - Add database persistence
   - Implement REST API
   - Create web interface

## 🤝 Contributing

This is a learning project, but feel free to:
- Report bugs or issues
- Suggest improvements
- Add more test cases
- Improve documentation

## 📄 License

This project is created for educational purposes. Feel free to use and modify as needed for learning.

---

**Happy Coding! 🚀**

Remember: The best way to learn programming is by writing code, making mistakes, and fixing them. Don't be afraid to experiment with the code!