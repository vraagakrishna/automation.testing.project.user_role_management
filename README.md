# User Role Management

## Table of Contents

* [Project Overview](#project-overview)
* [Project Goal](#project-goal)
* [Tech Stack](#tech-stack)
* [Project Structure](#project-structure)
* [Setup / Installation](#setup--installation)
* [Setup in IntelliJ IDEA (Optional)](#setup-in-intellij-idea--optional-)
* [CI/CD Pipelines](#cicd-pipelines)

<br/>

## Project Overview

This project demonstrates an **end-to-end automated test workflow** using **Cucumber (BDD)** to validate a complete
**user lifecycle and role elevation process**.

The automation validates:

1. Creating a new user
2. Approving the user
3. Promoting the user to an admin role
4. Logging in as the new user
5. Verifying elevated admin privileges

This project focuses on **role-based access control (RBAC)** validation and business workflow automation.

<br>

## Project Goal

The primary goal of this project is to:

* Demonstrate **Behavior-Driven Development (BDD)** using Cucumber
* Validate **multi-user workflows**
* Test **role-based access control (RBAC)** logic
* Simulate real-world **admin approval and privilege elevation**
* Showcase clean test design and maintainable automation structure

### What is BDD?

**Behaviour-Driven Development (BDD)** is a software development approach that:

* Encourages collaboration between **developers**, **QA**, and **non-technical stakeholders**
* Uses **plain language (Gherkin)** to describe application behavior
* Bridges the gap between **requirements and implementation**

In BDD, tests are written as **scenarios** that describe how the system should behave in specific situations. This makes
automated tests **readable and understandable by everyone**, not just engineers.

Example of a BDD scenario in this project:

```gherkin
Scenario: Promote a new user to Admin

Given a new user is created
And an existing admin approves the new user
And the admin assigns admin privileges to the new user
When the new user logs in
Then the user should have admin access
```

### What is Gherkin?

Gherkin is a **plain-text language** used to write **BDD test scenarios** in a way that's readable by humans **and**
executable by tools like Cucumber.

Key points:

* Uses simple keywords like `Given`, `When`, `Then`, `And`, `But`
* Written in **English (or other supported languages)**
* Bridges the gap between **technical implementation** and **business requirements**

### Difference between Cucumber and BDD

| Aspect     | BDD                                                          | Cucumber                                                       |
|:-----------|:-------------------------------------------------------------|:---------------------------------------------------------------| 
| What it is | A **methodology**; a way to design software around behaviors | A **tool/framework** executes BDD scenarios written in Gherkin |
| Purpose    | Enciurage collaboration between DEVs, QA and business        | Convert Gherkin scnarios into automated tests                  | 
| Output     | Plain-text, human-readable scenarios                         | Automated test results in console/HTML/report                  |

### Summary

> BDD is a **methodology** to describe system behavior in plain language.
>
> Gherkin is the **language** used to write these behaviors.
>
> Cucumber is the **tool** that runs Gherkin scenarios as automated tests.

<br/>

## Tech Stack

* Java 21
* Maven 2.x
* Git
* **Google Chrome** or **Microsoft Edge** or **Mozilla Firefox**
* Cucumber (BDD)
* Gherkin
* Selenium WebDriver

<br/>

## Project Structure

```bash
src
└── test
    ├── java
    │   ├── factory             # Browser creation & WebDriver configuration
    │   ├── hooks               # Cucumber @Before/@After lifecycle management
    │   ├── model               # Test data/domain models (e.g., User objects)
    │   ├── pages               # Page Object Model classes (UI interactions & locators)
    │   ├── runner              # Cucumber test runners (entry points, tag filtering)
    │   ├── stepdefinitions     # Gherkin step implementations (test logic layer)
    │   └── utils               # Reusable helpers (DriverManager, alerts, test data, etc.)
    │
    └── resources
        ├── features            # Gherkin feature files (BDD scenarios)
        ├── extent.properties   # Extent Reports configuration
        ├── spark-config.xml    # Spark reporter visual configuration
        └── testng.xml          # TestNG suite definition (for running Cucumber via TestNG)

pom.xml
```

<br/>

## Setup / Installation

1. Clone the repository:

```bash
git clone https://github.com/vraagakrishna/automation.testing.project.user_role_management.git
cd automation.testing.project.user_role_management.git 
```

2. Build the project:

```bash
mvn clean test
```

<br/>

## Setup in IntelliJ IDEA (Optional)

1. Import the project

* Open IntelliJ IDEA.
* Select **File** -> **Open**, and choose the clone project folder.
* Wait for IntelliJ to download all Maven dependencies.

2. Open the `testng.xml` file.

3. **Right-click** -> **Run 'testng.xml'**

<br/>

## CI/CD Pipelines

TBC

<br/>

