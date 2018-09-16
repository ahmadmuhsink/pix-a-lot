package com.amk.pixalot.screen.home.photolist

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.amk.pixalot.R
import com.amk.pixalot.arch.UserAction
import com.amk.pixalot.common.AppConfig.INTENT_FULL_SCREEN_URL
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_photo_list.listPhotoView
import kotlinx.android.synthetic.main.fragment_photo_list.progressBar
import kotlinx.android.synthetic.main.fragment_photo_list.swipeContainer
import kotlinx.android.synthetic.main.fragment_photo_list.view.swipeContainer
import org.koin.android.ext.android.inject

class PhotoListPage : Fragment(), PhotoListContract.View {

    private val presenter: PhotoListContract.Presenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_photo_list,
            container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { swipeContainer.setColorSchemeColors(ContextCompat.getColor(it, R.color.brickRed)) }
        presenter.attach(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.load()
    }

    override fun actions(): Observable<out UserAction> {
        return swipeContainer.refreshes().map { PhotoListAction.ActionRefresh }
    }

    private fun hideLoading() {
        swipeContainer.isRefreshing = false
        progressBar.visibility = View.INVISIBLE
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showData(model: PhotoListViewModel): Observable<out UserAction> {
        hideLoading()
        return listPhotoView.bind(model.images)
    }

    override fun showError(@StringRes message: Int) {
        hideLoading()
        Toast.makeText(context, context?.getString(message).orEmpty(), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun openFullScreenView(url: String) {
        view?.let { view ->
            activity?.intent?.putExtra(INTENT_FULL_SCREEN_URL, url)
            Navigation.findNavController(view).navigate(R.id.actionGoToViewPhotoPage)
        }
    }
}

sealed class PhotoListAction : UserAction {
    object ActionRefresh : PhotoListAction()
    object ActionLoadMore : PhotoListAction()
}