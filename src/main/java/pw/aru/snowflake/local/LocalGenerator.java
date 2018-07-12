package pw.aru.snowflake.local;

import pw.aru.snowflake.SnowflakeConfig;
import pw.aru.snowflake.entities.SnowflakeDatacenter;
import pw.aru.snowflake.entities.SnowflakeGenerator;

import java.util.HashMap;
import java.util.Map;

public class LocalGenerator implements SnowflakeGenerator {

    final SnowflakeConfig config;
    private final Map<Long, LocalDatacenter> datacenters = new HashMap<>();

    public LocalGenerator(SnowflakeConfig config) {
        this.config = config;
    }

    @Override
    public SnowflakeConfig getConfig() {
        return config;
    }

    @Override
    public SnowflakeDatacenter getDatacenter(long datacenter) {
        return datacenters.computeIfAbsent(
            datacenter,
            k -> new LocalDatacenter(this, datacenter)
        );
    }

    @Override
    public String toString() {
        return "LocalGenerator[config=" + config + "]";
    }

}
