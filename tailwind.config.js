module.exports = {
  darkMode: 'class',
  content:
    process.env.NODE_ENV === "production"
      ? ["public/assets/js/app.js"]
      : ["public/assets/js/cljs-runtime/*.js"],
  theme: {
    extend: {},
  },
  variants: {},
  plugins: [],
};
