# Interview Atlas API

Local Spring Boot backend for the AI interview mode.

## Run

1. Open `src/main/resources/application.yml`
2. Choose the provider in `interview-atlas.ai.provider`
3. Paste the matching API key into `interview-atlas.ai.api-key`
3. Start the app:

```bash
mvn spring-boot:run
```

The API runs on `http://localhost:8080`.

## Endpoints

- `GET /api/ai/health`
- `POST /api/ai/problem`
- `POST /api/ai/approach-review`
- `POST /api/ai/clarify`
- `POST /api/ai/hint`
- `POST /api/ai/evaluate`

## Notes

- This backend is local-first and meant for personal use.
- Supported providers:
  - `gemini`
  - `openai`
- The provider API key stays in the backend config, not in the browser.
- Later this can be replaced by env vars, secret managers, or a deployed backend.
