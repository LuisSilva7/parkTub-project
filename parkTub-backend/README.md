# Backend

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

4. **Run backend server:**

   ```bash
   mvn spring-boot:run
   ```

5. **Stripe correct usage:**
   In order to use Stripe, you need to setup the api key in configurations/payment-service.yml file.
