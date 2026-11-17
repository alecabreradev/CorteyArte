const { getCollection } = require('./db');
const { ObjectId } = require('mongodb');

const reserva = function () {};

reserva.prototype.getAll = async () => {
	const reservaCollection = await getCollection("reserva");
	return await reservaCollection.find({}).toArray();
};

reserva.prototype.add = async (reservaData) => {
	const reservaCollection = await getCollection("reserva");
	return await reservaCollection.insertOne(reservaData);
};

reserva.prototype.removeAll = async () => {
	const reservaCollection = await getCollection("reserva");
	return await reservaCollection.deleteMany({});
};

reserva.prototype.get = async (_id) => {
	const reservaCollection = await getCollection("reserva");
	console.log(typeof _id, _id);
	return await reservaCollection.findOne({ _id: ObjectId.createFromHexString(_id) });
};

reserva.prototype.update = async (_id, updatedreserva) => {
	const reservaCollection = await getCollection("reserva");
	return await reservaCollection.updateOne({ _id: ObjectId.createFromHexString(_id) }, { $set: updatedreserva } );
};

reserva.prototype.remove = async (_id) => {
	const reservaCollection = await getCollection("reserva");
	return await reservaCollection.deleteOne({ _id: ObjectId.createFromHexString(_id) });
};

module.exports = new reserva();