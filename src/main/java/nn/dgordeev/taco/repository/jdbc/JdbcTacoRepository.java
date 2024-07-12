package nn.dgordeev.taco.repository.jdbc;

import lombok.RequiredArgsConstructor;
import nn.dgordeev.taco.exception.TacoCloudException;
import nn.dgordeev.taco.model.Taco;
import nn.dgordeev.taco.repository.TacoRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcOperations jdbcOperations;
    private final JdbcIngredientRepository jdbcIngredientRepository;

    @Override
    @Transactional
    public Taco save(Taco taco) {
        taco.setCreatedAt(new Date());
        var pscf = new PreparedStatementCreatorFactory("""
                INSERT INTO taco (created_at, name) VALUES (?, ?)
                """, Types.TIMESTAMP, Types.VARCHAR);
        pscf.setReturnGeneratedKeys(true);
        var psc = pscf.newPreparedStatementCreator(List.of(taco.getCreatedAt(), taco.getName()));

        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);

        var id = ofNullable(keyHolder.getKeys())
                .map(keys -> (Long) keys.get("id"))
                .orElseThrow(() -> new TacoCloudException("Unable to retrieve order identifier."));
        taco.setId(id);

        return taco;
    }

    @Override
    public void save(Long orderId, Taco taco) {
        var savedTaco = save(taco);
        jdbcIngredientRepository.save(savedTaco.getId(), savedTaco.getIngredients());
        jdbcOperations.update(
                "INSERT INTO taco_order_ref (taco_id, order_id) VALUES (?, ?)",
                savedTaco.getId(),
                orderId
        );
    }
}
