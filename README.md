# DayDream Management Tool for Daydreamers
  
This is a DayDream Management Tool for Daydreamers which manages daydreams based in a Web-Application published on Azure Cloud with Java as Backend and React as Frontend.

[![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

#### Contents:
- [Analysis](#analysis)
  - [Scenario](#scenario)
  - [User Stories](#user-stories)
  - [Use Case](#use-case)
- [Design](#design)
  - [Prototype Design](#prototype-design)
  - [Domain Design](#domain-design)
  - [Business Logic](#business-logic)
- [Implementation](#implementation)
  - [Backend Technology](#backend-technology)
  - [Frontend Technology](#frontend-technology)
- [Project Management](#project-management)
  - [Roles](#roles)
  - [Milestones](#milestones)

## Analysis
### Analysis Overview

The DayDream Management Tool is a collaborative platform designed to enable users to capture, organize, and share their daydreams. The application serves as a digital space for creative expression and community interaction, allowing users to document personal thoughts while connecting with others who share similar interests.

**Key Features:**
- User authentication and account management
- Daydream creation, retrieval, updating, and deletion (CRUD operations)
- Privacy controls with public/private visibility settings
- Community engagement through comments and interactions
- Personal feed management for individual content organization

**Target Users:**
- Casual dreamers seeking a personal journal platform
- Creative individuals wanting to share ideas with a community
- Users interested in discovering and discussing others' perspectives

**Core Value Proposition:**
DayDream provides an intuitive, web-based solution for documenting and sharing creative thoughts with customizable privacy controls and community interaction features.

### Scenario
The system enables users to create and manage personal daydreams with customizable privacy settings. It provides authentication, CRUD operations, and community interaction features allowing users to document creative thoughts, control visibility, and engage with other users through comments on public daydreams.

### User Stories
1. As a Visitor, I want to register with a username, email, and password so that I can create my own DayDream account.

2. As a User, I want to log in with my email and password so that I can access my account.

3. As a User, I want to log out of my account so that I can securely end my session.

4. As a User, I want to browse all public daydreams so that I can discover what others are dreaming about.(READ)

5. As a User, I want to create a daydream with a title, description, and mood so that I can capture my ideas and feelings.(CREATE)

6. As a User, I want to update my daydream’s title, description, and mood so that I can keep my ideas and feelings up to date.(UPDATE)

7. As a User, I want to delete my daydream so that I can remove content I no longer want to keep.(DELETE)

8. As a User, I want to set my daydream visibility to public or private so that I can control who can see my content.

9. As a User, I want to view only my personal daydream feed so that I can focus on my own content.

10. As a User, I want to comment on public daydreams so that I can interact with the community and discuss ideas.

### Use Case

![](images/use-case.png)


## Use Cases

### UC-1: Register Account
**Actor:** Visitor  
**Precondition:** User is not logged in
**Main Flow:**
1. Visitor selects “Register”
2. System displays registration form
3. Visitor enters username, email, and password
4. Visitor submits the form
5. System validates input (email format, password strength, uniqueness)
6. System creates account
7. System confirms registration and logs user in
**Alternative Flow:**
- Invalid input → show error message
- Email already exists → prompt user to log in

### UC-2: Log In
**Actor:** User  
**Precondition:** User has a registered account
**Main Flow:**
1. User selects “Log In”
2. System displays login form
3. User enters email and password
4. User submits credentials
5. System validates credentials
6. System grants access and redirects to dashboard
**Alternative Flow:**
- Invalid credentials → show error message

### UC-3: Log Out
**Actor:** User  
**Precondition:** User is logged in
**Main Flow:**
1. User selects “Log Out”
2. System terminates session
3. System redirects to Login Form

### UC-4: Browse Public Daydreams
**Actor:** User  
**Precondition:** User is logged in
**Main Flow:**
1. User navigates to “Public Feed”
2. System retrieves public daydreams
3. System displays list of daydreams
4. User scrolls and views content

### UC-5: Create Daydream
**Actor:** User  
**Precondition:** User is logged in
**Main Flow:**
1. User selects “Create Daydream”
2. System displays creation form
3. User enters title, description, and mood
4. User selects visibility (public/private)
5. User submits form
6. System validates input
7. System saves daydream
8. System confirms creation
**Alternative Flow:**
- Missing/invalid fields → show error message

### UC-6: Update Daydream
**Actor:** User  
**Precondition:** User owns the daydream
**Main Flow:**
1. User selects a daydream
2. User clicks “Edit”
3. System displays editable form
4. User modifies title, description, or mood
5. User submits changes
6. System validates input
7. System updates daydream
8. System confirms update
**Alternative Flow:**
- Unauthorized access → deny action

### UC-7: Delete Daydream
**Actor:** User  
**Precondition:** User owns the daydream
**Main Flow:**
1. User selects a daydream
2. User clicks “Delete”
3. System asks for confirmation
4. User confirms deletion
5. System deletes daydream
6. System updates feed

### UC-8: Set Daydream Visibility
**Actor:** User  
**Precondition:** User owns the daydream
**Main Flow:**
1. User selects a daydream
2. User chooses visibility option (public/private)
3. System updates visibility setting
4. System confirms change

### UC-9: View Personal Feed
**Actor:** User  
**Precondition:** User is logged in
**Main Flow:**
1. User navigates to “My Daydreams”
2. System retrieves user’s daydreams
3. System displays personal feed
4. User can switch between viewing public, private, or both types of daydreams in their feed

### UC-10: Comment on Daydream
**Actor:** User  
**Precondition:** User is logged in and viewing a public daydream
**Main Flow:**
1. User opens a public daydream
2. User enters a comment
3. User submits comment
4. System validates input
5. System saves comment
6. System displays comment under the post
**Alternative Flow:**
- Empty comment → show error message
## Design
### Prototype Design

The Prototype Design phase focuses on creating visual mockups and interactive wireframes for the DayDream application. The prototype includes:

**Home Page**: Landing page with navigation to login/register
**Dashboard**: Personal feed where users can view, filter, and switch between their public, private, or all daydreams
**Create/Edit Daydream Page**: Form interface for creating new daydreams or editing existing ones, including title, description, mood, and visibility (public/private)
**Public Feed**: Browse all public daydreams from the community, with filtering and search options
**Detail View**: Individual daydream display with the ability to view and add comments (if public), and see all related interactions
**User Profile**: Manage account settings, view personal information, and control privacy/visibility preferences
**Commenting**: Users can comment on public daydreams, with validation for empty comments and error handling
**Visibility Controls**: Users can set or update the visibility of each daydream (public/private) at creation or later

The prototype incorporates the Corporate Identity guidelines with a cohesive color scheme, typography, and responsive design for optimal user experience across devices.

**Corporate Identity (implemented in frontend)**


### Wireframe
> 🚧: It is suggested to start with a wireframe. The wireframe focuses on the website structure (Sitemap planning), sketching the pages using Wireframe components (e.g., header, menu, footer) and UX. You can create a wireframe already with draw.io or similar tools. 

TODO: START
Starting from the home page, we can visit different pages. Available public pages are visible in the menu...
TODO: END

### Prototype
> 🚧: A prototype can be designed using placeholder text/figures in Budibase. You don't need to connect the front-end to back-end in the early stages of the project development.
TODO: START
??
TODO: END

### Domain Design
The `ch.fhnw.dream.data.domain` package contains the following domain objects / entities including getters and setters:

![](images/class-diagram.png)

### Business Logic 
> 🚧: Describe the business logic for **at least one business service** in detail. If available, show the expected path and HTPP method. The remaining documentation of APIs shall be made available in the swagger endpoint. The default Swagger UI page is available at /swagger-ui.html.
TODO: START
Based on the UC-4, there will be two offers and a standard offer. Given a location, a message is shown accordingly:

- If the location is "Basel", the message is "10% off on all large pizzas!!!"
- If the location is "Brugg", the message is "two for the price of One on all small pizzas!!!"
- Otherwise, the message is "No special offer".

**Path**: [`/api/menu/?location="Basel"`] 

**Param**: `value="location"` Admitted value: "Basel","Brugg".

**Method:** `GET`
TODO: END
## Implementation
> 🚧: Briefly describe your technology stack, which apps were used and for what.
TODO: START
TODO: END
### Backend Technology
> 🚧: It is suggested to clone this repository, but you are free to start from fresh with a Spring Initializr. If so, describe if there are any changes to the PizzaRP e.g., different dependencies, versions & etc... Please, also describe how your database is set up. If you want a persistent or in-memory H2 database check [link](https://github.com/FHNW-INT/Pizzeria_Reference_Project/blob/main/pizza/src/main/resources/application.properties). If you have placeholder data to initialize at the app, you may use a variation of the method **initPlaceholderData()** available at [link](https://github.com/FHNW-INT/Pizzeria_Reference_Project/blob/main/pizza/src/main/java/ch/fhnw/pizza/PizzaApplication.java).
TODO: START
TODO: END

This Web application is relying on [Spring Boot](https://projects.spring.io/spring-boot) and the following dependencies:

- [Spring Boot](https://projects.spring.io/spring-boot)
- [Spring Data](https://projects.spring.io/spring-data)
- [Java Persistence API (JPA)](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html)
- [H2 Database Engine](https://www.h2database.com)

To bootstrap the application, the [Spring Initializr](https://start.spring.io/) has been used.

Then, the following further dependencies have been added to the project `pom.xml`:

- DB:
```XML
<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
</dependency>
```

- SWAGGER:
```XML
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.3.0</version>
   </dependency>
```

### Frontend Technology
> 🚧: Describe your views and what APIs is used on which view. If you don't have access to the Internet Technology class Budibase environment(https://inttech.budibase.app/), please write to Devid on MS teams.

TODO: START
TODO: END


This Web application was developed using Budibase and it is available for preview at https://inttech.budibase.app/app/pizzeria. 

## Execution
> 🚧: Please describe how to execute your app and what configurations must be changed to run it. 
TODO: START
beschreiben bettrefend onenote wie man es lokal zum laufen bringt. 
TODO: END
**The codespace URL of this Repo is subject to change.** Therefore, the Budibase PizzaRP webapp is not going to show any data in the view, when the URL is not updated or the codespace is offline. Follow these steps to start the webservice and reconnect the webapp to the new webservice url. 

> 🚧: This is a shortened description for example purposes. A complete tutorial will be provided in a dedicated lecture.
TODO: START
TODO: END
1. Clone PizzaRP in a new repository.
2. Start your codespace (see video guide at: [link](https://www.youtube.com/watch?v=_W9B7qc9lVc&ab_channel=GitHub))
3. Run the PizzaRP main available at PizzaApplication.java on your own codespace.
4. Set your app with a public port, see the guide at [link](https://docs.github.com/en/codespaces/developing-in-a-codespace/forwarding-ports-in-your-codespace).
5. Create an own Budibase app, you can export/import the existing Pizzeria app. Guide available at [link](https://docs.budibase.com/docs/export-and-import-apps).
6. Update the pizzeria URL in the datasource and publish your app.

### Deployment to a PaaS
> 🚧: Deployment to PaaS is optional but recommended as it will make your application (backend) accessible without server restart and through a unique, constantly available link.  
TODO: Start
Hier beschreiben dass es auf azure free läuft unter welchem link und auch wenn es ins main gepushed wird.
TODO: END
Alternatively, you can deploy your application to a free PaaS like [Render](https://dashboard.render.com/register).
1. Refer to the Dockerfile inside the application root (FHNW-INT/Pizzeria_Reference_Project/pizza).
2. Adapt line 13 to the name of your jar file. The jar name should be derived from the details in the pom.xml as follows:<br>
`{artifactId}-{version}.jar` 
2. Login to Render using your GitHub credentials.
3. Create a new Web Service and choose Build and deploy from a Git repository.
4. Enter the link to your (public) GitHub repository and click Continue.
5. Enter the Root Directory (name of the folder where pom.xml resides).
6. Choose the Instance Type as Free/Hobby. All other details are default.
7. Click on Create Web Service. Your app will undergo automatic build and deployment. Monitor the logs to view the progress or error messages. The entire process of Build+Deploy might take several minutes.
8. After successful deployment, you can access your backend using the generated unique URL (visible on top left under the name of your web service).
9. This unique URL will remain unchanged as long as your web service is deployed on Render. You can now integrate it in Budibase to make API calls to your custom endpoints.

## Project Management
> 🚧: Include all the participants and briefly describe each of their **individual** contribution and/or roles. Screenshots/descriptions of your Kanban board or similar project management tools are welcome.
TODO: Start
Hier noch was zur individualität schreiben?
TODO: End
### Roles
- Back-end developer: Matovic Nikola, Luca Masella
- Front-end developer: Jasin Jusufi, Silvan Rebmann

### Milestones
1. **Analysis**: Scenario ideation, use case analysis and user story writing.✅
2. **Prototype Design**: Creation of wireframe and prototype.✅
3. **Domain Design**: Definition of domain model.✅
4. **Business Logic and API Design**: Definition of business logic and API.
5. **Data and API Implementation**: Implementation of data access and business logic layers, and API.
6. **Security and Frontend Implementation**: Integration of security framework and frontend realisation.
7. (optional) **Deployment**: Deployment of Web application on cloud infrastructure.

#### Maintainer
- Nikola Matovic
- Luca Masella
- Jasin Jusufi
- Silvan Rebmann

#### License
- [Apache License, Version 2.0](blob/master/LICENSE)
=======
# Internet-Technology-GroupWork