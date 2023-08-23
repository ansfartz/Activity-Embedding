package com.example.activity_embedding

import android.content.Context
import androidx.startup.Initializer
import androidx.window.embedding.RuleController

class SplitInitializer : Initializer<RuleController> {

    override fun create(context: Context): RuleController {
        val ruleController = RuleController.getInstance(context)

        ruleController.setRules(
            RuleController.parseRules(context, R.xml.main_split_config)
        )

        return ruleController
    }

    override fun dependencies(): List<Class<out Initializer<*>?>> {
        return emptyList()
    }
}