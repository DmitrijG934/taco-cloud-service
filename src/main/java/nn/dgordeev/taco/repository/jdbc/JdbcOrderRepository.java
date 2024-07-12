package nn.dgordeev.taco.repository.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nn.dgordeev.taco.exception.TacoCloudException;
import nn.dgordeev.taco.model.TacoOrder;
import nn.dgordeev.taco.repository.OrderRepository;
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

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcOperations jdbcOperations;
    private final TacoRepository tacoRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {
        var delivery = convertToJsonString(tacoOrder.getDelivery());
        var payment = convertToJsonString(tacoOrder.getPayment());
        var preparedStatementCreatorFactory = new PreparedStatementCreatorFactory("""
                INSERT INTO taco_order (created_at, delivery, payment) VALUES
                (?, ?::jsonb, ?::jsonb)""", Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        tacoOrder.setCreatedAt(new Date());
        var preparedStatementCreator = preparedStatementCreatorFactory
                .newPreparedStatementCreator(List.of(
                        tacoOrder.getCreatedAt(), delivery, payment));
        var generatedKeyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(preparedStatementCreator, generatedKeyHolder);

        var orderId = ofNullable(generatedKeyHolder.getKeys())
                .map(keys -> (Long) keys.get("id"))
                .orElseThrow(() -> new TacoCloudException("Unable to retrieve order identifier."));

        tacoOrder.setId(orderId);

        tacoOrder.getTacos().forEach(taco -> tacoRepository.save(orderId, taco));

        return tacoOrder;
    }

    private String convertToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error happened during conversion: {}", e.getMessage());
            throw new TacoCloudException("Conversion error", e);
        }
    }
}
