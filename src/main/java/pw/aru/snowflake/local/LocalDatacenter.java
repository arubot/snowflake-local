package pw.aru.snowflake.local;

import jibril.snowflake.entities.SnowflakeDatacenter;
import jibril.snowflake.entities.SnowflakeGenerator;
import jibril.snowflake.entities.SnowflakeWorker;

import java.util.HashMap;
import java.util.Map;

class LocalDatacenter implements SnowflakeDatacenter {

    final long datacenterId;
    private final LocalGenerator generator;
    private final Map<Long, LocalWorker> workers = new HashMap<>();

    LocalDatacenter(LocalGenerator generator, long datacenterId) {
        this.generator = generator;

        // sanity check for datacenterId
        long maxDatacenterId = generator.config.maxDatacenterId;
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID can't be greater than " + maxDatacenterId + " or less than 0");
        }

        this.datacenterId = datacenterId;
    }

    @Override
    public long getDatacenterId() {
        return datacenterId;
    }

    @Override
    public SnowflakeGenerator getGenerator() {
        return generator;
    }

    @Override
    public SnowflakeWorker getWorker(long worker) {
        return workers.computeIfAbsent(
            worker,
            k -> new LocalWorker(generator, this, worker)
        );
    }

    @Override
    public String toString() {
        return "LocalDatacenter[config=" + generator.config + ", datacenterId=" + datacenterId + "]";
    }

}
