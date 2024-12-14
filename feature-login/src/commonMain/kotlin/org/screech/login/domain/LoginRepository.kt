package org.screech.login.domain

import com.atproto.server.CreateSessionResponse

interface LoginRepository {

    suspend fun createSession(identifier: String, password: String): Result<CreateSessionResponse>
}