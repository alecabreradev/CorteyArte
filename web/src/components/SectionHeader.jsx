export default function SectionHeader({ title, subtitle, as: Heading = "h2" }) {
  return (
    <div className="section-header">
      <Heading>{title}</Heading>
      {subtitle && <p>{subtitle}</p>}
    </div>
  );
}
