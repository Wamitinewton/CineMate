package com.newton.network.data.mappers

import com.newton.core.utils.ImageBaseUrl
import com.newton.domain.models.PeopleDetails
import com.newton.network.data.dto.PeopleDetailsDto

fun PeopleDetailsDto.toDomainPeopleDetails(): PeopleDetails{
    return PeopleDetails(
        adult = adult,
        alsoKnownAs = also_known_as,
        biography = biography,
        birthday = birthday,
        deathDay = deathday,
        gender = gender,
        homepage = homepage,
        id = id,
        knownForDepartment = known_for_department,
        name = name,
        placeOfBirth = place_of_birth,
        popularity = popularity,
        profilePath = profile_path?.let { "${ImageBaseUrl.IMAGE_BASE_URL}$it" }
    )
}