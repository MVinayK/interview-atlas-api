package com.interviewatlas.api.provider;

import java.time.Duration;
import java.util.concurrent.Callable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

final class AiRetrySupport {

  private static final int MAX_ATTEMPTS = 3;
  private static final Duration INITIAL_BACKOFF = Duration.ofMillis(600);

  private AiRetrySupport() {}

  static <T> T execute(Callable<T> operation) {
    Duration backoff = INITIAL_BACKOFF;

    for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
      try {
        return operation.call();
      } catch (Exception exception) {
        if (!isRetryable(exception) || attempt == MAX_ATTEMPTS) {
          throw rethrow(exception);
        }

        sleep(backoff);
        backoff = backoff.multipliedBy(2);
      }
    }

    throw new IllegalStateException("AI retry execution reached an impossible state.");
  }

  private static boolean isRetryable(Exception exception) {
    if (exception instanceof ResourceAccessException) {
      return true;
    }

    if (exception instanceof HttpStatusCodeException statusCodeException) {
      int status = statusCodeException.getStatusCode().value();
      return status == 429 || status == 502 || status == 503 || status == 504;
    }

    return false;
  }

  private static void sleep(Duration duration) {
    try {
      Thread.sleep(duration.toMillis());
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("AI retry was interrupted.", interruptedException);
    }
  }

  private static RuntimeException rethrow(Exception exception) {
    if (exception instanceof RuntimeException runtimeException) {
      return runtimeException;
    }

    return new IllegalStateException("AI provider call failed.", exception);
  }
}
