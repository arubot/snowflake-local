package jibril.snowflake.local;

import jibril.snowflake.Snowflake;
import jibril.snowflake.SnowflakeConfig;
import jibril.snowflake.entities.SnowflakeGenerator;

public class LocalGeneratorBuilder {
    private long datacenterIdBits = 5;
    private long epoch;
    private long sequenceBits = 12;
    private long workerIdBits = 5;

    public LocalGeneratorBuilder() {
    }

    public LocalGeneratorBuilder(Snowflake metadata) {
        setDatacenterIdBits(metadata.datacenterIdBits());
        setSequenceBits(metadata.sequenceBits());
        setWorkerIdBits(metadata.workerIdBits());
        setEpoch(metadata.epoch());
    }

    public LocalGeneratorBuilder(SnowflakeConfig config) {
        datacenterIdBits = config.datacenterIdBits;
        sequenceBits = config.sequenceBits;
        workerIdBits = config.workerIdBits;
        epoch = config.epoch;
    }

    public SnowflakeGenerator build() {
        return new LocalGenerator(
            new SnowflakeConfig(epoch, datacenterIdBits, workerIdBits, sequenceBits)
        );
    }

    public LocalGeneratorBuilder setDatacenterIdBits(long datacenterIdBits) {
        if (datacenterIdBits < 0) throw new IllegalArgumentException("datacenterIdBits must be positive");
        if ((datacenterIdBits + workerIdBits + sequenceBits) >= Long.SIZE)
            throw new IllegalArgumentException("(datacenterIdBits + workerIdBits + sequenceBits) need to be under " + Long.SIZE + " bits.");

        this.datacenterIdBits = datacenterIdBits;
        return this;
    }

    public LocalGeneratorBuilder setEpoch(long epoch) {
        if (epoch < 0) throw new IllegalArgumentException("epoch must be positive");
        if (epoch > System.currentTimeMillis()) throw new IllegalArgumentException("epoch is on the future");

        this.epoch = epoch;
        return this;
    }

    public LocalGeneratorBuilder setSequenceBits(long sequenceBits) {
        if (sequenceBits < 0) throw new IllegalArgumentException("sequenceBits must be positive");
        if ((datacenterIdBits + workerIdBits + sequenceBits) >= Long.SIZE)
            throw new IllegalArgumentException("(datacenterIdBits + workerIdBits + sequenceBits) need to be under " + Long.SIZE + " bits.");

        this.sequenceBits = sequenceBits;
        return this;
    }

    public LocalGeneratorBuilder setWorkerIdBits(long workerIdBits) {
        if (workerIdBits < 0) throw new IllegalArgumentException("workerIdBits must be positive");
        if ((datacenterIdBits + workerIdBits + sequenceBits) >= Long.SIZE)
            throw new IllegalArgumentException("(datacenterIdBits + workerIdBits + sequenceBits) need to be under " + Long.SIZE + " bits.");

        this.workerIdBits = workerIdBits;
        return this;
    }
}
