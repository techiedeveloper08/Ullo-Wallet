package com.ullo.ui.scan_qr_code

import android.app.Application
import com.ullo.api.service.UlloService
import com.ullo.base.BaseViewModel
import com.ullo.db.DataManager
import com.ullo.utils.Session


class ScanQrCodeViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<ScanQrCodeNavigator>(application, ulloService, session, dataManager) {

    fun onCloseButtonClick(){
        getNavigator()?.onCloseHandle()
    }

    fun onSendButtonClick(){
        getNavigator()?.onSendHandle()
    }
}