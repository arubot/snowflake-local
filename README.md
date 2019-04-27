# Local Snowflakes
A local implementation of Snowflake generation in Java.

Licensed under the [Apache 2.0 License](https://github.com/arudiscord/snowflake-local/blob/master/LICENSE).

### Installation

![Latest Version](https://api.bintray.com/packages/arudiscord/maven/snowflake-local/images/download.svg)

Using in Gradle:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'pw.aru.libs:snowflake-local:LATEST' // replace LATEST with the version above
}
```

Using in Maven:

```xml
<repositories>
  <repository>
    <id>central</id>
    <name>bintray</name>
    <url>http://jcenter.bintray.com</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>pw.aru.libs</groupId>
    <artifactId>snowflake-local</artifactId>
    <version>LATEST</version> <!-- replace LATEST with the version above -->
  </dependency>
</dependencies>
```

### Usage

Use `LocalGenerator` or `LocalGeneratorBuilder` to create the local generators.

```java
SnowflakeGenerator generator = new LocalGeneratorBuilder()
    .epoch(1420070400000L)
    .build();
```

Then use the `SnowflakeGenerator` to generate IDs.

```java
long id = generator.getDatacenter(3L).getWorker(0L).generate();
```

### Support

Support is given on [Aru's Discord Server](https://discord.gg/URPghxg)

[![Aru's Discord Server](https://discordapp.com/api/guilds/403934661627215882/embed.png?style=banner2)](https://discord.gg/URPghxg)
