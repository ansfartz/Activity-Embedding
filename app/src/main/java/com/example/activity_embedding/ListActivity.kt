package com.example.activity_embedding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {
    companion object {
        const val TAG = "ANDYYY ListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listRecyclerView: RecyclerView = findViewById(R.id.listRecyclerView)
        val arraySize = 10
        listRecyclerView.adapter = ItemAdapter(
            Array(arraySize) { i ->
                if (i == arraySize - 1) "Summary"
                else "Item ${(i + 1)}"
            }
        )

        printMetrics()

        // The activity splits are controlled by display aspect ratio in addition to split minimum width.
        //      <SplitPairRule> - splitMaxAspectRatioInPortrait,    splitMaxAspectRatioInLandscape
        //      SplitRule       - maxAspectRatioInPortrait,         maxAspectRatioInLandscape
        //
        // If the aspect ratio (height:width) of a display exceeds the value `maxAxpectRatio` in either orientation, splits are disabled regardless of the width of the display.
        // The default max aspect ratio in portrait orientation is 1.4 (see SplitRule#SPLIT_MAX_ASPECT_RATIO_PORTRAIT_DEFAULT), which prevents tall, narrow displays from including splits.
        // By default, splits are always allowed in landscape orientation (see SplitRule#SPLIT_MAX_ASPECT_RATIO_LANDSCAPE_DEFAULT).
        //
        // Pixel C (Tablet)
        //      landscape
        //          height = 840dp
        //          width = 1280dp
        //          aspect ratio (height:width) = (32:21) = 1.523
        //      portrait
        //          height = 1220dp
        //          width = 900dp
        //          aspect ratio (height:width) = (61:45) = 1.355 > 1.4         ?????
        //
        //  The Pixel C (Tablet) emulator has a portrait width of 900dp, which is wider than the `splitMinWidthDp` setting in the sample app XML configuration file, so the emulator should show an activity split.
        //  But the aspect ratio of the Pixel C in portrait is greater than 1.4, which prevents activity splits from displaying in portrait orientation.
        //
        // 7 WSVGA (Tablet)
        //      landscape
        //          height = 540dp
        //          width = 1024dp
        //          aspect ratio (height:width) = (256:135) = 1.896
        //      portrait
        //          height = 964dp
        //          width = 600dp
        //          aspect ratio (height:width) = (241:150) = 1.606
        //
        // Pixel 6 (Phone)
        //      landscape
        //          height = 387.42856dp
        //          width = 865.5238dp
        //          aspect ratio (height:width) = (865:387) = 2.235
        //      portrait
        //          height = 841.5238dp
        //          width = 411.42856dp
        //          aspect ratio (height:width) = (841:411) = 2.046
    }

    private fun printMetrics() {
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val aspectRatioPair = getAspectRatio(dpHeight.toInt(), dpWidth.toInt())
        Log.d(TAG, "dpHeight = $dpHeight")
        Log.d(TAG, "dpWidth = $dpWidth")
        Log.d(
            TAG,
            "ratio = (${dpHeight} : ${dpWidth}) = (${aspectRatioPair.first} : ${aspectRatioPair.second}) = ${aspectRatioPair.first.toFloat() / aspectRatioPair.second.toFloat()}"
        )
    }

    private fun getAspectRatio(height: Int, width: Int): Pair<Int, Int> {
        val gcd = getGCD(height, width)
        if (height > width) {
            return Pair(height / gcd, width / gcd)
        } else {
            return Pair(width / gcd, height / gcd)
        }
    }

    /**
     * a should be greater than b
     */
    private fun getGCD(a: Int, b: Int): Int {
        return if (b == 0) a
        else getGCD(b, a % b)
    }
}