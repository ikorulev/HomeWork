package ru.ikorulev.homework.presentation.view

import ru.ikorulev.homework.R

class Flavor {

    fun getNavItem(): Int {
        var nav = 0
        when (MainActivity.navItem) {
            R.id.nav_list -> {
                nav = 1
            }
            R.id.nav_watch_later -> {
                nav = 3
            }
            R.id.nav_download -> {
                nav = 4
            }
            R.id.nav_clear -> {
                nav = 5
            }
        }
        return nav
    }
}