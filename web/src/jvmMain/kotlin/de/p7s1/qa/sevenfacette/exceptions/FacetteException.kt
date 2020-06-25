package de.p7s1.qa.sevenfacette.exceptions

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
open class FacetteException : RuntimeException {

    /**
     * Constructs a new exception with null as its detail message.
     */
    constructor() : super()

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    constructor(message: String) : super(message)

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    constructor(message: String, cause: Throwable?) : super(message, cause)

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * (`cause?.toString()`) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    constructor(cause: Throwable?) : super(cause)

    /**
     * Constructs a new exception with the specified detail message, cause, suppression
     * enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message the detail message.
     * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether or not suppression is enabled or disabled.
     * @param writableStackTrace whether or not the stack trace should be writable.
     */
    constructor(message: String, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(
            message,
            cause,
            enableSuppression,
            writableStackTrace
    )
}
