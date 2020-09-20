package com.lhd.app.sccv

import android.graphics.Rect
import android.graphics.RectF
import android.util.Log

fun RectF.set(l:Number,t:Number,r:Number,b:Number){
    set(l.toFloat(),t.toFloat(),r.toFloat(),b.toFloat())
}

fun Rect.set(l:Number, t:Number, r:Number, b:Number){
    set(l.toInt(),t.toInt(),r.toInt(),b.toInt())
}

fun RectF.setCenter(cX:Number,cY:Number,sizeX:Number,sizeY:Number){
    val centerX = cX.toFloat()
    val centerY = cY.toFloat()
    val width = sizeX.toFloat()
    val height = sizeY.toFloat()
    set(centerX-width/2f,centerY-height/2f,centerX+width/2f,centerY+height/2f)
}

fun RectF.setCenterX(cX:Number,sizeX:Number){
    val centerX = cX.toFloat()
    val width = sizeX.toFloat()
    set(centerX-width/2f,top,centerX+width/2f,bottom)
}

fun Rect.setCenter(cX:Number,cY:Number,sizeX:Number,sizeY:Number){
    val centerX = cX.toInt()
    val centerY = cY.toInt()
    val width = sizeX.toInt()
    val height = sizeY.toInt()
    set(centerX-width/2f,centerY-height/2,centerX+width/2,centerY+height/2)
}

var isLogEnable = true
val TAG_LOG = "APP_LOG"
fun eLog(message:String){
    TAG_LOG.eLog(message)
}

fun String.eLog(message: String){
    if (isLogEnable)
        Log.e(this,message)
}

fun Float.abs():Float{
    return kotlin.math.abs(this)
}

fun Int.abs():Int{
    return kotlin.math.abs(this)
}
