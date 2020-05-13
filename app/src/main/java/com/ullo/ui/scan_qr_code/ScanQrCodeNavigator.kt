package com.ullo.ui.scan_qr_code

import com.ullo.base.BaseNavigator


interface ScanQrCodeNavigator : BaseNavigator {
    fun onCloseHandle()
    fun onSendHandle()
}