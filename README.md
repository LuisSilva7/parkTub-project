# TUB Parking - Urban Transport Parking System

**TUB Parking** is an online platform designed for managing parking spaces, payments, and discounts for users of the Transportes Urbanos de Braga (TUB) service. Users can view available parking spaces, apply for discounts/bonuses, and manage their parking payments seamlessly. The project is split into two main parts: the **Frontend** (the user interface) and the **Backend** (the API for handling parking data and payments).

## Features

- **Real-Time Parking Lot Updates** - Parking lot availability is dynamically updated through seamless integration with Niop and Kafka, ensuring users always have the latest information on available spots.
- **Live Parking Lot Details**: Users can view detailed information about all parking lots, with real-time updates reflecting changes as they happen.
- **Parking Session Management**: Users can manage their parking sessions with ease, including creating new sessions (check-in), viewing ongoing sessions, and finalizing sessions (check-out).
- **Payment Management**: Provides a comprehensive overview of all user payments, allowing them to view details, make payments, and track their payment history.
- **Bonus Rewards**: Users can access and activate available bonuses, redeeming their points for rewards and benefits seamlessly.

## Technologies Used

- **Frontend**:

  - **React** - JavaScript library for building user interfaces.
  - **HTML** - Structure of the web application.
  - **CSS** - For styling the website and creating a responsive design.
  - **JavaScript** - Used for client-side logic and interactivity.
  - **Google Maps API** - For displaying real-time available parking spots on a map and assisting users in locating them.

- **Backend**:
  - **Microservices Architecture** - The backend is structured into independent services, promoting scalability and fault tolerance.
  - **Docker** - A containerization platform used to package and deploy the application in lightweight, isolated environments, ensuring consistency across different environments and simplifying the deployment process.
  - **Java** - Programming language used for backend development.
  - **Spring Framework** - A comprehensive framework for building the backend API with a focus on simplicity, productivity, and robust data handling.
  - **MySQL** - Relational database for storing parking, payment, and user data.
  - **Stripe** - Payment processing system for handling parking payments.
  - **Niop** - A low-code platform that simplifies the orchestration of intelligent systems, enabling easy programming of machines integrating AI, robotics, and automation.
  - **Kafka** - A distributed messaging system designed for high-throughput, real-time data streaming and synchronization, ensuring accurate updates of parking space availability.
  - **SSE (Server-Sent Events)** - A technology used to establish a one-way connection from the server to the client, allowing the server to send real-time updates (e.g., parking lot availability) directly to the client over HTTP.
  - **Zipkin** - A distributed tracing system that helps monitor and troubleshoot the performance of microservices, providing detailed insights into latency and inter-service communication.
