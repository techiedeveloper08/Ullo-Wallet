package com.ullo.extensions

import androidx.viewpager.widget.ViewPager

val ViewPager.lastItem: Int?
    get() = adapter?.count?.minus(1)