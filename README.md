# The Green Garden Recruitment Application

This application was developed as part of the Royal Institute of Technology KTH course IV1201 - Design of Global Applications.

It serves as system handling recruitment applications and distinguish between two types of users: 
- applicants which applies for a position within the company owning the system 
- recruiters which manages applications

Applicants can log in and specify their competences from a given list, their experience in form of years as well as the time periods which they are available for work.
Recruiters can in addition to what a normal applicant can do, also search for applications entered by system users based on competence, availability periods and names. 

## Tools

The following software tools where used when developing the application:

- Version control (Git)
- Project management (Maven)
- Test (Springboot Test, JUnit, Pyppeteer)
- Static analysis (Spotbugs)
- Continous integration (Github Actions)
- Cloud runtime (Heroku)
- IDE (Jetbrains IntelliJ Ultimate)

## Frameworks

The following frameworks are used in the application:

- Spring Boot
- Thymeleaf
- Spring Data (Commons and JPA)

## Architecture

The image below shows the architectural layout of the application

<br/>
<br/>
<p align="center" >
  <img src="images/architecture.png" width="500" >
</p>
<br/>
<br/>

## DevOps

The image below shows the DevOps flow used during development

<br/>
<br/>
<p align="center" >
  <img src="images/devops.png" width="500" >
</p>
<br/>
<br/>


## Getting Started

The development was performed in Microsoft Windows and Mac OS and if another operating system is used there is no guarantee that the following steps will set up a working application.

1. Download the project source code.
2. Since the application was developed using the Intellij Ultimate IDEA it is recommended to download and install it. This can be done via this link [link](https://www.jetbrains.com/idea/). The community version can likely be used but there will probably be a need for adding plugins not existing originally.
3. Open up the downloaded project in IntelliJ and let the IDE through Maven download adn resolve all dependencies specified in the projects pom.xml file.
4. Run the application and connect to it with a browser of choice to the adress localhost:8080
5. Use one of the hard coded users of the application to login and test out the functionalities. To log in as an admin/recruiter use "testAdmin<span></span>@example.com" and to log in as a user/applicant use "testUser<span></span>@example.com" as user name. Both have "pass" as their login password.

With this continued development by developers who did not participate in the original development should be possible.

### Optional

1. To let the IDE connect to the database open up the "Database" tab in the upper right part of the development window and press the New butten marked as a + sign.
2. Click on "New Data Source from URL" and enter jdbc:h2:file:~/IdeaProjects/iv1201-project/database and choose H2 as driver.
3. In the authentication tab choose "No auth" and test the connection. It is important to run the application before doing this step so that a database file will be created in the specified path.
4. If a connection is established click "Apply" and the "Ok" and the IDE should have a connection to the applications database and the its content should be browsable. 

  
