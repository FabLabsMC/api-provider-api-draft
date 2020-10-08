package io.github.fablabsmc.fablabs.api.provider.v1;

/**
 * Unique reference to an API.
 */
public final class ApiKey<T> {
    private ApiKey() { }

    public static <T> ApiKey<T> create(Class<T> clazz) {
        return new ApiKey<>();
    }
}
