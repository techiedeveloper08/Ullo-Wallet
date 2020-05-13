package com.ullo.ui.tutorial

import android.app.Application
import com.ullo.api.service.UlloService
import com.ullo.base.BaseViewModel
import com.ullo.db.DataManager
import com.ullo.utils.Session


class TutorialViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<TutorialNavigator>(application, ulloService, session, dataManager) {

    fun onNextButtonClick() {
        getNavigator()?.onNextHandle()
    }

    fun onGetStartedButtonClick(){
        getNavigator()?.onGetStartedHandle()
    }
}