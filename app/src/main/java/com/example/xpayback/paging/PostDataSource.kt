package com.example.xpayback.paging



import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.xpayback.models.UsersResponse
import com.example.xpayback.models.UsersResponse.User
import com.example.xpayback.network.RetrofitClientFactory

/**
 * @author jeffin
 * @date 31/01/23
 */
class PostDataSource(private val retrofitClientFactory: RetrofitClientFactory):
    PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersResponse.User> {
        try {
            val currentLoadingPageKey = params.key ?: 0
            val response = retrofitClientFactory.restApis.getAllUser(PAGE_SIZE,
                currentLoadingPageKey* PAGE_SIZE)
            val responseData = mutableListOf<UsersResponse.User>()
            val data = response.body()?.users ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 0) null else currentLoadingPageKey - 1
            val nextKey = if (data.isEmpty()) null else currentLoadingPageKey + 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}
