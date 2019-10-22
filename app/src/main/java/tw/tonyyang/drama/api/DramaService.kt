package tw.tonyyang.drama.api

import io.reactivex.Observable
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
        fun getService(): DramaService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DramaService::class.java)
        }
    }
}