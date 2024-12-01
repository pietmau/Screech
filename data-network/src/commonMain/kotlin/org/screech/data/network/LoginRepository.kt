package org.screech.data.network

import com.atproto.server.CreateSessionResponse

interface LoginRepository {

    suspend fun createSession(identifier: String, password: String): Result<CreateSessionResponse>
}