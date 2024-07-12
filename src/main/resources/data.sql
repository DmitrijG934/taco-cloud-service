-- Fulfill ingredients table
INSERT INTO ingredient (id, name, type) VALUES
    ('FLTO', 'Flour Tortilla', 'WRAP'),
    ('COTO', 'Corn Tortilla', 'WRAP'),
    ('GRBF', 'Ground Beef', 'PROTEIN'),
    ('CARN', 'Carnitas', 'PROTEIN'),
    ('TMTO', 'Diced Tomatoes', 'VEGGIES'),
    ('LETC', 'Lettuce', 'VEGGIES'),
    ('CHED', 'Cheddar', 'CHEESE'),
    ('JACK', 'Monterrey Jack', 'CHEESE'),
    ('SLSA', 'Salsa', 'SAUCE'),
    ('SRCR', 'Sour Cream', 'SAUCE')
ON CONFLICT(id)
DO UPDATE
SET id = EXCLUDED.id,
    name = EXCLUDED.name,
    type = EXCLUDED.type;
