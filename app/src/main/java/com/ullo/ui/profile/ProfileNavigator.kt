package com.ullo.ui.profile

import com.ullo.api.response.AppUser
import com.ullo.base.BaseNavigator


interface ProfileNavigator : BaseNavigator {
    fun onUpdateHandle()
    fun onUpdateSuccessful(data: AppUser)
}