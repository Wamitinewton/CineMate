package com.newton.network.data.mappers

import com.newton.core.utils.ImageBaseUrl
import com.newton.network.data.dto.PeopleResponseDto
import com.newton.network.domain.models.PeopleData

fun PeopleResponseDto.toDomainPeople(): PeopleData {
    return PeopleData(
        adult = adult,
        gender = gender,
        id = id,
        knownFor = known_for?.toAllTrendingDomainList(),
        department = known_for_department,
        mediaType = media_type,
        name = name,
        originalName = original_name,
        popularity = popularity,
        profilePath = profile_path?.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" }
    )
}

fun List<PeopleResponseDto>.toDomainPeopleList(): List<PeopleData> =
    map { it.toDomainPeople() }