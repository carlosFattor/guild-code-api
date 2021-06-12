package org.guildcode.infrastructure.data.exceptions;

import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.shared.exception.ErrorCode;

public class GithubUserEmailNotFound extends DomainException {

    public GithubUserEmailNotFound() {
        super(ErrorCode.GITHUB_USER_EMAIL_NOT_FOUND);
    }

    public GithubUserEmailNotFound(ErrorCode error, String... message) {
        super(error, message);
    };
}
