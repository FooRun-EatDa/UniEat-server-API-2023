package foorun.unieat.api.exclude.local;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.io.Closeable;

@Slf4j
@Profile("local")
@ToString()
@Validated
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "foorun.unieat.local.tunnel.ssh")
public class SshTunneling implements Closeable {
    private static final JSch jsch;
    private static Session session;
    static {
        jsch = new JSch();
    }

    @NotNull private String host;
    @NotNull private String username;
    @ToString.Exclude
    @NotNull private String password;

    @Getter(AccessLevel.NONE)
    @NotNull private String port;

    @ToString.Exclude
    @NotNull private String prvkey;

    @NotNull private String endpoint;
    @Getter(AccessLevel.NONE)
    @NotNull private String lport;

    @Getter(AccessLevel.NONE)
    @NotNull private String rport;

    public int connectToSSH() throws JSchException {
        int port = Integer.parseInt(this.port);
        int lport = Integer.parseInt(this.lport);
        int rport = Integer.parseInt(this.rport);

        log.debug("{}@{}:{} " + (prvkey == null || prvkey.trim().isEmpty() ? "" : "with private key") + " try connect.", username, host, port);
        if (prvkey != null && !prvkey.trim().isEmpty()) {
            jsch.addIdentity(prvkey);
        }

        session = jsch.getSession(username, host, port);
        if (password != null && !password.isEmpty()) {
            session.setPassword(password);
        }

        session.setConfig("StrictHostKeyChecking", "no" /* ask */); // 접속 시, host key checking 여부
        session.connect();
        log.debug("ssh connected.");

        int openPort = session.setPortForwardingL(lport, endpoint, rport);
        log.debug("{}:{} bound to localhost:{}", endpoint, rport, openPort);

        return openPort;
    }

    @PostConstruct
    public void printProperties() {
        log.debug(this.toString());
    }

    @PreDestroy
    @Override
    public void close() {
        session.disconnect();
        log.warn("shutdown tunneling ssh.");
    }

    public int getPort() {
        return Integer.parseInt(this.port);
    }

    public int getLport() {
        return Integer.parseInt(this.lport);
    }

    public int getRport() {
        return Integer.parseInt(this.rport);
    }
}