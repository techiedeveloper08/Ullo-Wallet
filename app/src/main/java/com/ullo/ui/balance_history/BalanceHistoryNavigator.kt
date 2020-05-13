package com.ullo.ui.balance_history

import com.ullo.api.response.balance_history.BalanceHistoryData
import com.ullo.base.BaseNavigator

interface BalanceHistoryNavigator : BaseNavigator {
    fun onBalanceHistorySuccessfull(data: BalanceHistoryData)
}