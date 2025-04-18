package com.newton.people.data.repository

import com.newton.core.utils.Resource
import com.newton.domain.models.PeopleDetails
import com.newton.domain.repository.PeopleRepository
import com.newton.network.data.mappers.toDomainPeopleDetails
import com.newton.network.data.remote.FilmApiService
import com.newton.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeopleDetailsRepositoryImpl @Inject constructor(
    private val filmApiService: FilmApiService
): PeopleRepository {
    override suspend fun getPeopleDetails(id: Int): Flow<Resource<PeopleDetails>> =
        safeApiCall(
            apiCall = {
                val response = filmApiService.getPeopleDetails(id).toDomainPeopleDetails()
                response
            }
        )
}