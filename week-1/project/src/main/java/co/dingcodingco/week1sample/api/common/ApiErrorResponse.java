package co.dingcodingco.week1sample.api.common;

public record ApiErrorResponse(
    String code,
    String message
) {
}
