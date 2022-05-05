module.exports = {
  content:
    process.env.NODE_ENV === "production"
      ? ["./public/**/*.html", "./public/js/renderer.js"]
      : ["./public/**/*.html", "./public/js/renderer.js", "./public/js/cljs-runtime/*.js"],
  theme: {
    extend: {
      backgroundImage: {
        logotype: "url('/assets/images/logotype.black.svg')",
      },
    },
  },
  darkMode: "class",
  variants: {},
  plugins: [],
};
