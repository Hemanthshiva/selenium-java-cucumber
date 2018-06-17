##About##

**interview-test project** is a template for a [Cucumber-jvm](https://github.com/cucumber/cucumber-jvm), JAVA, [Selenium WebDriver](http://www.seleniumhq.org/projects/webdriver/) (supports UI testing) and [JUnit](http://junit.org/) project. 

This template implements the [Selenium Page Object] design pattern.

The purpose of this template is to provide a quick start to new software test automation projects. 

To get started, create a copy of this project in a directory on your machine (e.g. download the zip or better [fork the repository] which allows you to **git pull** updates). The root directory should be a project name of your choosing.

I have used **interview-test** as my project name just because I have to submit this project as an assignment.


##Table of Contents##

[Modify pom.xml](#modify-pom-xml)

[Installations](#installations)

[Run using the Maven Build configuration](#run-using-the-maven-build-configuration)

[Advanced Cucumber Reporting](#advanced-cucumber-reporting)


#Modify pom.xml

Modify the pom.xml file according your needs.  
Use [this](https://maven.apache.org/pom.html) as a guide.

At a minimum you should supply new values for **groupId** (unique within your organization) and **artifactId** (generally your project name). Changes must be reflected in the folder names of your project.


##Installations

Please install all the below software's for running your tests.
        
        IntelliJ
        Selenium WebDriver
        JUnit
        Cucumber
        ChromeDriver
        Maven
        
###Run using the Maven Build configuration

To run your tests on Mac just double click or Run the **RunTestOnMac.sh** file.

To run your tests on Windows just double click or Run the **RunTestOnWindows.cmd** file.

##Advanced Cucumber Reporting

I have used `maven-cucumber-reporting` for advanced reporting.

Once you have run the test navigate to _/target/cucumber-reports/advanced-reports/cucumber-html-reports/overview-features.html_ to view the html report.
