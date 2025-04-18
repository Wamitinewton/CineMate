package com.newton.domain.repository

import androidx.paging.PagingData
import com.newton.core.enums.MovieCategory
import com.newton.core.utils.*
import com.newton.domain.models.FilmData
import com.newton.domain.models.FilmDetails
import kotlinx.coroutines.flow.*

interface MoviesRepository {
    suspend fun getMovieDetails(id: Int): Flow<Resource<FilmDetails>>

     fun getListOfMovies(movieCategory: MovieCategory): Flow<PagingData<FilmData>>

}