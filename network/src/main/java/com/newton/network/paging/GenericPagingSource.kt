package com.newton.network.paging

class GenericPagingSource<T : Any, R>(
    private val apiCall: suspend (Int) -> R,
    private val dataMapper: (R) -> List<T>,
    private val getNextPageNumber: (R) -> Int? = { null }
) : androidx.paging.PagingSource<Int, T>() {

    override fun getRefreshKey(state: androidx.paging.PagingState<Int, T>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val nextPage = params.key ?: 1

            val response = apiCall(nextPage)
            val items = dataMapper(response)

            LoadResult.Page(
                data = items,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = getNextPageNumber(response)
            )
        } catch (e: java.io.IOException) {
            LoadResult.Error(e)
        } catch (e: retrofit2.HttpException) {
            LoadResult.Error(e)
        }
    }
}
