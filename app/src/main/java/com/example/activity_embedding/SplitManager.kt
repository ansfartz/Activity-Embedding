package com.example.activity_embedding

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.window.embedding.ActivityFilter
import androidx.window.embedding.ActivityRule
import androidx.window.embedding.EmbeddingAspectRatio
import androidx.window.embedding.RuleController
import androidx.window.embedding.SplitAttributes
import androidx.window.embedding.SplitPairFilter
import androidx.window.embedding.SplitPairRule
import androidx.window.embedding.SplitPlaceholderRule
import androidx.window.embedding.SplitRule

class SplitManager {

    companion object {
        fun createSplit(context: Context) {

            val ruleController = RuleController.getInstance(context)

            // Split rule
            val splitPairFilter = SplitPairFilter(
                primaryActivityName = ComponentName(context, ListActivity::class.java),
                secondaryActivityName = ComponentName(context, DetailActivity::class.java),
                secondaryActivityIntentAction = null
            )

            val filterSet = setOf(splitPairFilter)

            val splitAttributes: SplitAttributes = SplitAttributes.Builder()
                .setSplitType(SplitAttributes.SplitType.ratio(0.33f))
                .setLayoutDirection(SplitAttributes.LayoutDirection.LEFT_TO_RIGHT)
                .build()

            val splitPairRule = SplitPairRule.Builder(filterSet)
                .setDefaultSplitAttributes(splitAttributes)
                .setMinWidthDp(840)
                .setMinSmallestWidthDp(600)
                .setFinishPrimaryWithSecondary(SplitRule.FinishBehavior.NEVER)
                .setFinishSecondaryWithPrimary(SplitRule.FinishBehavior.ALWAYS)
                .setClearTop(false)
                // .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.6f))                   // add either of these in splitPairRule and splitPlaceholderRule to enable split in portrait for Pixel C (which has width=900 but ratio=1.422)
                // .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ALWAYS_ALLOW)
                .build()

            ruleController.addRule(splitPairRule)


            // Placeholder rule
            val placeholderActivityFilter = ActivityFilter(
                ComponentName(context, ListActivity::class.java),
                intentAction = null
            )

            val placeholderActivityFilterSet = setOf(placeholderActivityFilter)

            val splitPlaceholderRule = SplitPlaceholderRule.Builder(
                filters=placeholderActivityFilterSet,
                placeholderIntent = Intent(context, PlaceholderActivity::class.java)
            )
                .setDefaultSplitAttributes(splitAttributes)
                .setMinWidthDp(840)
                .setMinSmallestWidthDp(600)
                .setFinishPrimaryWithPlaceholder(SplitRule.FinishBehavior.ALWAYS)
                // .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ratio(1.6f))                   // add either of these in splitPairRule and splitPlaceholderRule to enable split in portrait for Pixel C (which has width=900 but ratio=1.422)
                // .setMaxAspectRatioInPortrait(EmbeddingAspectRatio.ALWAYS_ALLOW)
                .build()

            ruleController.addRule(splitPlaceholderRule)


            // Activity Rule
            //  - Used to define a rule for an Activity that occupies the entire task window, such as a modal dialog.
            val summaryActivityFilter = ActivityFilter(
                componentName = ComponentName(context, SummaryActivity::class.java),
                intentAction = null
            )

            val summaryActivityFilterSet = setOf(summaryActivityFilter)

            val activityRule = ActivityRule.Builder(summaryActivityFilterSet)
                .setAlwaysExpand(true)
                .build()

            ruleController.addRule(activityRule)
        }
    }

}