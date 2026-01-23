/// <reference types="vite/client" />
interface ImportMetaEnv {
  readonly VITE_MONEY_SPLITTER_BACKEND_URL: string;
  readonly VITE_FIREBASE_URL: string;
  readonly VITE_FIREBASE_DB_URL: string;
  readonly VITE_FIREBASE_API_KEY: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}