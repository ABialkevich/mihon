package eu.kanade.tachiyomi.network

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object CloudflareHelper {
    private val _interactiveChallengeFlow = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    // Emits the URL that requires manual CloudFlare challenge resolution.
    val interactiveChallengeFlow = _interactiveChallengeFlow.asSharedFlow()

    fun emitInteractiveChallenge(url: String) {
        _interactiveChallengeFlow.tryEmit(url)
    }
}
