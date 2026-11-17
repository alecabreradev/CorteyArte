// File name must match exactly: models/counter.js (Linux servers are case-sensitive)
const Counter = require("../models/counter");

/** Returns the next number of an auto-increment sequence (1, 2, 3...). */
async function getNextSequence(name) {
  const result = await Counter.findByIdAndUpdate(
    name,
    { $inc: { sequence_value: 1 } },
    { new: true, upsert: true }
  );
  return result.sequence_value;
}

module.exports = getNextSequence;
