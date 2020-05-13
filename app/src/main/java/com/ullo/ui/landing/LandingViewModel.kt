package com.ullo.ui.landing

import android.app.Application
import com.ullo.api.service.UlloService
import com.ullo.base.BaseViewModel
import com.ullo.db.DataManager
import com.ullo.utils.Session


class LandingViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<LandingNavigator>(application, ulloService, session, dataManager) {

    fun onLoginButtonClick() {
        getNavigator()!!.onLoginScreen()
    }

    fun onRegisterButtonClick() {
        getNavigator()!!.onRegisterScreen()
    }
}