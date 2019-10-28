package tw.tonyyang.drama.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import tw.tonyyang.drama.CoreApplication
import tw.tonyyang.drama.addTo
import tw.tonyyang.drama.api.DramaService
import tw.tonyyang.drama.db.AppDatabase
import tw.tonyyang.drama.model.Drama

class DramaDetailViewModel: ViewModel() {

    private val dramaService by lazy {
        DramaService.getService(CoreApplication.getContext())
    }

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val dramaDao by lazy {
        AppDatabase.getInstance().dramaDao()
    }

    val dramaLiveData: MutableLiveData<Drama> = MutableLiveData()

    fun loadDrama(drama: Drama) {
        dramaLiveData.value = drama
    }

    fun loadDrama(dramaId: Int) {
        val cache = Observable.create<Drama> {
            it.onNext(dramaDao.getDrama(dramaId))
            it.onComplete()
        }
        val network = dramaService.searchDrama(dramaId)
            .map {
                if (it.data.isNotEmpty()) {
                    it.data[0]
                } else {
                    null
                }
            }
        Observable.concat(cache, network)
            .filter { true }
            .map { it}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                dramaLiveData.postValue(it)
                dramaDao.insert(it)
            }, {
                Log.i(TAG, "" + it.localizedMessage)
            }).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        private val TAG = DramaViewModel::class.java.simpleName
    }
}