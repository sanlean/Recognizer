package com.example.sdk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class CameraOverlayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): View(context, attrs, defStyleAttr) {

    val aspectRatio: Float
    val horizontalMarginPercent: Float
    val topMarginPercent: Float
    val strokeWidth: Float
    val strokeColor: Int
    val backgroundColor: Int
    val overlayType: Int

    init{
        val typedArray = context.theme.obtainStyledAttributes(attrs,
            R.styleable.CameraOverlayView, 0, 0)
        aspectRatio = typedArray.getFloat(R.styleable.CameraOverlayView_aspectRatio, DEFAULT_DIMENSION)
        topMarginPercent = typedArray.getFloat(R.styleable.CameraOverlayView_topMarginPercent, DEFAULT_MARGIN_TOP_PERCENT)
        horizontalMarginPercent = typedArray.getFloat(R.styleable.CameraOverlayView_horizontalMarginPercent, DEFAULT_MARGIN_HORIZONTAL_PERCENT)
        strokeWidth = typedArray.getFloat(R.styleable.CameraOverlayView_widthStroke, DEFAULT_STROKE_WIDTH)
        strokeColor = typedArray.getColor(R.styleable.CameraOverlayView_colorStroke, DEFAULT_STROKE_COLOR)
        backgroundColor = typedArray.getColor(R.styleable.CameraOverlayView_colorBackground, DEFAULT_BACKGROUND_COLOR)
        overlayType = typedArray.getInt(R.styleable.CameraOverlayView_overlayType, TYPE_DOCUMENT)
    }

    override fun onDraw(canvas: Canvas) {
        var paint = Paint()
        var path = Path()
        paint.strokeWidth = 0f
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = backgroundColor

        val x0 = horizontalMarginPercent*width
        val x1 = width-(x0)
        val y0 = topMarginPercent*height
        val y1 = y0+((x1-x0)/aspectRatio)
        val frame = RectF(x0, y0,x1, y1)

        path.fillType = Path.FillType.INVERSE_EVEN_ODD
        if(overlayType== TYPE_FACE)
            drawFaceBackground(canvas, path, paint, frame)
        else
            drawDocumentBackground(canvas, path, paint, frame)

        paint.strokeWidth = strokeWidth
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.color = strokeColor

        val board = RectF(x0+1f, y0+1f,x1-1f, y1-1f)
        if (overlayType== TYPE_FACE)
            drawFaceStroke(canvas, board, paint)
        else
            drawDocumentStroke(canvas, board, paint)
    }

    fun getFrameRecF(): RectF{
        val x0 = horizontalMarginPercent*width
        val x1 = width-(x0)
        val y0 = topMarginPercent*height
        val y1 = y0+((x1-x0)/aspectRatio)
        return RectF(x0, y0,x1, y1)
    }

    private fun drawFaceBackground(canvas: Canvas, path: Path, paint: Paint, frame: RectF){
        path.addOval(frame, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }

    private fun drawFaceStroke(canvas: Canvas, board: RectF, paint: Paint){
        canvas.drawOval(board, paint)
    }

    private fun drawDocumentBackground(canvas: Canvas, path: Path, paint: Paint, frame: RectF){
        path.addRoundRect(frame, 60f, 60f, Path.Direction.CW)
        canvas.drawPath(path, paint)
    }

    private fun drawDocumentStroke(canvas: Canvas, board: RectF, paint: Paint){
        canvas.drawRoundRect(board, 55f, 55f, paint)
    }

    companion object{
        const val DEFAULT_DIMENSION: Float = 19F/14F
        const val DEFAULT_MARGIN_TOP_PERCENT = 0.2F
        const val DEFAULT_MARGIN_HORIZONTAL_PERCENT = 0.04F
        const val DEFAULT_STROKE_WIDTH = 15F
        const val DEFAULT_STROKE_COLOR = Color.WHITE
        const val DEFAULT_BACKGROUND_COLOR = Color.GRAY
        const val TYPE_FACE = 0
        const val TYPE_DOCUMENT = 1
    }
}