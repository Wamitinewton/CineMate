package com.newton.network.data.mappers

import com.newton.core.utils.*
import com.newton.domain.models.PeopleData
import com.newton.network.data.dto.*

fun PeopleResponseDto.toDomainPeople(): PeopleData {
    return PeopleData(
        adult = adult,
        id = id,
        name = name,
        originalName = original_name,
        profilePath = profile_path?.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" }
    )
}

fun List<PeopleResponseDto>.toDomainPeopleList(): List<PeopleData> =
    map { it.toDomainPeople() }