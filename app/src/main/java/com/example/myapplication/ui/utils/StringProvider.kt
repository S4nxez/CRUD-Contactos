package com.example.myapplication.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringProvider @Inject constructor(@ApplicationContext val context: Context) {
    companion object {
        fun instance(context: Context): StringProvider = StringProvider(context)
    }

    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
}
