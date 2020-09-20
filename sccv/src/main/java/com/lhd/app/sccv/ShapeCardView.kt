package com.lhd.app.sccv

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout

class ShapeCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val rectView = RectF()
    private val paintCard = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val pathView = Path()
    private var cornersRadius = 0f
    private var shape = Shape.RECTANGLE

    init {
        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.ShapeCardView)
            paintCard.color =
                ta.getColor(R.styleable.ShapeCardView_sccv_background_color, Color.WHITE)
            cornersRadius = ta.getDimension(R.styleable.ShapeCardView_sccv_corners_radius, 0f)
            val shadowRadius = ta.getDimension(R.styleable.ShapeCardView_sccv_shadow_radius, 0f)
            if (shadowRadius > 0) {
                val shadowColor = ta.getColor(
                    R.styleable.ShapeCardView_sccv_shadow_color,
                    Color.parseColor("#88000000")
                )
                paintCard.setShadowLayer(shadowRadius, 0f, 0f, shadowColor)
            }

            val shapeValue = ta.getInt(R.styleable.ShapeCardView_sccv_shape, Shape.RECTANGLE.value)
            shape = when (shapeValue) {
                Shape.OVAL.value -> {
                    Shape.OVAL
                }
                Shape.RHOMBUS.value -> {
                    Shape.RHOMBUS
                }
                else -> {
                    Shape.RECTANGLE
                }
            }

            ta.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectView.set(0, 0, w, h)
        pathView.reset()
        when (shape) {
            Shape.OVAL -> {
                connectPathOval()
            }
            Shape.RHOMBUS -> {
                connectPathRhombus()
            }
            else -> {
                connectPathRectangle()
            }
        }

    }

    private fun connectPathRectangle() {
        pathView.addRoundRect(rectView, cornersRadius, cornersRadius, Path.Direction.CCW)
    }

    private fun connectPathOval() {
        pathView.addOval(rectView, Path.Direction.CCW)
    }

    private fun connectPathRhombus() {
        pathView.moveTo(rectView.left + cornersRadius / 2f, rectView.centerY() - cornersRadius / 2f)
        pathView.lineTo(rectView.centerX() - cornersRadius / 2f, rectView.top + cornersRadius / 2f)
        pathView.quadTo(
            rectView.centerX(),
            rectView.top,
            rectView.centerX() + cornersRadius / 2f,
            rectView.top + cornersRadius / 2f
        )
        pathView.lineTo(
            rectView.right - cornersRadius / 2f,
            rectView.centerY() - cornersRadius / 2f
        )
        pathView.quadTo(
            rectView.right,
            rectView.centerY(),
            rectView.right - cornersRadius / 2f,
            rectView.centerY() + cornersRadius / 2f
        )
        pathView.lineTo(
            rectView.centerX() + cornersRadius / 2f,
            rectView.bottom - cornersRadius / 2f
        )
        pathView.quadTo(
            rectView.centerX(),
            rectView.bottom,
            rectView.centerX() - cornersRadius / 2f,
            rectView.bottom - cornersRadius / 2f
        )
        pathView.lineTo(rectView.left + cornersRadius/2f, rectView.centerY() + cornersRadius/2f)
        pathView.quadTo(rectView.left, rectView.centerY(), rectView.left + cornersRadius / 2f, rectView.centerY() - cornersRadius / 2f)
    }

    enum class Shape(var value: Int) {
        RECTANGLE(0), OVAL(1), RHOMBUS(2)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let { canvas ->
            canvas.drawPath(pathView, paintCard)
            canvas.clipPath(pathView)
        }
        super.onDraw(canvas)
    }
}