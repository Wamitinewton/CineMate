package com.newton.shows.data.repository

import com.newton.core.utils.Resource
import com.newton.network.data.mappers.toFilmDomainDetails
import com.newton.network.data.remote.FilmApiService
import com.newton.domain.models.FilmDetails
import com.newton.domain.repository.ShowsRepository
import com.newton.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val filmApiService: FilmApiService
): ShowsRepository {

    override suspend fun getShowsDetails(id: Int): Flow<Resource<FilmDetails>> =
        safeApiCall(
            apiCall = {
                val showDetails = filmApiService.getSeriesDetails(id).toFilmDomainDetails()
                showDetails
            }
        )
}