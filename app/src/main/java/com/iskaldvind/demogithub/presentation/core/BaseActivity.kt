package com.iskaldvind.demogithub.presentation.core

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes contentLayoutId: Int = 0): AppCompatActivity(contentLayoutId)