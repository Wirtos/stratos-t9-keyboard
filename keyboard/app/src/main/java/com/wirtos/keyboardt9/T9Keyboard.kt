@file:Suppress("DEPRECATION")

package com.wirtos.keyboardt9

import android.content.Context
import android.inputmethodservice.Keyboard

class T9Keyboard(context: Context?, xmlLayoutResId: Int) : Keyboard(context, xmlLayoutResId) {
    val layoutId = xmlLayoutResId
}