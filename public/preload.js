const { contextBridge, ipcRenderer } = require("electron");

contextBridge.exposeInMainWorld("bridge", {
  dispatch: (handler, event) => ipcRenderer.invoke("dispatch", handler, event),
});
