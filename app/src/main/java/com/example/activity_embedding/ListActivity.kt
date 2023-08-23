/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.activity_embedding

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

/**
 * The list portion of a list-detail layout.
 */
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
            Array(arraySize) {
                    i -> if (i == arraySize - 1) "Summary"
                         else "Item ${(i + 1)}"
            }
        )

        printMetrics()
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

    // Pixel C (Tablet)
    //      landscape
    //          height = 900dp
    //          width = 1280dp
    //          aspect ratio (height:width) = (64:45) = 1.422
    //      portrait
    //          height = 1280dp
    //          width = 900dp
    //          aspect ratio (height:width) = (64:45) = 1422 > 1.4

    // Pixel 6 (Phone)
    //      landscape
    //          height = 387.42856dp
    //          width = 865.5238dp
    //          aspect ratio (height:width) = (865:387) = 2.235
    //      portrait
    //          height = 841.5238dp
    //          width = 411.42856dp
    //          aspect ratio (height:width) = (841:411) = 2.046

    // 7 WSVGA (Tablet)
    //      landscape
    //          height = 600dp
    //          width = 1024dp
    //          aspect ratio (height:width) = (128:75) = 1.706
    //      portrait
    //          height = 1024dp
    //          width = 600dp
    //          aspect ratio (height:width) = (128:75) = 1.706
}
