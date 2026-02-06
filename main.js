// electron's main.js
const { app, BrowserWindow } = require("electron");

// determine whether the current environment is a development environment
const isDev = !app.isPackaged;

const createWindow = () => {
  const win = new BrowserWindow({
    width: 1200,
    height: 800,
    webPreferences: {
      // preload script path
      // preload: path.join(__dirname, 'electron/preload.js'),
      nodeIntegration: false,
      contextIsolation: true,
    },
  });

  if (isDev) {
    // if in dev mode, load vite local service
    win.loadURL("http://localhost:5173");
    // automatically open DevTools in dev mode
    win.webContents.openDevTools();
  } else {
    // if in production mode, load packaged file
    // Vite will package index.html into dist/
    win.loadFile(path.join(__dirname, "dist/index.html"));
  }
};

app.whenReady().then(() => {
  createWindow();

  app.on("activate", () => {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
  });
});

app.on("window-all-closed", () => {
  if (process.platform !== "darwin") app.quit();
});
