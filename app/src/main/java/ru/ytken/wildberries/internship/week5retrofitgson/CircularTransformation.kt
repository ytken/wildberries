package ru.ytken.wildberries.internship.week5retrofitgson

import android.graphics.*
import com.squareup.picasso.Transformation

class CircularTransformation: Transformation {

    override fun transform(source: Bitmap?): Bitmap {
        val output = Bitmap.createBitmap(source!!.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        val rect = Rect(0,0,source.width, source.height)

        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true

        canvas.drawARGB(0,0,0,0)
        paint.color = Color.WHITE

        canvas.drawCircle((source.width/2).toFloat(),
            (source.height/2).toFloat(), (source.width/2).toFloat(), paint)

        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(source, rect, rect, paint)

        if (source != output)
            source.recycle()
        return output
    }

    override fun key(): String {
        return "circular"
    }
}