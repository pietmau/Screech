package org.screech.login.data

import com.atproto.server.CreateSessionRequest
import com.atproto.server.CreateSessionResponse
import org.screech.ScreechClient
import org.screech.login.domain.LoginRepository

class LoginRepositoryImpl(private val client: ScreechClient) : LoginRepository {

    override suspend fun createSession(
        identifier: String,
        password: String,
    ): Result<CreateSessionResponse> {
       return client.createSession(CreateSessionRequest(identifier, password))
    }
}