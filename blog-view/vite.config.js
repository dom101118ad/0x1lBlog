import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      'router': fileURLToPath(new URL('./src/router', import.meta.url)),
      'store': fileURLToPath(new URL('./src/store', import.meta.url))
    },
    extensions: ['.ts', '.js', '.vue', '.json']
  },
  server: {
    host: true,
    port: 5174
  },"rewrites": [
    { "source": "/(.*)", "destination": "/" }
  ]
})
