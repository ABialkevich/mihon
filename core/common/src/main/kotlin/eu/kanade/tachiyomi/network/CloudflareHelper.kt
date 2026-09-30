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

    // True when a UI (reader/WebView) is actively listening and can show the challenge.
    val hasInteractiveListener: Boolean
        get() = _interactiveChallengeFlow.subscriptionCount.value > 0

    fun emitInteractiveChallenge(url: String) {
        _interactiveChallengeFlow.tryEmit(url)
    }
}
