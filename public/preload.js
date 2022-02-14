const { contextBridge, ipcRenderer } = require("electron");

contextBridge.exposeInMainWorld("bridge", {
  dispatch: (event) => ipcRenderer.invoke("dispatch", event),
});
