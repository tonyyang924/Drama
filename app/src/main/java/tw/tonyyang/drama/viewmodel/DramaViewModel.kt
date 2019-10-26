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

class DramaViewModel : ViewModel() {

    private val dramaService by lazy {
        DramaService.getService(CoreApplication.getContext())
    }

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val dramaDao by lazy {
        AppDatabase.getInstance().dramaDao()
    }

    val dramaListLiveData: MutableLiveData<List<Drama>> = MutableLiveData()

    fun loadDramaList() {
        val cache = Observable.create<List<Drama>> {
            it.onNext(dramaDao.getAllDramas())
            it.onComplete()
        }
        val network = dramaService.searchDramas()
            .map {
                it.data
            }
        Observable.concat(cache, network)
            .filter { it.isNotEmpty() }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                dramaListLiveData.postValue(it)
                dramaDao.insertAll(it)
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