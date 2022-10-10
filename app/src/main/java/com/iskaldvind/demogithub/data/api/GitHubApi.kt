package com.iskaldvind.demogithub.data.api

import com.google.gson.GsonBuilder
import com.iskaldvind.demogithub.data.user.model.User
import io.reactivex.Single
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface IGitHubApi {

    companion object Factory {
        fun create(): IGitHubApi {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(GitHubApiInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
                .protocols(listOf(Protocol.HTTP_1_1))
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
            return retrofit.create(IGitHubApi::class.java)
        }
    }

    @GET("/users")
    fun fetchUsers(@Query("since") since: Int, @Query("per_page") limit: Int): Single<List<User>>

    @GET("/users/{username}")
    fun fetchUserByLogin(@Path("username") login: String): Single<User>
}