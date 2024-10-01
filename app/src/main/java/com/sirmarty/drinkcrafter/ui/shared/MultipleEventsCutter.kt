package com.sirmarty.drinkcrafter.ui.shared

/**
 * This class is intended to prevent multiple events from being launched
 * when the user is spamming clicks
 */
internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    /**
     * We create an interface to keep the implementation hidden. This class can only be
     * accessed through this companion object, which returns a singleton instance.
     * By doing so we can guarantee that different events can't be launched multiple times neither.
     */
    companion object {
        private val instance: MultipleEventsCutter by lazy { MultipleEventsCutterImpl() }
        fun get(): MultipleEventsCutter = instance
    }
}


private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    /**
     * Only executes event if 300 ms have passed since the last event was executed
     */
    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}