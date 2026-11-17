require("dotenv").config(); // Loads variables from .env
const mongoose = require("mongoose");
const app = require("./app");

const uri = process.env.MONGODB_URI;
const PORT = process.env.PORT || 3000;

if (!uri) {
  console.error("❌ MONGODB_URI is not defined. Copy .env.example to .env and fill it in.");
  process.exit(1);
}

mongoose
  .connect(uri)
  .then(() => {
    console.log("✅ Connected to MongoDB");
    app.listen(PORT, "0.0.0.0", () => {
      console.log(`🚀 API running on http://localhost:${PORT}`);
    });
  })
  .catch((err) => {
    console.error("❌ Could not connect to MongoDB:", err.message);
    process.exit(1);
  });
