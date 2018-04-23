CREATE Table wines(
  id SERIAL PRIMARY KEY,
  winery VARCHAR(255),
  name VARCHAR(255),
  varietal VARCHAR(255),
  vintage INT
);