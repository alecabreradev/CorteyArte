import Icon from "./Icon.jsx";

/** Star rating from 0 to 5 in steps of 0.5. */
export default function Rating({ value }) {
  const stars = [1, 2, 3, 4, 5].map((n) => {
    if (value >= n) return "star";
    if (value >= n - 0.5) return "star-half";
    return "star-outline";
  });
  return (
    <div className="rating" role="img" aria-label={`${value} de 5 estrellas`}>
      {stars.map((name, i) => (
        <Icon key={i} name={name} />
      ))}
    </div>
  );
}
