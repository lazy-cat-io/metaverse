const { contextBridge, ipcRenderer } = require("electron");

contextBridge.exposeInMainWorld("bridge", {
  // Renderer to main (one-way)
  send: (event) => ipcRenderer.send("send", event),

  // Renderer to main (two-way)
  invoke: (event) => ipcRenderer.invoke("invoke", event),

  // Main to renderer
  dispatch: (event) => ipcRenderer.on("dispatch", event),
});
