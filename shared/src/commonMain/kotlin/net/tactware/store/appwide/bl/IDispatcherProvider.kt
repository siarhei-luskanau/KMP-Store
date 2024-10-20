package net.tactware.qrg.appwide.bl.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * Provides access to various `CoroutineContext` configurations for executing coroutines
 * in different threading environments. This interface abstracts the creation of coroutine
 * contexts with optional error handling through `CoroutineExceptionHandler`, facilitating
 * consistent coroutine management and error handling strategies across the application.
 */
interface IDispatcherProvider {

    fun ui(errorCatcher: CoroutineExceptionHandler? = null): CoroutineContext

    /**
     * Provides a `CoroutineContext` for executing coroutines on the main thread, optionally
     * integrated with a `CoroutineExceptionHandler` for centralized error handling.
     *
     * @param errorCatcher An optional `CoroutineExceptionHandler` for capturing uncaught exceptions
     *                     in coroutines running on the main thread.
     * @return A `CoroutineContext` configured for main thread execution.
     */
    fun main(errorCatcher: CoroutineExceptionHandler? = null): CoroutineContext

    /**
     * Provides a `CoroutineContext` optimized for I/O operations, optionally integrated
     * with a `CoroutineExceptionHandler` for error handling.
     *
     * @param errorCatcher An optional `CoroutineExceptionHandler` for capturing uncaught exceptions
     *                     in coroutines dedicated to I/O operations.
     * @return A `CoroutineContext` configured for I/O operations.
     */
    fun io(errorCatcher: CoroutineExceptionHandler? = null): CoroutineContext

    /**
     * Provides a `CoroutineContext` for executing compute-intensive operations in the background,
     * optionally integrated with a `CoroutineExceptionHandler`.
     *
     * @param errorCatcher An optional `CoroutineExceptionHandler` for capturing uncaught exceptions
     *                     in coroutines running compute-intensive tasks.
     * @return A `CoroutineContext` configured for default dispatcher use.
     */
    fun default(errorCatcher: CoroutineExceptionHandler? = null): CoroutineContext

    /**
     * Provides a `CoroutineContext` that is not confined to any specific thread, optionally
     * integrated with a `CoroutineExceptionHandler` for error handling.
     *
     * @param errorCatcher An optional `CoroutineExceptionHandler` for capturing uncaught exceptions
     *                     in coroutines running on the unconfined dispatcher.
     * @return A `CoroutineContext` configured for unconfined execution.
     */
    fun unconfined(errorCatcher: CoroutineExceptionHandler? = null): CoroutineContext
}
