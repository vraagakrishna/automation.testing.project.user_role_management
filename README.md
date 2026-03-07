# User Role Management

## Table of Contents

* [Project Overview](#project-overview)
* [Project Goal](#project-goal)
* [Tech Stack](#tech-stack)
* [Project Structure](#project-structure)
* [Setup / Installation](#setup--installation)
* [Setup in IntelliJ IDEA](#setup-in-intellij-idea)
* [CI/CD Pipelines](#cicd-pipelines)

<br/>

## Project Overview

This project demonstrates an **end-to-end automated test workflow** using **Cucumber (BDD)** to validate a complete
**user lifecycle and role elevation process** on [Ndosi Test Automation](https://ndosisimplifiedautomation.vercel.app/).

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
    │   ├── common              # Global constatns and shared configurations
    │   ├── driver              # Browser abstraction layer 
    │   ├── factory             # High-level factory (BrowserFactory)
    │   ├── hooks               # Cucumber @Before/@After lifecycle management
    │   ├── model               # Test data/domain models (e.g., User objects)
    │   ├── pages               # Page Object Model classes (UI interactions & locators)
    │   ├── runner              # Cucumber test runners (entry points, tag filtering)
    │   ├── services            # External/system services (EmailService, MailSlurpEmailService)
    │   ├── stepdefinitions     # Gherkin step implementations (test logic layer)
    │   └── utils               # Reusable helpers (DriverManager, alerts, test data, etc.)
    │
    └── resources
        ├── features            # Gherkin feature files (BDD scenarios)
        ├── extent.properties   # Extent Reports configuration
        ├── spark-config.xml    # Spark reporter visual configuration

testng.xml          # TestNG suite definition (for running Cucumber via TestNG)

pom.xml
```

<br/>

## Setup / Installation

1. Clone the repository:

```bash
git clone https://github.com/vraagakrishna/automation.testing.project.user_role_management.git
cd automation.testing.project.user_role_management 
```

2. Build the project:

```bash
mvn clean test -Dbrowser=BROWSER_NAME -Dheadless=true -DscreenType=SCREEN_TYPE -DADMIN_EMAIL=ADMIN_EMAIL -DADMIN_PASSWORD=ADMIN_PASSWORD -DMAIL_SLURP_API_KEY=MAIL_SLURP_API_KEY
```

<br/>

## Setup in IntelliJ IDEA

1. Import the project

* Open IntelliJ IDEA.
* Select **File** -> **Open**, and choose the clone project folder.
* Wait for IntelliJ to download all Maven dependencies.

2. Open the `testng.xml` file.

3. **Right-click** -> **Run 'testng.xml'**

<br/>

## CI/CD Pipelines

This project is designed to run locally and in CI environments.

### Pipeline Objectives

* Execute automated UI tests
* Generate Extent HTML reports
* Capture logs and screenshots
* Support cross-browser execution via system properties
* Support responsive UI validation (desktop / tablet / mobile)
* Allow headless executive for CI environments

<br/>

### Running Tests in CI

Tests can be triggered using Maven with system properties:

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true -DscreenType=desktop -DADMIN_EMAIL=ADMIN_EMAIL -DADMIN_PASSWORD=ADMIN_PASSWORD -DMAIL_SLURP_API_KEY=MAIL_SLURP_API_KEY
```

Supported runtime parameters:

| Parameter  | Description                           | Default   |
|:-----------|:--------------------------------------|:----------|
| browser    | Browser to execute tests on           | chrome    |
| headless   | Run browser in headless mode          | true      | 
| os         | Operating system override             | System OS | 
| screenType | Screen size for responsive UI testing | desktop   | 

<br/>

### Screen Size Options

The framework supports responsive layout validations by adjusting browser window size at runtime.

| screenType | Resolution (Width x Height) | Description                  |
|:-----------|:----------------------------|:-----------------------------| 
| desktop    | 1920 x 1080                 | Standard laptop/desktop view | 
| tablet     | 768 x 1024                  | Tablet portrait layout       |
| mobile     | 375 x 182                   | Mobile phone layout          |

Screen size is controlled via the `screenType` system property and is automatically handled by the `DriverManager`
and `NavigationFactory`.

<br/>

### Email Testing (MailSlurp)

This project includes automated tests that verify password reset and other email-based workflows.
To support this functionality, the tests use the email testing service [MailSlurp](https://app.mailslurp.com/).

MaiSlurp allows test to:

* Create temporary inboxes
* Receive emails sent by the application
* Extract links or verification codes from those emails
* Clean up inboxes after test executions

Each email test dynamically creates **a unique inbox per test run**, ensuring tests remain isolated and do not interfere
with each other.

#### Why do the Email Tests only run in certain CI configurations?

Because each email test creates a temporary inbox, running them across all browser combinations would quickly consume
the inbox quota.

To optimise resource usage, email tests are executed **only in the following CI configuration**:

| browser | screenType | 
|:--------|:-----------| 
| chrome  | desktop    | 
| chrome  | mobile     | 

Email tests are **automatically skipped** for other combinations.

This behaviour is controlled by a Cucumber `@email` tag and conditional test hooks that skip execution when the CI
matrix configuration does not match the allowed combinations.

#### MailSlurp Setup

To run email tests locally or in CI, a MailSlurp API key is required.

1. Create a MailSlurp Account at [https://app.mailslurp.com/](https://app.mailslurp.com/).

2. Generate an **API Key** from the dashboard.

3. Configure the API Key as environment variable (`MAIL_SLURP_API_KEY`)

4. GitHub Actions Setup
   In CI, the API Key should be stored as a **GitHub repository secret**:

```
MAIL_SLURP_API_KEY
```

The pipeline automatically injects this secret when running tests.

#### Inbox Cleanup

Each test deletes the temporary inbox after execution to prevent resource leaks and keep the MailSlurp accounts clean.

<br/>

### Example Executions

Desktop (default):

```bash
mvn clean test -Dbrowser=chrome -DADMIN_EMAIL=ADMIN_EMAIL -DADMIN_PASSWORD=ADMIN_PASSWORD -DMAIL_SLURP_API_KEY=MAIL_SLURP_API_KEY
```

Mobile (headless):

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true -DscreenType=mobile -DADMIN_EMAIL=ADMIN_EMAIL -DADMIN_PASSWORD=ADMIN_PASSWORD -DMAIL_SLURP_API_KEY=MAIL_SLURP_API_KEY
```

Cross-browser + mobile:

```bash
mvn clean test -Dbrowser=firefox -DscreenType=mobile -DADMIN_EMAIL=ADMIN_EMAIL -DADMIN_PASSWORD=ADMIN_PASSWORD
```

<br/>

### Report Artifacts

After execution, the following artifacts are generated:

* `reports/ExtentReport.html` -> Test execution report
* `/screenshots/` -> Failure or additional screenshots
* `execution.log` -> Stored via LoggerManager

These artifacts should be archived in the CI pipeline for traceability.

<br/>

