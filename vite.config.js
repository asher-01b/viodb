// frontend/vite.config.js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  base: './', // after packaging with Electron, the paths need to be relative 
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src') // use @ to represent src dir 
    }
  },
  server: {
    port: 5173, // server port 
  }
})