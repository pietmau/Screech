package org.screech.login.presentation.domain

import com.atproto.server.CreateSessionResponse
import org.screech.data.network.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {

    suspend fun execute(params: Params): Result<CreateSessionResponse> {
        return loginRepository.createSession(params.username, params.password)
    }

    data class Params(val username: String, val password: String)
}