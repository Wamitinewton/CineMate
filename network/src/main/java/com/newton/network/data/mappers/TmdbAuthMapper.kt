package com.newton.network.data.mappers

import com.newton.core.utils.ImageBaseUrl
import com.newton.domain.models.*
import com.newton.network.data.dto.*

fun RequestTokenDto.toDomain(): TmdbRequestToken {
    return TmdbRequestToken(
        success = success,
        expiresAt = expires_at,
        requestToken = request_token
    )
}

fun SessionDto.toDomain(): TmdbSession {
    return TmdbSession(
        success = success,
        sessionId = session_id
    )
}

fun AccountDetailsDto.toDomain(): TmdbUser {
    val avatarPath = avatar?.tmdb?.avatar_path?.let {
        "${ImageBaseUrl.IMAGE_BASE_URL}$it"
    } ?: avatar?.gravatar?.hash?.let { hash ->
        "https://www.gravatar.com/avatar/$hash"
    }

    return TmdbUser(
        id = id,
        username = username,
        name = name,
        avatarPath = avatarPath,
        includeAdult = include_adult,
        language = iso_639_1,
        country = iso_3166_1
    )
}