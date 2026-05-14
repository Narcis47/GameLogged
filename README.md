# 🎮 GameLogged

A personal game library tracker — log games you're playing, completed, or want to play, and rate them.

Built with Java & Spring Boot on the backend, powered by the [RAWG API](https://rawg.io/apidocs) for game data.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.2.5 |
| Database | PostgreSQL |
| Security | Spring Security + BCrypt |
| Game Data | RAWG REST API |
| Build Tool | Maven |

---

## Features

- Register and manage users
- Search games via RAWG API (500k+ games)
- Add games to your personal library
- Track status: `PLAYING`, `COMPLETED`, `DROPPED`, `WISHLIST`
- Rate games from 1–10
- Write reviews

---

## Project Structure

```
src/main/java/com/narcis/gamelogged/
├── controller/        ← REST API endpoints
│   ├── UserController.java
│   ├── GameController.java
│   └── UserGameController.java
├── service/           ← Business logic
│   ├── UserService.java
│   ├── GameService.java
│   ├── UserGameService.java
│   └── RawgService.java
├── repository/        ← Database access
│   ├── UserRepository.java
│   ├── GameRepository.java
│   └── UserGameRepository.java
├── model/             ← Database entities
│   ├── User.java
│   ├── Game.java
│   └── UserGame.java
└── dto/               ← Data Transfer Objects
    └── RawgGameDto.java
```

---

## API Endpoints

### Users
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users/register` | Register a new user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |

### Games
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/games/add` | Add a game by RAWG ID |
| GET | `/api/games` | Get all games |
| GET | `/api/games/{id}` | Get game by ID |
| GET | `/api/games/rawg/{rawgId}` | Fetch game details from RAWG |

### Library
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/library/add` | Add game to user's library |
| GET | `/api/library/{userId}` | Get user's game library |
| PUT | `/api/library/{id}/status` | Update game status |
| PUT | `/api/library/{id}/rating` | Update game rating |
| DELETE | `/api/library/{id}` | Remove game from library |

---

## Database Schema

```sql
users        → id, username, email, password_hash, created_at
games        → id, rawg_id
user_games   → id, user_id, game_id, status, rating, review, created_at
```

---

## Setup & Installation

### Prerequisites
- Java 21
- PostgreSQL
- Maven

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/Narcis47/GameLogged.git
cd GameLogged
```

**2. Create the database**
```sql
CREATE DATABASE game_library;
```

**3. Run the schema**
```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE games (
    id SERIAL PRIMARY KEY,
    rawg_id INTEGER UNIQUE NOT NULL
);

CREATE TABLE user_games (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    game_id INTEGER REFERENCES games(id),
    status VARCHAR(20) CHECK (status IN ('PLAYING', 'COMPLETED', 'DROPPED', 'WISHLIST')),
    rating INTEGER CHECK (rating BETWEEN 1 AND 10),
    review TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(user_id, game_id)
);
```

**4. Set environment variables**
```bash
DB_USERNAME=your_postgres_username
DB_PASSWORD=your_postgres_password
RAWG_API_KEY=your_rawg_api_key
```

Get a free RAWG API key at [rawg.io/apidocs](https://rawg.io/apidocs)

**5. Run the application**
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## Example Requests

**Register a user**
```json
POST /api/users/register
{
    "username": "narcis",
    "email": "narcis@example.com",
    "password": "securepassword"
}
```

**Add a game to library**
```json
POST /api/library/add
{
    "userId": 1,
    "gameId": 1,
    "status": "PLAYING"
}
```

**Update rating**
```json
PUT /api/library/1/rating
{
    "rating": 9
}
```

---

## Author

**Narcis** — [@Narcis47](https://github.com/Narcis47)
