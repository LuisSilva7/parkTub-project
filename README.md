# ParkTub - Urban Transport Parking System

ParkTub is a mobile platform designed to simplify parking for users of Transportes Urbanos de Braga (TUB). It provides real-time parking availability updates and integrated payments offering an efficient parking experience.

## Features

- **Real-Time Parking Lot Updates**: Parking lot availability is dynamically updated through integration with Niop, Kafka and SSE (Server-Sent Events), ensuring users always have the latest information on available spots.
- **Parking Session Management**: Users can manage their parking sessions easily, including creating new sessions (check-in), viewing ongoing sessions, and finalizing sessions (check-out).
- **Payment Management**: Provides a comprehensive overview of all user payments, allowing them to view details, make payments, and track their payment history.
- **Bonus Rewards**: Users can access and activate available bonuses, redeeming their points for rewards and benefits seamlessly.
- **Mobile Design**: Access the platform on smartphones for greater user experience.

## Technologies Used

- **Frontend**:
  - **React** - JavaScript library for building user interfaces.
  - **HTML** - Structure of the web application.
  - **CSS** - For styling the website and creating a responsive design.
  - **JavaScript** - Used for client-side logic and interactivity.
  - **Google Maps API** - For displaying real-time available parking spots on a map and assisting users in locating them.

- **Backend**:
  - **Microservices Architecture** - The backend is structured into independent services, promoting scalability and fault tolerance.
  - **Docker**: A containerization platform used to package and deploy the application in lightweight, isolated environments, ensuring consistency across different environments.
  - **Java** - Programming language used for backend development.
  - **Spring Boot**: Framework for building the backend API and handling HTTP requests.
  - **Spring Data JPA**: Simplifies database interaction by providing an abstraction layer for managing data persistence.
  - **Spring Validation**: A framework used for validating input data, ensuring that the data received by the application meets the defined rules.
  - **Spring Cloud Discovery**: Provides tools for deploying and managing distributed systems.
  - **Spring Cloud Config**: Centralized configuration management for microservices.
  - **MySQL** - Relational database for storing parking, payment, and user data.
  - **Stripe** - Payment processing system for handling parking payments.
  - **Niop** - A low-code platform that simplifies the orchestration of intelligent systems, enabling easy programming of machines integrating AI, robotics, and automation.
  - **Kafka** - A distributed messaging system designed for high-throughput, real-time data streaming and synchronization, ensuring accurate updates of parking space availability.
  - **SSE (Server-Sent Events)**: Establishes a one-way connection from the server to the client, allowing real-time updates (e.g., parking lot availability) directly to the client over HTTP.
  - **Zipkin**: A distributed tracing system that helps monitor and troubleshoot the performance of microservices, providing detailed insights into latency and inter-service communication.

## How to Run the Project Locally

### Installation Steps

1. **Clone the repository:**

   ```bash
   git clone https://github.com/LuisSilva7/parkTub-project.git
   ```

2. **Navigate to the project backend directory:**

   ```bash
   cd parkTub-project/parkTub-backend
   ```

3. **Run all containers:**

   ```bash
   docker compose up -d
   ```

4. **Run the backend server:**

   config-server -> discovery -> customer -> parking -> bonus -> payment -> car-count-producer -> gateway

5. **Correct Google Maps usage:**
   You will need to create .env file with VITE_google_maps_api_key=key

6. **Stripe correct usage:**
   In order to use Stripe, you need to setup the api key in application.properties file.

7. **Navigate to the project frontend directory:**

   ```bash
   cd ../parkTub-frontend
   ```

8. **Install project dependencies:**

   ```bash
   npm install
   ```

9. **Start the development server:**
   ```bash
   npm run dev
   ```
10. **Clear the LocalStorage:**
    Clear your LocalStorage to make sure everything is working.

The application will be available at [http://localhost:5173](http://localhost:5173) in your browser.

## Screenshots

### Home Page

<img src="screenshots/home.jpg" alt="Home Page" height="550">

### Home2 Page

<img src="screenshots/home2.jpg" alt="Home2 Page" height="550">

### Bonus Page

<img src="screenshots/bonus.jpg" alt="Bonus Page" height="550">

### Payments Page

<img src="screenshots/payments.jpg" alt="Payments Page" height="550">

### Stripe Page

<img src="screenshots/stripe.jpg" alt="Stripe Page" height="550">

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.

### Maintainer

- **Luis Silva** (Owner/Developer)
