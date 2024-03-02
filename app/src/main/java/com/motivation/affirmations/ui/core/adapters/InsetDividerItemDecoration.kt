package com.motivation.affirmations.ui.core.adapters

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.InsetDrawable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 25.07.2022.
 */
class InsetDividerItemDecoration(
    private val context: Context,
    orientation: Int
) : DividerItemDecoration(context, orientation) {

    fun setInsets(insetLeft: Int, insetTop: Int, insetRight: Int, insetBottom: Int) {
        val attrs = intArrayOf(android.R.attr.listDivider)
        val typedArray = context.obtainStyledAttributes(attrs)
        val divider = typedArray.getDrawable(0)
        val insetDivider = InsetDrawable(divider, insetLeft, insetTop, insetRight, insetBottom)

        typedArray.recycle()
        setDrawable(insetDivider)
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val divider = drawable
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        divider?.let { drawable ->
            val childCount = parent.childCount
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + drawable.intrinsicHeight

                drawable.setBounds(left, top, right, bottom)
                drawable.draw(c)
            }
        }
    }
}
