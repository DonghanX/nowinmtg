package com.donghanx.navigation.navkey

import androidx.navigation3.runtime.NavKey
import kotlin.reflect.KClass

val KClass<out NavKey>.routeName: String
    get() = simpleName ?: error("NavKey must be a named class")
