# ist-411-group-1
## Table of Contents
- Development
1. [Programs you need](#programs-you-need)
2. [Initializing project](#initializing-project)
3. [Running Project](#running-project)

# Development
## Programs you need
- Integrated Development Environment (IDE) - [Visual Studio Code](https://code.visualstudio.com/download) is fast and easy to use. It comes with Git integration and support for a variety of different terminals and development workflows. You can customize VS Code with different plugins to add language specific linting (code correction) and support for language specific development requirements by installing extensions through the embedded extension browser.
- Java Development Kit (JDK) - Make sure [Java](https://www.oracle.com/java/technologies/downloads/) is installed and configured correctly in your `PATH`.
- [Maven](https://maven.apache.org/install.html) is a project managment tool that can manage a project's build, reporting and documentation based on the contents of the project object model outlined `pom.xml`.
- You will also need a terminal. You have a lot of options here. I mainly use [Windows Subsystem for Linux](https://docs.microsoft.com/en-us/windows/wsl/install) (WSL) which, put simply, let's you use a Linux terminal in a Windows environment. That's not necessary for this project; you can get by with Command prompt or Powershell.

**Make sure Java and Maven are both in your PATH**
Run `java --version` and `mvn --version` to confirm.

[Stack Overflow thread: How to add Maven to the Path variable?](https://stackoverflow.com/questions/45119595/how-to-add-maven-to-the-path-variable)

## Initializing project

**Note: This is how the project was initialized, you don't need to do this to run it the code in this repo**

Open your terminal and `cd` into the directory where you want your project directory to live.

Run this command to generate a project folder `[-DartifactId]` in you current working directory.

```bash
mvn archetype:generate \
    -DarchetypeGroupId=org.openjfx \
    -DarchetypeArtifactId=javafx-archetype-simple \
    -DarchetypeVersion=0.0.3 \
    -DgroupId=psu.edu \
    -DartifactId=netEx1 \
    -Dversion=1.0.0 \
    -Djavafx-version=17.0.1
```

This generates a project from a template that OpenJFX provides to get started.

command used to generate starting archetype
```bash
mvn archetype:generate -DarchetypeGroupId=org.openjfx -DarchetypeArtifactId=javafx-archetype-simple -DarchetypeVersion=0.0.3 -DgroupId=psu.edu  -DartifactId=food-list-app -Dversion=1.0.0 -Djavafx-version=17.0.1
```

## Running Project
```bash
# clone project
git clone git@github.com:danfred360/ist-411-group-1.git
cd ist-411-group-1

# check out develop branch to run version of program that's still in development
git checkout develop
# run project
mvn clean javafx:run
```


## Explanation
Run `code .` to open VS Code in your current working directory to look at what you generated.

Run `mvn clean javafx:run` from inside your project directory to run the project. (You may need to us `cd` in the terminal to change directory.)

*Note: The `mvn archetype:generate` command is similar to what Netbeans does when you start a project. It generates all the files you need in the correct structure, so it saves you a lot of headaches.*