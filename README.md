# ChatApp

An elegant Android messaging application built with Java and AI-powered chatbot using Google Gemini API.

## Features

- **User Authentication**
  - Secure registration with email validation
  - Login system with SHA-256 password hashing
  - Session management

- **AI Chatbot**
  - Powered by Google Gemini API
  - Real-time AI conversations
  - Context-aware responses

- **Messaging**
  - Real-time group chat functionality
  - Message history persistence using SQLite
  - Clean message bubble UI

- **User Management**
  - User profiles with bio and status
  - Group admin capabilities
  - Add/remove users from groups

## Tech Stack

- **Language:** Java
- **Platform:** Android (API 31+)
- **Database:** SQLite
- **AI:** Google Gemini API
- **HTTP Client:** OkHttp
- **Architecture:** MVC Pattern

## Project Structure

```
app/src/main/java/
├── com/example/chatapp/       # Activity classes
│   ├── LoginActivity.java
│   ├── RegisterActivity.java
│   ├── MainActivity.java
│   ├── ChatActivity.java
│   ├── BotChatActivity.java    # AI Chat screen
│   ├── BotMessageAdapter.java  # AI message adapter
│   └── MessageAdapter.java
├── model/                    # Data models
│   ├── User.java
│   ├── Message.java
│   ├── ChatBot.java            # AI chat logic
│   └── ...
├── service/                  # Business logic
│   ├── AuthService.java
│   ├── ChatService.java
│   └── GeminiService.java      # Gemini API integration
└── ...
```

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 11 or higher
- Android SDK 31+
- Google Gemini API Key

### API Key Setup

1. Get your API key from [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Open `BotChatActivity.java`
3. Replace `YOUR_GEMINI_API_KEY_HERE` with your actual API key

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/zehraakucuk13w/Chat-App.git
   ```

2. Open the project in Android Studio

3. Add your Gemini API key (see above)

4. Sync Gradle dependencies

5. Run the app on an emulator or physical device

## Screenshots

| Login Screen | Main Screen | AI Chat |
|:------------:|:-----------:|:-------:|
| Login with username and password | Navigate to features | Chat with AI |

## Security Features

- Passwords are hashed using SHA-256 before storage
- Input validation for usernames and emails
- Secure session handling

## Author

GitHub: [@zehraakucuk13w](https://github.com/zehraakucuk13w)
