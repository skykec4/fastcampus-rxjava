package com.maryang.fastrxjava

import com.maryang.fastrxjava.data.repository.GithubRepository
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.ui.repos.GithubReposPresenter
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GithubReposPresenterTest {

    private lateinit var presenter: GithubReposPresenter
    @Mock
    lateinit var view: GithubReposPresenter.View
    @Mock
    lateinit var githubRepository: GithubRepository
    private val repos: List<GithubRepo> = emptyList()
    private val searchText = "searchText"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter = GithubReposPresenter(view, githubRepository)

        Mockito.`when`(githubRepository.searchGithubRepos(Mockito.anyString()))
            .thenReturn(Single.just(repos))
        Mockito.`when`(githubRepository.checkStar(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(Completable.complete())
    }

    @Test
    fun searchTest() {
        presenter.searchGithubRepos(searchText)
        Mockito.verify(view).showRepos(repos)
    }

    @Test
    fun searchSubjectTest() {
        val test = presenter.searchSubject()
            .test()
        presenter.searchGithubRepos(searchText)
        test.assertValue {
            it == repos
        }
    }
}
