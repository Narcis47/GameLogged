# 🎮 GameLogged

A personal game library tracker — search for games, add them to your library, track your progress and rate them.

Built with Java & Spring Boot on the backend and vanilla HTML/CSS/JavaScript on the frontend, powered by the [RAWG API](https://rawg.io/apidocs) for game data.


---

## ✨ Features

- 🔐 User registration and login with BCrypt password hashing
- 🔍 Search 500,000+ games via RAWG API with cover images
- 📚 Personal game library per user
- 🏷️ Track game status: `PLAYING`, `COMPLETED`, `DROPPED`, `WISHLIST`
- ⭐ Rate games from 1–10
- 🗑️ Remove games from library
- 🎨 Dark theme UI with purple accent

---

## 🖥️ Screenshots

### Library
> Games displayed in a grid with cover images, status badges and ratings
![My Library](https://i.imgur.com/bQ325A9.png)
### Search
> Search any game and add it to your library in one click
![Search list](https://i.imgur.com/hxBdOX0.png)

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.2.5 |
| Database | PostgreSQL |
| Security | Spring Security + BCrypt |
| HTTP Client | RestTemplate |
| Game Data API | RAWG REST API |
| Build Tool | Maven |
| Frontend | HTML, CSS, JavaScript (Vanilla) |

---

## 📁 Project Structure

```
gamelogged/
├── src/main/java/com/narcis/gamelogged/
│   ├── controller/
│   │   ├── UserController.java       ← /api/users
│   │   ├── GameController.java       ← /api/games
│   │   └── UserGameController.java   ← /api/library
│   ├── service/
│   │   ├── UserService.java
│   │   ├── GameService.java
│   │   ├── UserGameService.java
│   │   └── RawgService.java          ← RAWG API integration
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── GameRepository.java
│   │   └── UserGameRepository.java
│   ├── model/
│   │   ├── User.java
│   │   ├── Game.java
│   │   └── UserGame.java
│   ├── dto/
│   │   ├── RawgGameDto.java
│   │   └── RawgSearchDto.java
│   └── SecurityConfig.java
└── frontend/
    ├── index.html      ← Game library page
    ├── login.html      ← Login page
    ├── register.html   ← Register page
    ├── style.css       ← Shared styles
    ├── index.js        ← Library logic
    ├── login.js        ← Login logic
    └── register.js     ← Register logic
```

---

## 🔌 API Endpoints

### Users
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Login |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |

### Games
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/games/add` | Add a game by RAWG ID |
| GET | `/api/games` | Get all games |
| GET | `/api/games/{id}` | Get game by ID |
| GET | `/api/games/{id}/rawg` | Get RAWG data for a game |
| GET | `/api/games/rawg/{rawgId}` | Fetch game details from RAWG |
| GET | `/api/games/search?query=` | Search games via RAWG |

### Library
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/library/add` | Add game to user's library |
| GET | `/api/library/{userId}` | Get user's game library |
| PUT | `/api/library/{id}/status` | Update game status |
| PUT | `/api/library/{id}/rating` | Update game rating |
| DELETE | `/api/library/{id}` | Remove game from library |

---

## 🗄️ Database Schema

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

---

## 🚀 Setup & Installation

### Prerequisites
- Java 21
- PostgreSQL
- Maven
- RAWG API key (free at [rawg.io/apidocs](https://rawg.io/apidocs))

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/Narcis47/GameLogged.git
cd GameLogged
```

**2. Create the PostgreSQL database**
```sql
CREATE DATABASE game_library;
```

**3. Run the schema** (copy the SQL from above into your database client)

**4. Set environment variables**

In IntelliJ → Run/Debug Configurations → Environment Variables:
```
DB_USERNAME=your_postgres_username
DB_PASSWORD=your_postgres_password
RAWG_API_KEY=your_rawg_api_key
```

**5. Run the backend**
```bash
./mvnw spring-boot:run
```

**6. Open the frontend**

Open `frontend/index.html` with Live Server (VS Code) or any static file server.

The API runs at `http://localhost:8080`

---

## 📝 Example API Requests

**Register**
```json
POST /api/users/register
{
    "username": "narcis",
    "email": "narcis@example.com",
    "password": "securepassword"
}
```

**Login**
```json
POST /api/users/login
{
    "email": "narcis@example.com",
    "password": "securepassword"
}
```

**Search games**
```
GET /api/games/search?query=witcher
```

**Add game to library**
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

## 👤 Author

**Narcis** — [@Narcis47](https://github.com/Narcis47)