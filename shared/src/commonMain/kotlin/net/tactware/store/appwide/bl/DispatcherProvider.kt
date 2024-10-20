package net.tactware.store.appwide.bl

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import net.tactware.qrg.appwide.bl.coroutines.IDispatcherProvider
import kotlin.coroutines.CoroutineContext

/**
 * A concrete implementation of [IDispatcherProvider] that provides `CoroutineContext` instances
 * for different types of coroutine execution environments. This implementation uses the standard
 * Kotlin coroutine dispatchers and supports the integration of a `CoroutineExceptionHandler`
 * for handling uncaught exceptions in coroutines.
 */
internal class DispatcherProvider(private val defaultErrorCatcher: CoroutineExceptionHandler? = null) :
    IDispatcherProvider {
    override fun ui(errorCatcher: CoroutineExceptionHandler?): CoroutineContext =
        Dispatchers.Main.immediate + (errorCatcher ?: defaultErrorCatcher ?: CoroutineExceptionHandler { _, _ -> })

    /**
     * Returns a coroutine context for the main thread, optionally enhanced with an error handler.
     *
     * @param errorCatcher An optional [CoroutineExceptionHandler] to handle uncaught exceptions
     *                     in coroutines executing on the main thread. If null, a default no-op
     *                     handler is used.
     * @return A [CoroutineContext] that combines the Main dispatcher with the provided
     *         [errorCatcher] or a default handler.
     */
    override fun main(errorCatcher: CoroutineExceptionHandler?): CoroutineContext =
        Dispatchers.Main + (errorCatcher ?: defaultErrorCatcher ?: CoroutineExceptionHandler { _, _ -> })

    /**
     * Returns a coroutine context optimized for I/O operations, optionally enhanced with an error handler.
     *
     * @param errorCatcher An optional [CoroutineExceptionHandler] to handle uncaught exceptions
     *                     in coroutines dedicated to I/O operations. If null, a default no-op
     *                     handler is used.
     * @return A [CoroutineContext] that combines the IO dispatcher with the provided
     *         [errorCatcher] or a default handler.
     */
    override fun io(errorCatcher: CoroutineExceptionHandler?): CoroutineContext =
        Dispatchers.Default + (errorCatcher ?: defaultErrorCatcher ?: CoroutineExceptionHandler { _, _ -> })

    /**
     * Returns a coroutine context for executing compute-intensive operations in the background,
     * optionally enhanced with an error handler.
     *
     * @param errorCatcher An optional [CoroutineExceptionHandler] to handle uncaught exceptions
     *                     in coroutines running compute-intensive tasks. If null, a default no-op
     *                     handler is used.
     * @return A [CoroutineContext] that combines the Default dispatcher with the provided
     *         [errorCatcher] or a default handler.
     */
    override fun default(errorCatcher: CoroutineExceptionHandler?): CoroutineContext =
        Dispatchers.Default + (errorCatcher ?: defaultErrorCatcher ?: CoroutineExceptionHandler { _, _ -> })

    /**
     * Returns a coroutine context that is not confined to any specific thread, optionally enhanced
     * with an error handler.
     *
     * @param errorCatcher An optional [CoroutineExceptionHandler] to handle uncaught exceptions
     *                     in coroutines running on the unconfined dispatcher. If null, a default
     *                     no-op handler is used.
     * @return A [CoroutineContext] that combines the Unconfined dispatcher with the provided
     *         [errorCatcher] or a default handler.
     */
    override fun unconfined(errorCatcher: CoroutineExceptionHandler?): CoroutineContext =
        Dispatchers.Unconfined + (errorCatcher ?: defaultErrorCatcher ?: CoroutineExceptionHandler { _, _ -> })
}
