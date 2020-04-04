package com.ajkelley.imguralbumproject.repositories

import android.util.Log
import com.ajkelley.imguralbumproject.App
import com.ajkelley.imguralbumproject.web.ApiResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(requestFunc: suspend () -> Response<T>): T? {

        val result : ApiResult<T> = safeApiResult(requestFunc)
        var data : T? = null

        when(result) {
            is ApiResult.Success ->
                data = result.data
            is ApiResult.Error -> {
                Log.d(this.javaClass.simpleName, "Exception - ${result.exception}")
            }
        }

        return data

    }

    suspend private fun <T: Any> safeApiResult(requestFunc: suspend () -> Response<T>) : ApiResult<T>{
        try{
            val response = Result.success(requestFunc.invoke()).getOrNull()
            return ApiResult.Success(response!!.body()!!)
        }catch(e: Exception){
            return ApiResult.Error(e)
        }
    }
}