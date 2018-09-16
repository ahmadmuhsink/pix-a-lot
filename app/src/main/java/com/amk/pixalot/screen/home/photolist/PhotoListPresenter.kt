package com.amk.pixalot.screen.home.photolist

import com.amk.pixalot.R
import com.amk.pixalot.arch.SingleDisposable
import com.amk.pixalot.arch.UserAction
import com.amk.pixalot.common.ui.PhotoPreviewAction
import com.amk.pixalot.domain.workflow.PhotoListWorkflow
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class PhotoListPresenter(
        private val workflow: PhotoListWorkflow
) : PhotoListContract.Presenter() {

    private var fetchDisposable: Disposable? by SingleDisposable()
    private var showDataDisposable: Disposable? by SingleDisposable()

    override fun onAttach() {
        view()?.let { observe { it.actions().subscribe(::onUserAction) } }
    }

    override fun load() = fetchData { workflow.load() }

    private fun loadMore() = fetchData { workflow.loadMore() }

    private fun fetchData(fetch: () -> Observable<PhotoListViewModel>) {
        view()?.showLoading()
        fetchDisposable = fetch().subscribe(
                { data ->
                    showDataDisposable = view()
                            ?.showData(data)
                            ?.subscribe(::onUserAction)
                },
                {
                    view()?.showError(R.string.error_cannot_fetch)
                }
        )
    }

    private fun onUserAction(action: UserAction) {
        when (action) {
            is PhotoPreviewAction.ActionFullImage -> view()
                    ?.openFullScreenView(action.fullscreenUrl)

            is PhotoListAction.ActionRefresh -> load()

            is PhotoListAction.ActionLoadMore -> loadMore()

            else -> {
            }
        }
    }

    override fun onDetach() {
        fetchDisposable?.dispose()
        showDataDisposable?.dispose()
    }
}


