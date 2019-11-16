package com.maryang.fastrxjava.ui.repos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.maryang.fastrxjava.base.BaseViewModelActivity
import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.event.DataObserver
<<<<<<< HEAD
import io.reactivex.observers.DisposableObserver
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_github_repos.*


class GithubReposActivity : BaseViewModelActivity() {

    override val viewModel: GithubReposViewModel by lazy {
        GithubReposViewModel()
=======
import kotlinx.android.synthetic.main.activity_github_repos.*


class GithubReposActivity : BaseActivity(), GithubReposPresenter.View {

    private val presenter: GithubReposPresenter by lazy {
        GithubReposPresenter(this)
>>>>>>> ea9a77383e8dec1baf83cf1e113cfb71e7fc46fb
    }
    private val adapter: GithubReposAdapter by lazy {
        GithubReposAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.maryang.fastrxjava.R.layout.activity_github_repos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter

        refreshLayout.setOnRefreshListener { presenter.searchGithubRepos() }

        searchText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(text: Editable?) {
                presenter.searchGithubRepos(text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
<<<<<<< HEAD
        subscribeSearch()
        subscribeDataObserver()
    }

    private fun subscribeSearch() {
        compositeDisposable += viewModel.searchGithubReposSubject()
            .doOnNext {
                if (it) showLoading()
            }
            .switchMap { viewModel.searchGithubReposObservable() }
            .subscribeWith(object : DisposableObserver<List<GithubRepo>>() {
                override fun onNext(t: List<GithubRepo>) {
                    hideLoading()
                    adapter.items = t
                }
=======
>>>>>>> ea9a77383e8dec1baf83cf1e113cfb71e7fc46fb

        subscribeDataObserver()
    }

    private fun subscribeDataObserver() {
        compositeDisposable += DataObserver.observe()
            .filter { it is GithubRepo }
            .subscribe { repo ->
                adapter.items.find {
                    it.id == repo.id
                }?.apply {
                    star = star.not()
                }
                adapter.notifyDataSetChanged()
            }
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }

    override fun showRepos(repos: List<GithubRepo>) {
        adapter.items = repos
    }
}
