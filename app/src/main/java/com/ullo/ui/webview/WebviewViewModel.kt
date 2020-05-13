package com.ullo.ui.webview

import android.app.Application
import com.ullo.api.service.UlloService
import com.ullo.base.BaseViewModel
import com.ullo.db.DataManager
import com.ullo.utils.Session


class WebviewViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<WebviewNavigator>(application, ulloService, session, dataManager) {

}