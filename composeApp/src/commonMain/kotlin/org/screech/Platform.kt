package org.screech

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform