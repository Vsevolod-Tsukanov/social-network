package ru.effectivemobile.socialnetwork.exception.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultErrorResponse {
    private final String info;
    private final Throwable cause;
}
