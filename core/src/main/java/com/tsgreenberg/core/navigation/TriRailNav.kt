package com.tsgreenberg.core.navigation


abstract class TriRailNavImplementor<T> : TriRailNav {
    abstract var navController: T
}

interface TriRailNav {
    fun navigate(routeAction: TriRailRootAction)
//    fun <T : Any> getArgsFromBackstack(route: String, key: String): T
}


