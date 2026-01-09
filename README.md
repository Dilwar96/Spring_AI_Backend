# SpringAI Backend

A Spring Boot application that integrates with OpenAI's API to provide AI-powered services including chat, image generation, and recipe creation.

## Features

- **AI Chat** - Ask questions and get responses from GPT-4
- **Image Generation** - Generate images using DALL-E with customizable count
- **Recipe Generation** - Create recipes based on ingredients and dietary restrictions
- **Docker Support** - Ready for deployment on Render or any Docker-compatible platform
- **CORS Enabled** - Frontend communication enabled

## Tech Stack

- **Framework**: Spring Boot 3.5.9
- **Language**: Java 21
- **Build Tool**: Maven
- **AI Integration**: Spring AI + OpenAI API
- **Containerization**: Docker

## Prerequisites

- Java 21+
- Maven 3.9+
- OpenAI API Key
- Docker (optional, for containerization)

## Project Structure

```
Backend/
├── src/
│   ├── main/
│   │   ├── java/com/ai/SpringAI/
│   │   │   ├── SpringAiApplication.java       # Main application entry
│   │   │   ├── config/
│   │   │   │   └── WebConfig.java             # CORS & web configuration
│   │   │   ├── controllers/
│   │   │   │   └── GenAIController.java       # API endpoints
│   │   │   └── services/
│   │   │       ├── ChatService.java           # Chat logic
│   │   │       ├── ImageService.java          # Image generation logic
│   │   │       └── RecipeService.java         # Recipe generation logic
│   │   └── resources/
│   │       └── application.properties         # Configuration
│   └── test/
│       └── SpringAiApplicationTests.java
├── pom.xml                                    # Maven dependencies
├── Dockerfile                                 # Docker build configuration
├── .dockerignore                              # Docker ignore rules
├── render.yaml                                # Render deployment config
└── DEPLOYMENT.md                              # Deployment guide
```

## Setup Instructions

### 1. Clone Repository

```bash
git clone <your-repo-url>
cd Backend
```

### 2. Configure Environment Variables

Create or update `src/main/resources/application.properties`:

```properties
spring.application.name=SpringAI
spring.ai.openai.api-key=sk-your-api-key-here
```

**Or set via environment variable:**

```bash
export SPRING_AI_OPENAI_API_KEY=sk-your-api-key-here
```

### 3. Build the Application

```bash
mvn clean package
```

### 4. Run Locally

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Chat Endpoint

**Request:**
```bash
GET http://localhost:8080/ask-ai?prompt=What%20is%20AI?
```

**Response:**
```json
"AI is artificial intelligence..."
```

### Image Generation Endpoint

**Request:**
```bash
GET http://localhost:8080/generate-images?prompt=A%20sunny%20beach&n=4&width=1024&height=1024
```

**Parameters:**
- `prompt` (required) - Image description
- `n` (optional, default=1) - Number of images (1-10)
- `width` (optional, default=1024) - Image width
- `height` (optional, default=1024) - Image height

**Response:**
```json
[
  "https://oaidalleapiprodscus.blob.core.windows.net/...",
  "https://oaidalleapiprodscus.blob.core.windows.net/..."
]
```

### Recipe Endpoint

**Request:**
```bash
GET http://localhost:8080/recipe-creator?ingredients=chicken,tomato&cuisine=Italian&dietaryRestrictions=none
```

**Parameters:**
- `ingredients` (required) - Comma-separated ingredients
- `cuisine` (optional, default=any) - Cuisine type
- `dietaryRestrictions` (optional, default=none) - Dietary restrictions

**Response:**
```
🍳 Italian Chicken Tomato Recipe

Ingredients:
- 500g chicken breast
- 3 fresh tomatoes
- ...

Instructions:
1. Cut the chicken into cubes
...
```

## Configuration

### OpenAI API Key

**Option 1: Environment Variable (Recommended)**
```bash
export SPRING_AI_OPENAI_API_KEY=sk-xxxxx
```

**Option 2: Application Properties**
Edit `src/main/resources/application.properties`:
```properties
spring.ai.openai.api-key=sk-xxxxx
```

**Option 3: Docker**
```bash
docker run -e SPRING_AI_OPENAI_API_KEY=sk-xxxxx springai-backend
```

### CORS Configuration

CORS is enabled for frontend communication in `config/WebConfig.java`:
```java
allowedOrigins: ["http://localhost:5173", "http://localhost:3000"]
```

Update `allowedOrigins` to match your frontend URL.

## Docker

### Build Docker Image

```bash
docker build -t springai-backend .
```

### Run Docker Container

```bash
docker run -p 8080:8080 \
  -e SPRING_AI_OPENAI_API_KEY=sk-xxxxx \
  springai-backend
```

### Docker Compose (Optional)

Create `docker-compose.yml`:
```yaml
version: '3.8'

services:
  backend:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_AI_OPENAI_API_KEY: ${OPENAI_API_KEY}
```

Run:
```bash
docker-compose up
```

## Deployment

### Deploy on Render

Detailed instructions in [DEPLOYMENT.md](DEPLOYMENT.md)

**Quick Steps:**
1. Push code to GitHub
2. Connect GitHub to Render
3. Create Web Service with Docker runtime
4. Set `OPENAI_API_KEY` environment variable
5. Deploy

### Deploy on Other Platforms

- **AWS ECS/ECR**: Use Dockerfile with AWS ECR registry
- **Google Cloud Run**: `gcloud run deploy --source .`
- **Azure Container Instances**: Push to ACR and deploy
- **Heroku**: Use `heroku.yml` or Buildpack

## Development

### Run Tests

```bash
mvn test
```

### Build without Tests

```bash
mvn clean package -DskipTests
```

### View Application Logs

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.root=DEBUG"
```

### IDE Setup

**IntelliJ IDEA:**
1. Open project
2. File → Project Structure → SDK → Select Java 21
3. Run → Edit Configurations → Add Spring Boot configuration
4. Set main class: `com.ai.SpringAI.SpringAiApplication`

**VS Code:**
1. Install Extension Pack for Java
2. Open folder
3. Press F5 to run

## Troubleshooting

### Issue: "API key not found"
**Solution:** Make sure `SPRING_AI_OPENAI_API_KEY` is set:
```bash
echo $SPRING_AI_OPENAI_API_KEY
```

### Issue: "Connection refused" from frontend
**Solution:** Update CORS settings in `config/WebConfig.java` with your frontend URL:
```java
.allowedOrigins("https://your-frontend-url.com")
```

### Issue: Docker build fails
**Solution:** 
- Check Java 21 compatibility
- Run `mvn clean` before Docker build
- Check internet connection for Maven dependencies

### Issue: Render deployment fails
**Solution:**
- Check build logs in Render Dashboard
- Verify `Dockerfile` exists in Backend folder
- Ensure pom.xml is at Backend root

## API Limits

- OpenAI API calls are rate limited per your account tier
- Image generation: Max 10 images per request
- Chat: Max 150 tokens per response (configurable)

## Security Considerations

- **Never commit API keys** to GitHub
- Use environment variables for sensitive data
- In production, use secrets manager (Render Secrets, AWS Secrets Manager, etc.)
- Enable HTTPS for all communications
- Validate and sanitize all user inputs

## Performance Optimization

- Implement caching for repeated requests
- Add request rate limiting
- Use async processing for long-running tasks
- Monitor API usage and costs

## Dependencies

Main dependencies (see `pom.xml`):
- Spring Boot Starter Web
- Spring Boot Starter OpenAI
- Spring Boot Starter Test
- Jakarta Servlet API


