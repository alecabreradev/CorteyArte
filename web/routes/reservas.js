const express = require('express');
const router = express.Router();
const reservasService = require('./reservas-service');

router.get("/", async (req, res) => {
	try {
		const reservas = await reservasService.getAll();
		if (reservas.length == 0) {
			res.status(404).send({ msg: 'No se encontraron reservas' });
		}
		else{
			res.status(200).send(reservas);
		}
	} catch (error) {
		console.error("Error al buscar reservas:", error);
		res.status(500).send({ msg: error.message });
	}
});

router.post("/", async (req, res) => {
	const reserva = req.body;
	// Comprobación de que la reserva enviada no está vacía
	if (Object.keys(reserva).length == 0) {
		res.status(400).send({ msg: 'La reserva está vacía' });
	} 
	else {
		try {
			await reservasService.add(reserva);
			res.status(201).send({ msg: 'Reserva creada!'});
		} catch (error) {
			console.error("Error al agregar reserva:", error);
			res.status(500).send({ msg: error.message });
		}
	}
});

router.delete('/', async (req, res) => {
	try {
		await reservasService.removeAll();
		res.status(200).send({ msg: 'Reservas eliminadas!' });
	} catch (error) {
		console.error("Error al eliminar reservas:", error);
		res.status(500).send({ msg: error.message });
	}
});

router.get("/:_id", async (req, res) => {
	const { _id } = req.params;
	try {
		const reserva = await reservasService.get(_id);
		if (!reserva) {
			res.status(404).send({ msg: 'Reserva no encontrada' });
		}
		else{
			res.status(200).send(reserva);
		}
	} catch (error) {
		console.error("Error al buscar la reserva:", error);
		res.status(500).send({ msg: error.message });
	}
});

router.put("/:_id", async (req, res) => {
	const { _id } = req.params;
	const reservaData = req.body;
	try {
		const result = await reservasService.update(_id, reservaData);
		if (result.modifiedCount === 0) {
			return res.status(404).send({ msg: "reserva no encontrada o sin cambios." });
		}
		res.status(200).send({ msg: 'reserva actualizada!' });
	} catch (error) {
		console.error("Error al actualizar reserva:", error);
		res.status(500).send({ msg: error.message });
	}
});

router.delete("/:_id",async (req, res) =>{
	const {_id } = req.params;
	try {
		await reservasService.remove(_id);
    	res.status(200).send({ msg: "Reserva eliminada!" });
		
	} catch (error) {
		console.error("Error al eliminar reserva:", error);
		res.status(500).send({ msg: error.message });
	}
});

module.exports = router;
