/// <reference types="vite/client" />
interface ImportMetaEnv {
  readonly VITE_MONEY_SPLITTER_BACKEND_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}