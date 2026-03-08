package dev.vdrenkov.biblium.jwt;

/**
 * JWT-related constants used by the security layer.
 */
public final class JwtConstants {
    /**
     * JWT token validity in seconds.
     */
    public static final long JWT_TOKEN_VALIDITY = 60L * 60L;

    /**
     * JWT HTTP cookie name.
     */
    public static final String JWT_COOKIE_NAME = "BIBLIUM_AUTH";

    /**
     * Prevents instantiation of this utility class.
     */
    private JwtConstants() {
        throw new IllegalStateException("Utility class. Do not instantiate!");
    }
}
