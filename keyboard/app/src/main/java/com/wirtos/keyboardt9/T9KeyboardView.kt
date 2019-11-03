@file:Suppress("DEPRECATION")

package com.wirtos.keyboardt9

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.StateListDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet

@Suppress("DEPRECATION")
class T9KeyboardView(context: Context?, attrs: AttributeSet?) : KeyboardView(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var sb = StringBuilder()

    private val globeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_language)
    private val shiftIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_shift_filled)

    init {
        paint.textAlign = Paint.Align.CENTER
        paint.color = Color.WHITE
        paint.strokeWidth = 1f
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawColor(resources.getColor(R.color.background))

        val npd = context.resources.getDrawable(R.drawable.key_selector, null) as StateListDrawable

        val keys = keyboard.keys
        for (key in keys) {
            npd.setBounds(key.x, key.y, key.x + key.width, key.y + key.height)
            val drawableState = key.currentDrawableState
            npd.state = drawableState
            npd.draw(canvas)

            paint.textSize = 32f

            var x0 = (key.x + key.width / 2).toFloat()

            if (key.edgeFlags and Keyboard.EDGE_LEFT == Keyboard.EDGE_LEFT) {
                x0 += 20
            }
            if (key.edgeFlags and Keyboard.EDGE_RIGHT == Keyboard.EDGE_RIGHT) {
                x0 -= 20
            }

            if (key.label != null) {
                canvas.drawText(
                    key.label.toString(),
                    x0,
                    (key.y + key.height / 2 - 5).toFloat(), paint
                )
                var s: String
                sb.setLength(0)
                for (code in key.codes) {
                    if (code > 0 && (code < 48 || code > 57) && code != 32) {
                        sb.append(code.toChar())
                    }
                }
                s = sb.toString()

                if (s.isNotEmpty()) {
                    if (isShifted) {
                        s = s.toUpperCase()
                    }
                    paint.textSize = 20f
                    canvas.drawText(
                        s,
                        x0,
                        (key.y + key.height - 10).toFloat(), paint
                    )
                }
            } else {
                if (key.icon is BitmapDrawable) {
                    val bitmap: Bitmap = if (key.codes[0] == Keyboard.KEYCODE_SHIFT) {
                        if (isShifted) {
                            shiftIcon
                        } else {
                            (key.icon as BitmapDrawable).bitmap
                        }
                    } else {
                        (key.icon as BitmapDrawable).bitmap
                    }
                    var x1 = (key.x + key.width / 2 - bitmap.width / 2).toFloat()
                    var y1 = (key.y + key.height / 2 - bitmap.height / 2).toFloat()

                    if (key.edgeFlags and Keyboard.EDGE_LEFT == Keyboard.EDGE_LEFT) {
                        x1 += 25
                    }
                    if (key.edgeFlags and Keyboard.EDGE_RIGHT == Keyboard.EDGE_RIGHT) {
                        x1 -= 35
                        y1 -= 10
                    }

                    canvas.drawBitmap(bitmap, x1, y1, null)
                    //key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height)
                    //key.icon.draw(canvas)

                    if (key.codes[0] == Keyboard.KEYCODE_SHIFT) {

                        val x2 = (key.x + key.width / 2 - bitmap.width / 2 - 15).toFloat()
                        val y2 = (key.y + key.height / 2 - bitmap.height / 2).toFloat()
                        canvas.drawBitmap(globeIcon, x2, y2, null)
                    }
                }
            }
        }
    }

    override fun onLongPress(popupKey: Keyboard.Key?): Boolean {
        if ((popupKey?.codes?.get(0) ?: -1) == Keyboard.KEYCODE_SHIFT) {
            onKeyboardActionListener.onKey(Keyboard.KEYCODE_MODE_CHANGE, null)
            return true
        }
        // if key is special code or space(one without label) then act like long press
        else if ((popupKey?.codes?.get(0)
                ?: -1) < 0 || (popupKey?.codes?.get(0) == 32 && popupKey.label == null)
        ) {
            return super.onLongPress(popupKey)
        } else if (popupKey != null) {
            onKeyboardActionListener.onText(popupKey.label)
        }
        return true
    }
}