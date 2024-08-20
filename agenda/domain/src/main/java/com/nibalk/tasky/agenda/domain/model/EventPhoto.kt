package com.nibalk.tasky.agenda.domain.model

sealed class EventPhoto (val location: String) {
    data class Local(
        val key: String,
        val localUri: String,
        var photoBytes: ByteArray?
    ) : EventPhoto(localUri) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Local

            if (key != other.key) return false
            if (localUri != other.localUri) return false
            if (!photoBytes.contentEquals(other.photoBytes)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = key.hashCode()
            result = 31 * result + localUri.hashCode()
            result = 31 * result + photoBytes.contentHashCode()
            return result
        }
    }

    data class Remote(
        val key: String,
        val remoteUrl: String,
        var photoBytes: ByteArray
    ) : EventPhoto(remoteUrl) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Remote

            if (key != other.key) return false
            if (remoteUrl != other.remoteUrl) return false
            if (!photoBytes.contentEquals(other.photoBytes)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = key.hashCode()
            result = 31 * result + remoteUrl.hashCode()
            result = 31 * result + photoBytes.contentHashCode()
            return result
        }
    }
}
