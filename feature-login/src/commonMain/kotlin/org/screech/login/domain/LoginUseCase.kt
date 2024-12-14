package org.screech.login.domain

import com.atproto.server.CreateSessionResponse

class LoginUseCase(private val loginRepository: LoginRepository) {

    suspend fun execute(params: Params): Result<CreateSessionResponse> {
        return loginRepository.createSession(params.username, params.password)
    }

    data class Params(val username: String, val password: String)
}