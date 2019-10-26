package tw.tonyyang.drama.api

import android.content.Context
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import tw.tonyyang.drama.BuildConfig
import tw.tonyyang.drama.model.DramaResponse

interface DramaService {
    @GET("/drama")
    fun searchDramas(): Observable<DramaResponse>

    companion object {
        fun getService(context: Context): DramaService {
            val cacheSize = 10 * 1024 * 1024 // 10 MB
            val cache = Cache(context.cacheDir, cacheSize.toLong())
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .cache(cache)
                .build()
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(DramaService::class.java)
        }
    }
}