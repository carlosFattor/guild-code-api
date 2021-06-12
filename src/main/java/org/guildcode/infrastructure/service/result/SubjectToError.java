package org.guildcode.infrastructure.service.result;

import java.util.List;

public interface SubjectToError {
    boolean hasSucceeded();

    List<FailureDetail> getFailureDetails();
}

