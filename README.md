# Keep Dishes Going
Programming 6 University Assignment

**by Yiannis Ftiti**
31 October 2025

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![Keycloak](https://img.shields.io/badge/Keycloak-4D4D4D?style=for-the-badge&logo=keycloak&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

## Challenges

At the start, one of the toughest hurdles was understanding Domain Driven Design (DDD) and the **Hexagonal Architecture** approach. Getting comfortable with React’s syntax also took some time.

On top of that, time constraints were the biggest challenge, balancing multiple projects and deadlines was really stressful.


In any case, I'm aware it's not a perfect project, there are plenty of bugs and improvements that are needed, but even so, considering this has been done is less than 6 weeks, I believe it's not bad, and truthfully I enjoyed working on this project and learned a lot along the way, including willpower(like writing this readme file at 5am)

---

## Completed Features

### Owner Features
- Sign up / Sign in to access the restaurant management area.
- Create a restaurant with name, full address, contact email, photo(s) URL, default preparation time, cuisine type, and opening hours.
- Edit dishes in **draft mode** without affecting the live menu.
- **Publish / Unpublish** dishes.
- Apply all pending dish changes with a **single action**.
- Mark dishes **out of stock** or **back in stock** instantly.
- Accept or reject new orders (with rejection reasons).
- Automatically **decline orders after 5 minutes** if no decision is made.
- Mark accepted orders as **ready for pickup**.

### Customer Features
- Continue as a **guest customer** (without account).
- Explore restaurants in a **list** or **map view**.
- View detailed restaurant pages with their dishes.
- Filter restaurants by cuisine type, price range, distance, and estimated delivery time.
- Filter dishes by category (starter, main, dessert) and tags (vegan, etc.).
- Sort dishes.
- View **estimated delivery times** based on distance and restaurant busyness.
- Build a basket from a **single restaurant**.
- Prevent checkout if any dish becomes **unavailable or unpublished**.
- Enter name, delivery address, and contact email at checkout (unless already logged in).
- Choose a payment method.
- **Track order progress** as statuses update in real time.

### System Features
- Limit of **10 published dishes** per restaurant at any time.
- Publish delivery service messages when an order is accepted or ready for pickup.
- Consume delivery updates (picked up, delivered, courier location) to update order statuses.
- Enforce that **each owner manages exactly one restaurant**.

---

## Unfinished Features

### System
- Adjust the **price range criteria** dynamically.
- Track and visualize the **price range evolution** of restaurants over time.

### Restaurant
- Set **opening hours** and manually open/close the restaurant at any time.
- Schedule **publish/unpublish batches** to go live automatically at a chosen time.

### Customer
- Send **order confirmation links** so customers can return and track their order anytime.

---
# How to Run

### Backend

Start the infrastructure (PostgreSQL, RabbitMQ, Keycloak) using Docker:

```bash
cd backend/infrastructure
docker-compose up -d
```

Then run the Spring Boot application:

```bash
cd backend
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`.

### Frontend

Install dependencies and start the dev server:

```bash
cd frontend
npm install
npm run dev
```

The frontend will start on `http://localhost:5173`.

---

Thank you for your time.
