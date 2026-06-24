# 🌱 Carbon Footprint Tracker

A desktop application built with **Java Swing**, **MVC Architecture**, and **MySQL** that helps users track, manage, and reduce their carbon footprint through activity logging, leaderboards, and challenges.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Modules](#modules)
- [Setup & Installation](#setup--installation)
- [Database Configuration](#database-configuration)
- [Testing](#testing)
- [Test Cases](#test-cases)
- [Known Issues](#known-issues)
- [Contributors](#contributors)

---

## Project Overview

The Carbon Footprint Tracker allows users to:
- Register and log in securely
- Log daily carbon-emitting activities
- View their footprint score on a dashboard
- Compete with others via a leaderboard
- Accept and complete sustainability challenges

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java (Core Java) |
| UI Framework | Java Swing |
| Architecture | MVC (Model-View-Controller) |
| Database | MySQL |
| Build Tool | Apache ANT |
| IDE | Apache NetBeans 30 |

---

## Modules

| Module | File | Description |
|---|---|---|
| Authentication | `LoginModule.java` | User login and session handling |
| Registration | `CreateAccount.java` | New user registration |
| Dashboard | `Dashboard.java` | Carbon score summary view |
| Activity Logger | `CarbonFootprint.java` | Log and calculate emissions |
| Leaderboard | `Leaderboard.java` | Ranked user comparison |
| Challenges | `Challenges.java` | Sustainability challenge tracking |
| Database Layer | `DBConnection.java`, `UserDAO.java` | MySQL connectivity and queries |
| Entry Point | `Welcome.java` | Application launcher |

---

## Setup & Installation

### Prerequisites
- Java JDK 8 or above
- Apache NetBeans IDE
- MySQL Server (running on localhost)
- Apache ANT (bundled with NetBeans)

### Steps

1. Clone the repository:
   ```
   git clone https://github.com/OnkarWaykule/Carbon-Footprint-Tracker.git
   ```

2. Open NetBeans → File → Open Project → select the cloned folder

3. Configure the database (see below)

4. Run the project: Right-click project → Run

---

## Database Configuration

1. Open MySQL and create the database:
   ```sql
   CREATE DATABASE carbon_tracker;
   ```

2. Update `DBConnection.java` with your credentials:
   ```java
   String url = "jdbc:mysql://localhost:3306/carbon_tracker";
   String user = "root";
   String password = "your_password";
   ```

3. Run the provided SQL scripts to create tables for users, activities, and challenges.

---

## Testing

### Testing Approach

This project was tested using **Manual Black-Box Testing** techniques including:
- Equivalence Partitioning
- Boundary Value Analysis
- Negative Testing
- UI/UX Validation

### Test Environment

| Parameter | Details |
|---|---|
| OS | Windows 11 |
| IDE | Apache NetBeans 30 |
| Database | MySQL 8.0 |
| Java Version | JDK 17 |
| Test Type | Manual / Functional |

---

## Test Cases

### Module 1: Login

| TC ID | Test Scenario | Input | Expected Output | Status |
|---|---|---|---|---|
| TC_LGN_01 | Valid login | Correct username & password | Dashboard opens | ✅ Pass |
| TC_LGN_02 | Invalid password | Wrong password | Error message shown | ✅ Pass |
| TC_LGN_03 | Empty username | Blank field | Validation message shown | ✅ Pass |
| TC_LGN_04 | Empty password | Blank field | Validation message shown | ✅ Pass |
| TC_LGN_05 | SQL Injection attempt | `' OR '1'='1` | Login rejected | ✅ Pass |

---

### Module 2: Create Account / Registration

| TC ID | Test Scenario | Input | Expected Output | Status |
|---|---|---|---|---|
| TC_REG_01 | Valid registration | All fields filled correctly | Account created, redirect to login | ✅ Pass |
| TC_REG_02 | Duplicate username | Existing username | Error: "User already exists" | ✅ Pass |
| TC_REG_03 | Empty fields | Blank form | Validation errors shown | ✅ Pass |
| TC_REG_04 | Password mismatch | Different passwords | Error: "Passwords do not match" | ✅ Pass |
| TC_REG_05 | Invalid email format | `user@` | Format validation triggered | ⚠️ Pending |

---

### Module 3: Carbon Footprint Logger

| TC ID | Test Scenario | Input | Expected Output | Status |
|---|---|---|---|---|
| TC_CFT_01 | Log valid activity | Activity type + value entered | Score updated on dashboard | ✅ Pass |
| TC_CFT_02 | Log zero emission activity | 0 entered | Score unchanged or 0 added | ✅ Pass |
| TC_CFT_03 | Negative input value | -10 | Validation error shown | ⚠️ Pending |
| TC_CFT_04 | Empty activity field | Blank | Validation message shown | ✅ Pass |
| TC_CFT_05 | Large input value | 999999 | Accepted or boundary error | ⚠️ Pending |

---

### Module 4: Dashboard

| TC ID | Test Scenario | Expected Output | Status |
|---|---|---|---|
| TC_DSH_01 | Load after login | User's total score displayed | ✅ Pass |
| TC_DSH_02 | No activities logged | Score shows 0 | ✅ Pass |
| TC_DSH_03 | Multiple activities | Score is cumulative sum | ✅ Pass |
| TC_DSH_04 | Navigation to other modules | Smooth page transition | ✅ Pass |

---

### Module 5: Leaderboard

| TC ID | Test Scenario | Expected Output | Status |
|---|---|---|---|
| TC_LDR_01 | Load leaderboard | Ranked list of users shown | ✅ Pass |
| TC_LDR_02 | Single user in DB | Only that user displayed | ✅ Pass |
| TC_LDR_03 | Verify ranking order | Lowest footprint ranked #1 | ✅ Pass |
| TC_LDR_04 | Empty database | "No records found" message | ⚠️ Pending |

---

### Module 6: Challenges

| TC ID | Test Scenario | Expected Output | Status |
|---|---|---|---|
| TC_CHG_01 | View available challenges | List of challenges displayed | ✅ Pass |
| TC_CHG_02 | Accept a challenge | Challenge marked as accepted | ✅ Pass |
| TC_CHG_03 | Complete a challenge | Status updated to "Completed" | ✅ Pass |
| TC_CHG_04 | Accept already accepted challenge | Error or no duplicate entry | ⚠️ Pending |

---

### Module 7: Database Connection

| TC ID | Test Scenario | Expected Output | Status |
|---|---|---|---|
| TC_DB_01 | Valid DB credentials | Connection established | ✅ Pass |
| TC_DB_02 | Wrong password in DBConnection | Connection error handled gracefully | ✅ Pass |
| TC_DB_03 | MySQL server offline | App shows DB error, does not crash | ⚠️ Pending |

---

## Known Issues

- Negative values are accepted in the activity logger (fix pending)
- Leaderboard does not refresh automatically after new activity is logged
- No session timeout implemented after idle period
- Email format validation not enforced during registration

---

## Contributors

| Name | Role |
|---|---|
| Onkar Waykule | Developer & Tester |

**Guided by:** Prof. Karade S.A.
**Institution:** Bhagwant Institute of Technology, Barshi (DBATU Affiliated)

---

## 📄 License

This project is developed for academic purposes.
