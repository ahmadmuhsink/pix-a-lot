package com.amk.pixalot.screen.home.viewphoto

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amk.pixalot.R
import com.amk.pixalot.common.AppConfig.INTENT_FULL_SCREEN_URL
import com.amk.pixalot.common.extension.getAndRemoveStringExtra
import kotlinx.android.synthetic.main.fragment_view_photo.highRestPhotoView

class ViewPhotoPage : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_view_photo,
            container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { act ->
            val fullscreenUrl = act.getAndRemoveStringExtra(INTENT_FULL_SCREEN_URL)
            highRestPhotoView.load(fullscreenUrl)
        }
    }
}
