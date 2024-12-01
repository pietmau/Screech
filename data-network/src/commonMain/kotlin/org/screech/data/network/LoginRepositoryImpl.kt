package org.screech.data.network

import com.atproto.server.CreateSessionRequest
import com.atproto.server.CreateSessionResponse
import org.screech.ScreechClient

class LoginRepositoryImpl(private val client: ScreechClient) : LoginRepository {

    override suspend fun createSession(
        identifier: String,
        password: String,
    ): Result<CreateSessionResponse> {
       return client.createSession(CreateSessionRequest(identifier, password))
    }
}