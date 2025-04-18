package com.newton.domain.repository

import com.newton.core.utils.Resource
import com.newton.domain.models.PeopleDetails
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {

    suspend fun getPeopleDetails(id: Int): Flow<Resource<PeopleDetails>>
}