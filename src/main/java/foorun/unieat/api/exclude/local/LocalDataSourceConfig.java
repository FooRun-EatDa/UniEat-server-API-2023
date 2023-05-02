package foorun.unieat.api.exclude.local;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Slf4j
@Profile("local")
@Configuration
@RequiredArgsConstructor
public class LocalDataSourceConfig {
    private final SshTunneling tunnel;

    @Bean("dataSource")
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        String url = properties.getUrl();
        try {
            int bindPort = tunnel.connectToSSH();
            String targetHost = tunnel.getEndpoint();
            int targetPort = tunnel.getRport();

            url = url.replace(targetHost + ":" + targetPort, "localhost:" + bindPort);
            log.debug("tunneling database url: {}", url);
        } catch (Exception e) {
            log.debug("tunneling 실패", e);
            throw new RuntimeException(e);
        }

        return DataSourceBuilder.create()
                .driverClassName(properties.getDriverClassName())
                .url(url)
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }
}