const { MongoClient, ServerApiVersion } = require("mongodb");

// The connection string is read from the .env file (never committed). See .env.example.
const uri = process.env.MONGODB_URI;
const dbName = process.env.MONGODB_DB || "corte-y-arte";

const clientOptions = {
	serverApi: {
		version: ServerApiVersion.v1,
		strict: true,
		deprecationErrors: true,
	}
};

let client;
let db;

// Connects to the database
async function connectDb() {
	if (!uri) {
		throw new Error("MONGODB_URI is not defined. Copy .env.example to .env and fill it in.");
	}
	client = new MongoClient(uri, clientOptions);
	await client.connect();
	db = client.db(dbName);
	console.log("Conexión correcta a la base de datos");
}

// Returns a collection of the connected database
function getCollection(collectionName) {
	if (!db) {
		throw new Error("No hay conexión a la base de datos");
	}
	return db.collection(collectionName);
}

module.exports = {
	connectDb,
	getCollection,
};
