CREATE TABLE tasting_notes(
  id SERIAL PRIMARY KEY,
  wine_id INT,
  user_id INT,
  notes VARCHAR(2550),
  score INT
)