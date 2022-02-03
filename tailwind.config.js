module.exports = {
  content:
    process.env.NODE_ENV === "production"
      ? ["./public/**/*.html", "./public/assets/js/main.js"]
      : ["./public/**/*.html", "./public/assets/js/main.js", "./public/assets/js/cljs-runtime/*.js"],
  theme: {
    extend: {},
  },
  variants: {},
  plugins: [],
};
