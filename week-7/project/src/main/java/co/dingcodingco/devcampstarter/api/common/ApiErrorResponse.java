package co.dingcodingco.devcampstarter.api.common;

public record ApiErrorResponse(
    String code,
    String message
) {
}
