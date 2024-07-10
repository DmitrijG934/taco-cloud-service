package nn.dgordeev.taco.repository.jdbc;

import lombok.RequiredArgsConstructor;
import nn.dgordeev.taco.model.Ingredient;
import nn.dgordeev.taco.repository.IngredientRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcIngredientRepository implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, type FROM ingredient",
                this::mapRowToIngredient
        );
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        var results = jdbcTemplate.query(
                "SELECT id, name, type FROM ingredient WHERE id = ?",
                this::mapRowToIngredient, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    @Transactional
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
                "INSERT INTO ingredient(id, name, type) VALUES (?, ?, ?)",
                ingredient.id(),
                ingredient.name(),
                ingredient.type()
        );
        return ingredient;
    }

    public void save(Long tacoId, Collection<Ingredient> ingredients) {
        ingredients.forEach(ingredient -> jdbcTemplate.update(
                "INSERT INTO ingredient_taco_ref(taco_id, ingredient_id) VALUES (?, ?)",
                tacoId, ingredient.id()
        ));
    }

    private Ingredient mapRowToIngredient(ResultSet resultSet, int row) throws SQLException {
        return new Ingredient(
                resultSet.getString("id"),
                resultSet.getString("name"),
                Ingredient.Type.valueOf(resultSet.getString("type"))
        );
    }
}
