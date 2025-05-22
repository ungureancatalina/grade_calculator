## About the Project

**Grade Calculator** is a JavaFX-based desktop application designed to help university students calculate and track their grades easily. It enables users to input courses, assign scores, and compute their average grade automatically.

The application uses a local database for persistent storage of grades and course information, allowing students to maintain a history of their academic progress.

---

## Technologies Used

| Tool             | Purpose                                |
|------------------|----------------------------------------|
| Java             | Core programming language              |
| JavaFX           | Graphical user interface               |
| SQLite           | Embedded database                      |
| JDBC             | Database connectivity                  |
| SceneBuilder     | UI layout editor (optional)            |

---

## Features

- Add, edit, and delete courses and corresponding grades
- Automatic average grade calculation
- Persistent storage of data using SQLite
- Intuitive and clean GUI using JavaFX
- Modular structure for easy future expansion

---

## Project Structure

```
grade_calculator/
├── controller/        -> JavaFX controllers for user actions
├── model/             -> Data models for courses and grades
├── service/           -> Grade computation and logic
├── database/          -> Database file and query access
├── resources/         -> FXML UI files, styles, icons
└── Main.java          -> Application entry point
```

---

## How to Run

### Requirements

- Java 17 or higher
- JavaFX SDK installed and linked in your IDE
- IntelliJ IDEA, VS Code or another Java-compatible IDE

### Steps

1. Clone the repository:
```bash
git clone https://github.com/ungureancatalina/grade_calculator
cd grade_calculator
```

2. Configure JavaFX in your IDE:
   - Add the JavaFX SDK as a library
   - Use the following VM options (adjust the path):

   ```
   --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
   ```

3. Run `Main.java` to start the application.
