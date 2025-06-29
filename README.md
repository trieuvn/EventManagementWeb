# FESTIVO - Online Event Management System

## Overview
FESTIVO is a web-based platform designed to streamline the management and registration of online and hybrid events. Developed as a final project for the Java Technology course (ITE1219E), this system supports two main user roles: **Participants** and **Administrators**. It provides a user-friendly interface, robust event management features, and advanced integrations for a seamless user experience.

## Key Features
- **User Management**: Registration, login, profile updates, and password recovery via OTP.
- **Event Management**: Create, update, delete, and browse events with filtering by name, category, or date.
- **Event Registration**: Users can register for events, receive confirmation codes (text/QR), and cancel registrations.
- **Check-in System**: QR code-based check-in with manual or camera-based input for administrators.
- **Feedback System**: Participants can rate events (1-5 stars) post-check-in.
- **Multilingual Support**: Interface available in English and Vietnamese.
- **Map Integration**: Displays event locations using MapTiler Cloud API.
- **Email Notifications**: Sends registration confirmations and event updates via JavaMail API.
- **Admin Tools**: Manage event categories, participant lists, and generate/export statistical reports.

## Technologies Used
- **Backend**: Java Spring MVC, Maven, Apache Tomcat
- **Frontend**: HTML, CSS, JavaScript, Bootstrap
- **Database**: SQL Server with ORM for data management
- **APIs and Libraries**:
  - MapTiler Cloud API for location display
  - ![image](https://github.com/trieuvn/EventManagementWeb/blob/main/map.png?raw=true)
  - JavaMail API for email notifications
  - Live2D for interactive assistant on the homepage
  - ![image](https://github.com/trieuvn/EventManagementWeb/blob/main/home.png?raw=true)
- **Version Control**: Git (hosted on GitHub)

## System Architecture
FESTIVO follows the **Model-View-Controller (MVC)** architecture to ensure modularity and scalability. The system includes:
- **Database**: A relational database with tables for Events, Users, Participants, Tickets, Organizers, Locations, Categories, Tags, and Changes.
- **Business Logic**: Handles event creation, registration, check-in, and reporting with strict business rules (e.g., unique email, valid QR codes).
- **Frontend**: Responsive design for cross-device compatibility and multilingual support.

## Installation and Setup
### Prerequisites
- Java Development Kit (JDK) 20 or higher
- Apache Tomcat 9.x
- SQL Server
- Maven
- Git

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/trieuvn/EventManagementWeb.git
   ```

2. **Configure Database**:
   - Set up a SQL Server database.
   - Update the database connection settings in `src\main\webapp\WEB-INF\dispatcher-servlet.xml`:
     ```properties
     <property name="driverClassName" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" />
     <property name="url" value="jdbc:sqlserver://localhost:1433;databaseName=EventManagement;encrypt=true;trustServerCertificate=true" />
     <property name="username" value="sa" />
     <property name="password" value="123" />
     ```

3. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

4. **Configure APIs**:
   - Obtain a MapTiler Cloud API key and update it in the configuration file.
   - Configure JavaMail SMTP settings in `dispatcher-servlet.xml` for email notifications.

5. **Run the Application**:
   - Deploy the project to Apache Tomcat or run it using:
     ```bash
     mvn spring-boot:run
     ```
   - Access the application at `http://localhost:8080`.

## Usage
- **Participants**:
  - Browse events on the homepage, filter by category or date, and register for events.
  - Use the generated QR code for check-in and rate events after participation.
- **Administrators**:
  - Log in to access the admin dashboard for event management, participant check-in, and report generation.
  - Export reports as PDF or Excel for analysis.
  - ![image](https://github.com/trieuvn/EventManagementWeb/blob/main/home.png?raw=true)

## Database Schema
The system uses a relational database with the following key tables:
- **EVENT**: Stores event details (name, description, type, organizer).
- **USER**: Manages user accounts (email, password, role).
- **PARTICIPANT**: Tracks event registrations and check-in status.
- **TICKET**: Manages ticket details (price, slots, QR code).
- **LOCATION**: Stores event location data (latitude, longitude).
- **ORGANIZER**, **CATEGORY**, **TAG**, **CHANGE**: Support event categorization and updates.

For a detailed ERD, refer to the [project report](#).

## Future Improvements
- **Online Payment**: Integrate payment gateways for ticketed events.
- **AI Recommendations**: Implement machine learning for personalized event suggestions.
- **Advanced Analytics**: Add real-time dashboards with heatmaps and trend analysis.

## References
- [MapTiler Cloud API Documentation](https://docs.maptiler.com/cloud/api/)
- [JavaMail API Documentation](https://javaee.github.io/javamail/docs/api/)
- [Live2D Widget](https://github.com/stevenjoezhang/live2d-widget)

## License
This project is licensed under the MIT License.
