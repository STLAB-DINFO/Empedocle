# Empedocle
## Overview
This repository provides the code and detailed instructions on how to build it and deploy it Wildfly. Specifically, detailed instructions will be given on how to compile the project directly from the command line along with a properly configured Wildfly server. This scenario is useful for developers who prefer a more granular and personalized management of the build and deploy process.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## Installation

### Prerequisites
#### Directory Structure
Module Structure
Ensure that you have a module structure as follows:
```
sourceCode/
├── stlab-modules/
├── empedocle/
│   ├── empedocle-viewers/
│   └── empedocle/
```
### Installing
#### Building Modules with Maven
* stlab-modules
  * Open the terminal or command prompt and navigate to the `sourceCode/stlab-modules folder`.
  * Execute the following command:
    ```
    mvn clean install -DskipTests
    ```
* empedocle-viewers
  * Navigate to the `sourceCode/empedocle/empedocle-viewers` folder via the terminal.
  * Execute the command:
    ```
    mvn clean install -DskipTests
    ```
* empedocle
  * Access the `sourceCode/empedocle/empedocle` folder via the terminal.
  * Use the command:
   ```
    mvn clean install -DskipTests
    ```
### Database Setup
Use the empedocle_initialized.sql script provided at this [link] to create the database schema. Ensure you have an empty database called emp_db on your system and run the script inside it to create tables and populate initial data.

Pre-set data:
* Username: administrator
* Password: adminpass

### Deploying the .war File with Wildfly
Once the previous steps are completed, a .war file will be created in the `sourceCode/empedocle/empedocle/target` directory.

Copy the `empedocle-1.0.0.war` file, generated in the `sourceCode/empedocle/empedocle/target` directory, into the `wildfly-16.0.0.Final/standalone/deployments` folder.

Navigate to the `wildfly-16.0.0.Final/bin` folder.

Execute the command: 
```
./standalone.sh
```

Once Wildfly is started correctly, the `empedocle.war` file will be automatically deployed from the deployments folder.

## Usage
### Configuration Guide
Refer to the "Guida alla Configurazione.pdf" for instructions on how to use the web application.

## License
This project is licensed under the [LICENSE NAME] - see the LICENSE.md file for details.
