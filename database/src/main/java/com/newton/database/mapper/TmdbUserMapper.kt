package com.newton.database.mapper

import com.newton.database.entity.TmdbUserEntity
import com.newton.domain.models.TmdbUser


fun TmdbUser.toEntity(): TmdbUserEntity {
    return TmdbUserEntity(
        id = id,
        username = username,
        name = name,
        avatarPath = avatarPath,
        includeAdult = includeAdult,
        language = language,
        country = country,
        updatedAt = System.currentTimeMillis()
    )
}

fun TmdbUserEntity.toDomain(): TmdbUser {
    return TmdbUser(
        id = id,
        username = username,
        name = name,
        avatarPath = avatarPath,
        includeAdult = includeAdult,
        language = language,
        country = country
    )
}