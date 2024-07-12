package nn.dgordeev.taco.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {

    private static final String POSTGRES_IMAGE = "postgres:16.2-alpine3.19";

    public PostgresContainer() {
        super(POSTGRES_IMAGE);
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DATABASE_URL", this.getJdbcUrl());
        System.setProperty("DATABASE_USERNAME", this.getUsername());
        System.setProperty("DATABASE_PASSWORD", this.getPassword());
    }
}
