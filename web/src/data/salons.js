const STANDARD_SERVICES = [
  "Corte de cabello",
  "Peinado",
  "Manicura",
  "Pedicura",
  "Tinte y color",
  "Depilación",
  "Maquillaje",
  "Tratamiento capilar",
];

export const SALONS = [
  {
    id: "norte",
    name: "Sala Norte",
    tag: "Premium",
    image: "/img/sala2.jpg",
    description:
      "Una sala moderna y confortable, diseñada con un estilo contemporáneo y equipada con la última tecnología en tratamientos capilares.",
    servicesTitle: "Servicios e instalaciones",
    services: STANDARD_SERVICES,
  },
  {
    id: "sur",
    name: "Sala Sur",
    image: "/img/sala1.jpg",
    description:
      "Un espacio de lujo y confort con un ambiente relajante, donde nuestros estilistas te ofrecen un servicio personalizado de alta calidad.",
    servicesTitle: "Servicios e instalaciones",
    services: STANDARD_SERVICES,
  },
  {
    id: "unisex",
    name: "Sala Unisex",
    tag: "Inclusiva",
    image: "/img/sala-unisex.jpg",
    description:
      "Un espacio moderno y versátil para todos los géneros, con servicios personalizados que se adaptan a cada estilo y preferencia.",
    servicesTitle: "Servicios especializados",
    services: [
      "Cortes para todos los géneros",
      "Coloración y mechas personalizadas",
      "Barbería y afeitado profesional",
      "Peinados para eventos",
      "Tratamientos capilares específicos",
      "Asesoramiento de imagen",
    ],
  },
  {
    id: "vip",
    name: "Sala VIP",
    tag: "Luxury",
    premium: true,
    image: "/img/sala-vip.jpeg",
    description:
      "Una experiencia exclusiva con atención individual, privacidad y los tratamientos más avanzados. Incluye masaje capilar y bebida de cortesía.",
    servicesTitle: "Servicios premium",
    services: [
      "Corte y peinado exclusivo",
      "Coloración avanzada",
      "Tratamientos de lujo",
      "Manicura y pedicura premium",
      "Masaje capilar",
      "Atención personalizada",
    ],
  },
];

export const salonById = (id) => SALONS.find((s) => s.id === id);
