package com.adithyaharun.footballclub.UI.Presenter

import android.accounts.NetworkErrorException
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.UI.MainView
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    private
    lateinit var view: MainView


    @Mock
    private lateinit var mainView: MainView

    @Mock
    private lateinit var apiRepository: DataRepository

    private lateinit var presenter: MainPresenter

    @Before @Throws
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler({ Schedulers.trampoline()})
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(mainView, apiRepository)
    }

    @Test fun testEventsReturnsError() {
        `when`(apiRepository.getPrevEvent())
                .thenReturn(Observable.error(NetworkErrorException()))

        presenter.getLastMatches()

        // View is never called
        verify(view, never()).showMatchList(any())
    }
}