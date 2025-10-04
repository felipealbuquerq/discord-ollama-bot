# Discord Ollama Bot

A Spring Boot Discord bot that integrates with Ollama to deliver real-time, AI-powered responses using local Large Language Models (LLMs). This project is designed for easy deployment with Docker and Docker Compose, and includes support for model switching via Discord commands.

## Features

- **Ollama Integration**: Connects to a local Ollama server to run LLMs.
- **Model Selection**: Use Discord commands to choose between models:
  - `!ai:r1 <your question>` for DeepSeek-R1:7b
  - `!ai:coder <your question>` for deepseek-coder:6.7b
  - `!ai:amy-coder <your question>` for Amy, the Think A.M. AI Coder.
  - `!ai:amy-thinkam <your question>` for Amy, the Think A.M. AI.
- **Fast Responses**: Get AI answers in real time.
- **Easy Deployment**: Ready-to-use Docker and Docker Compose setup.
- **Unit Tests**: Example test cases are included.
- **Full Documentation**: Usage and setup instructions are provided.

## Getting Started

### Prerequisites

- Docker
- Docker Compose
- Discord Bot Token

### Clone the Repository

```bash
git clone [https://github.com/felipealbuquerq/discord-ollama-bot.git](https://github.com/felipealbuquerq/discord-ollama-bot.git)
cd discord-ollama-bot

### Environment Variables

Create a `.env` or `application.properties` file in the project root with:

```
DISCORD_TOKEN=your_discord_token_here
```

### Run with Docker Compose

This will start both the Ollama server and the Discord bot:

- for .env file:
```bash
docker-compose up --build
```

- case you created application.properties:
```bash
./gradlew clean build
```


### Run Locally (Development)

Start your local Ollama server, then run:

```bash
./gradlew bootRun
```

## Usage

- In any Discord channel where the bot is present, type:
  - `!ia:r1 <your question>` for DeepSeek-R1:7b
  - `!ia:coder <your question>` for deepseek-coder:6.7b

## File Structure

```
discord-ollama-bot/
│
├── Dockerfile
├── docker-compose.yml
├── src/
│   ├── main/java/... (bot source code)
│   └── test/java/... (unit tests)
├── README.md
└── .application.properties
```

## Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](LICENSE)

## Author

Felipe Albuquerque ([GitHub](https://github.com/felipealbuquerq))