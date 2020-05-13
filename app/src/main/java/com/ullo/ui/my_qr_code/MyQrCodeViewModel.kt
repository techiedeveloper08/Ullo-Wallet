package com.ullo.ui.my_qr_code

import android.app.Application
import com.ullo.api.service.UlloService
import com.ullo.base.BaseViewModel
import com.ullo.db.DataManager
import com.ullo.utils.Session


class MyQrCodeViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<MyQrCodeNavigator>(application, ulloService, session, dataManager) {

}