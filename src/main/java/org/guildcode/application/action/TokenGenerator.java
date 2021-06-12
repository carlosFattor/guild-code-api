package org.guildcode.application.action;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.vertx.core.*;
import io.vertx.core.json.Json;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.domain.token.Tokens;
import org.guildcode.domain.enums.Role;
import org.guildcode.domain.user.User;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenGenerator extends AbstractVerticle {

    public static final String JWT_GENERATOR_CHANNEL = "JWT_GENERATOR";
    String PRIVATE_KEY_LOCATION = "/privatekey.pem";
    String type = "Bearer";
    PrivateKey privateKey;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        try {
            this.privateKey = readPrivateKey(PRIVATE_KEY_LOCATION);
            log.info("####################### STARTED VERTICLE TokenGenerator #######################");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void start(Promise<Void> startPromise) {

        this.vertx.eventBus().<String>consumer("JWT_GENERATOR", msg -> {
            try {
                var user = Json.decodeValue(msg.body(), User.class);
                var token = generateToken(user, new HashSet<>(user.getRoles()), 300000L);
                var refresh = generateToken(user, new HashSet<>(user.getRoles()), 3000000L);
                var tokens = Json.encode(new Tokens(type, token, refresh));
                msg.reply(tokens);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        startPromise.complete();
    }

    private String generateToken(User user, Set<Role> roles, Long duration) {

        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        long currentTimeInSecs = currentTimeInSecs();

        Set<String> groups = new HashSet<>();
        for (Role role : roles) groups.add(role.name());
        claimsBuilder.issuer("https://guildcode.com");
        claimsBuilder.claim("email", user.getEmail());
        claimsBuilder.subject(user.getEmail());
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresAt(currentTimeInSecs + duration);
        claimsBuilder.groups(groups);

        return claimsBuilder.jws().sign(privateKey);
    }

    private PrivateKey readPrivateKey(final String pemResName) throws Exception {
        try (InputStream contentIS = TokenGenerator.class.getResourceAsStream(pemResName)) {
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, StandardCharsets.UTF_8));
        }
    }

    private PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    private int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}
