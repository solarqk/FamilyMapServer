# Family Map Server

The Family Map server is a core component of the Family Map application, a tool that provides a geographical view of your family history. The server is a Java program and manages user accounts and family history data.

## Key Features
- User Authentication: Manages user login and registration, providing secure access to family history data.
- Family History Data Management: Generates and stores detailed family history data, including persons and events, in a relational database.
- Web APIs: Publishes a set of web APIs for client interaction, including endpoints for user management, data retrieval, and data manipulation.
- Data Persistence: Ensures that all data is stored in a database to prevent loss on server reboot.
- Automated Testing: Includes unit and integration tests to ensure reliability and correctness.

## Server Operations
- User Registration and Login: Handles the creation of user accounts and user authentication.
- Data Generation: Automatically generates family history data for users upon registration.
- Data Retrieval: Provides APIs to fetch detailed information about users, their family members, and significant life events.
- Data Management: Offers APIs for clearing the database, loading data, and filling user data with generated ancestors.
