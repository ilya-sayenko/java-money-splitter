export class HttpClientErrorResponse {
  error: ErrorResponse;
}

class ErrorResponse {
  code: number;
  message: string;
}