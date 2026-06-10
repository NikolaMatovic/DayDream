# DayDream

DayDream is a web application to create, manage, and share daydreams.

Users can:
- sign up and log in
- create, update, and delete their own daydreams
- choose public or private visibility
- comment on daydreams
- access an admin page (admin role) for moderation tasks

## Contents

- [Project Context](#project-context)
- [Team](#team)
- [Scenario](#scenario)
- [User Stories](#user-stories)
- [Domain Model](#domain-model)
- [Use Case Overview](#use-case-overview)
- [Architecture](#architecture)
- [Implemented Views](#implemented-views)
- [Wireframes](#wireframes)
- [Business Logic](#business-logic)
- [API Summary](#api-summary)
- [Security](#security)
- [Implementation](#implementation)
- [Execution](#execution)
- [Demo Credentials](#demo-credentials-seed-data)
- [Project Management](#project-management)
- [Links](#links)
- [License](#license)

## Project Context

This project was developed for the FHNW Internet Technology group work assessment.

## Scenario

The platform enables users to store personal ideas and optionally share them publicly.
A user can keep daydreams private, publish selected content, and interact with the community through comments.

## User Stories

### Core user stories
1. As a user, I want to register so that I can create my own account.
2. As a user, I want to log in so that I can access protected features.
3. As a user, I want to browse public daydreams so that I can discover content.
4. As a user, I want to create, edit, and delete my own daydreams.
5. As a user, I want to choose visibility (public/private) for each daydream.
6. As a user, I want to comment on daydreams.
7. As an admin, I want to manage users and daydreams.

## Domain Model

Main entities:
- User
- Daydream
- Comment
- Tag
- Visibility (enum)

Domain diagram:

![Class Diagram](images/class-diagram.png)

## Use Case Overview

![Use Case Diagram](images/use-case.png)

## Architecture

The solution follows a layered architecture over two tiers:

- Frontend tier
  - React + Vite
  - route-based views and API consumption

- Backend tier
  - Spring Boot 3 + Java 17
  - Controller layer
  - Service layer (business rules)
  - Repository layer (JPA)
  - H2 database

## Implemented Views

- Landing page
- Login page
- Signup page
- Feed page
- Create daydream page
- Daydream detail page
- Profile page
- Admin page

## Wireframes

### 01 Landing
![Landing Wireframe](images/wireframes/01_landing_page.png)

### 02 Login
![Login Wireframe](images/wireframes/02_login_page.png)

### 03 Signup
![Signup Wireframe](images/wireframes/03_signup_page.png)

### 04 Protected Shell
![Shell Wireframe](images/wireframes/04_shell_page.png)

### 05 Feed
![Feed Wireframe](images/wireframes/05_feed_page.png)

### 06 Create Daydream
![Create Daydream Wireframe](images/wireframes/06_create_daydream_page.png)

### 07 Daydream Detail
![Daydream Detail Wireframe](images/wireframes/07_daydream_detail_page.png)

### 08 Profile
![Profile Wireframe](images/wireframes/08_profile_page.png)

### 09 Admin
![Admin Wireframe](images/wireframes/09_admin_page.png)

### 10 Navigation Map
![Navigation Map](images/wireframes/10_navigation_map.png)

## Responsive Design

The application is designed to work on both desktop and mobile devices. The following mobile screenshot shows the create daydream view on a narrow screen, including responsive spacing, stacked form fields, and the compact mobile navigation.

![Responsive Mobile Screenshot](images/create_mobile.PNG)

## Business Logic

The service layer enforces the following business rules that reflect real application constraints:

### Rule 1: Ownership enforcement on update and delete

A user can only update or delete a daydream they own. The `DaydreamService` checks the authenticated username against `daydream.getUser().getUsername()` before any mutating operation. Unauthorized access throws a `SecurityException`, resulting in HTTP 403.

**Endpoint:** `PUT /v1/dreams/{id}` and `DELETE /v1/dreams/{id}`  
**Method:** `PUT` / `DELETE`  
**Constraint:** Caller username must match the daydream owner.

### Rule 2: Admin-only moderation

Only users with role `ADMIN` may delete any daydream (including private ones) or delete user accounts. Enforced via Spring Security `@PreAuthorize("hasRole('ADMIN')")` on the `AdminController`.

**Endpoint:** `DELETE /v1/admin/dreams/{id}` and `DELETE /v1/admin/users/{id}`  
**Method:** `DELETE`  
**Constraint:** Caller must carry role `ADMIN`.

### Rule 3: Visibility filtering

Public daydreams are accessible to any authenticated user. Private daydreams are only returned via the personal feed endpoint scoped to the authenticated user. `DaydreamService.getDaydreamsByVisibility(Visibility.PUBLIC)` ensures private content is never exposed through the public feed.

**Endpoint:** `GET /v1/dreams/public`  
**Method:** `GET`  
**Constraint:** Only `visibility=PUBLIC` daydreams are returned.

The full Swagger/OpenAPI documentation is available at:  
[https://meinewebbapp-nidzo.azurewebsites.net/swagger-ui.html](https://meinewebbapp-nidzo.azurewebsites.net/swagger-ui.html)

## API Summary

### Authentication
- POST /v1/auth/signup
- POST /v1/auth/login

### Daydreams
- GET /v1/dreams/public
- GET /v1/dreams/my
- GET /v1/dreams/all
- POST /v1/dreams
- PUT /v1/dreams/{id}
- DELETE /v1/dreams/{id}
- POST /v1/dreams/{daydreamId}/comments

### Admin
- GET /v1/admin/users
- DELETE /v1/admin/users/{id}
- GET /v1/admin/dreams
- DELETE /v1/admin/dreams/{id}

## Security

The backend includes role-based authorization with protected admin endpoints.
Current project version uses JWT-based bearer authentication for API access.

## Implementation

The application follows a three-layer, two-tier architecture:

- **Frontend** (React + Vite): Single-page application consuming REST APIs. Route-based views, protected routes, role-based navigation, and local JWT token handling.
- **Controller layer** (Spring Boot): REST endpoints for auth, daydreams, comments, users, and admin operations. Input validation at the API boundary.
- **Service layer** (Spring Boot): Business rule enforcement, ownership checks, visibility filtering.
- **Repository layer** (Spring Data JPA): Database access via JPA repositories.
- **Database** (H2 in-memory): Seeded on startup with demo users and daydream content.

The frontend communicates with the backend exclusively through REST API calls. Authentication uses JWT bearer tokens — the token is stored locally after login and attached to every subsequent API request.

The full-code frontend approach (React instead of a low-code tool) was chosen because the required functionality — protected routes, role-based rendering, and inline edit composer — could not be achieved with a low-code tool without significant limitations.

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.2
- Spring Web
- Spring Data JPA
- Spring Security
- Spring OAuth2 Resource Server
- Springdoc OpenAPI
- H2 database

### Frontend
- React 18
- React Router
- Vite
- CSS

## Run Locally

### Prerequisites
- Java 17
- Node.js 18+ (recommended)
- npm

### Backend
From _backend:

1. Build and run with Maven wrapper
   - ./mvnw spring-boot:run

or

2. Build and run with Docker
   - docker build -t daydream-backend .
   - docker run -p 8080:8080 daydream-backend

### Frontend
From _frontend:

1. Install dependencies
   - npm install
2. Start development server
   - npm run dev

## Execution

### Running app (deployed)

- Web application: [https://meinewebbapp-nidzo.azurewebsites.net/](https://meinewebbapp-nidzo.azurewebsites.net/)
- Swagger UI: [https://meinewebbapp-nidzo.azurewebsites.net/swagger-ui.html](https://meinewebbapp-nidzo.azurewebsites.net/swagger-ui.html)

### Running locally

**Frontend**
```bash
cd _frontend
npm install
npm run dev
```
Dev server starts at `http://localhost:5173`.

**Backend**
```bash
cd _backend
docker build -t my-image .
docker run -p 8080:8080 my-image
```
Backend API runs at `http://localhost:8080`.  
Swagger UI available at `http://localhost:8080/swagger-ui.html`.

The H2 database is in-memory and automatically seeded with demo users and daydreams on startup.

## Demo Credentials (Seed Data)

- admin / admin1234
- additional demo users are seeded in backend startup initialization

## Links

- Running app: https://meinewebbapp-nidzo.azurewebsites.net/
- OpenAPI (Swagger UI): https://meinewebbapp-nidzo.azurewebsites.net/swagger-ui.html
- OpenAPI JSON: https://meinewebbapp-nidzo.azurewebsites.net/v3/api-docs
- Presentation video: not yet ready

## Deliverables Status

- Source code: available in this repository
- Documentation: this README
- Running demonstrator: available via link above
- Video presentation: pending

## Project Management

### Roles

| Member | Primary Role |
|---|---|
| Nikola Matovic | Architect, cloud engineering, backend-frontend integration |
| Luca Masella | Design, business case framing, requirements |
| Jasin Jusufi | Backend implementation, API endpoints, service layer |
| Silvan Rebmann | Frontend implementation, domain model, UI/UX |

### Milestones

| # | Milestone | Status |
|---|---|---|
| 1 | Analysis: scenario ideation, use case analysis, user story writing | ✅ |
| 2 | Domain Design: definition of domain model | ✅ |
| 3 | Frontend implementation: design, prototyping and realization | ✅ |
| 4 | Business Logic and API Design: definition of business logic and API | ✅ |
| 5 | Data and API implementation: data access and business logic layers | ✅ |
| 6 | Security: API-level security with role-based authorization | ✅ |
| 7 | Demonstrator: end-to-end application consuming REST APIs | ✅ |


## Team

- Nikola Matovic: defined the overall technical architecture, coordinated backend-frontend integration, handled deployment setup, and managed cloud environment configuration.
- Luca Masella: led design decisions for user journeys and business case framing, contributed to requirement structuring, and supported validation of implemented features against use cases.
- Jasin Jusufi: implemented core backend functionality, including API endpoints, service-layer logic, and data-access integration in the Spring Boot application.
- Silvan Rebmann: implemented frontend views and interaction flow, contributed to domain-model alignment with UI behavior, and supported end-to-end testing of user-facing features.

All members participated in planning, reviews, testing, and iterative improvements across both frontend and backend.

## License

Apache License 2.0. See LICENSE.
