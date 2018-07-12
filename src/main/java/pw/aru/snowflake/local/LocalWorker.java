package pw.aru.snowflake.local;

import jibril.snowflake.SnowflakeConfig;
import jibril.snowflake.entities.SnowflakeDatacenter;
import jibril.snowflake.entities.SnowflakeGenerator;
import jibril.snowflake.entities.SnowflakeWorker;

import static java.lang.System.currentTimeMillis;

class LocalWorker implements SnowflakeWorker {

    private final LocalDatacenter datacenter;
    private final LocalGenerator generator;
    private final long workerId;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    LocalWorker(LocalGenerator generator, LocalDatacenter datacenter, long workerId) {
        this.generator = generator;
        this.datacenter = datacenter;

        // sanity check for workerId
        long maxWorkerId = generator.config.maxWorkerId;
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than " + maxWorkerId + " or less than 0");
        }

        this.workerId = workerId;
    }

    @Override
    public long generate() {
        SnowflakeConfig config = generator.getConfig();
        long timestamp = currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new IllegalStateException(String.format(
                "Clock moved backwards. Refusing to generate id for %d milliseconds",
                lastTimestamp - timestamp
            ));
        }

        synchronized (this) {
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & config.sequenceMask;
                if (sequence == 0) timestamp = tilNextMillis(lastTimestamp);
            } else sequence = 0L;

            lastTimestamp = timestamp;

            return timestamp - config.epoch << config.timestampShift | datacenter.datacenterId << config.datacenterIdShift | workerId << config.workerIdShift | sequence;
        }
    }

    @Override
    public SnowflakeDatacenter getDatacenter() {
        return datacenter;
    }

    @Override
    public SnowflakeGenerator getGenerator() {
        return generator;
    }

    @Override
    public long getWorkerId() {
        return workerId;
    }

    @Override
    public String toString() {
        return "LocalWorker[config=" + generator.config + ", datacenterId=" + datacenter.datacenterId + ", workerId=" + workerId + "]";
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = currentTimeMillis();
        while (timestamp <= lastTimestamp) timestamp = currentTimeMillis();
        return timestamp;
    }
}
