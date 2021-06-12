package org.guildcode.domain.token;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {

    String token;
    String type;

    public Token(String token, String type) {
        this.token = new StringBuilder()
                .append(type)
                .append(" ")
                .append(token)
                .toString();
        this.type = type;
    }
}
