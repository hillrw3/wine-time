CREATE Table wines(
  id SERIAL PRIMARY KEY,
  winery VARCHAR(255),
  name VARCHAR(255),
  varietal VARCHAR(255),
  vintage INT,
  country VARCHAR(255),
  region VARCHAR(255)
);