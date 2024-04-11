package com.ukejee.planetapp.util.extensions

import androidx.fragment.app.Fragment
import com.ukejee.planetapp.R

fun Fragment.replaceFragment(
    fragment: Fragment,
    containerId: Int = R.id.fragmentContainer,
    addToBackStack: Boolean = true
) {

    val transaction = requireActivity().supportFragmentManager.beginTransaction()
    transaction.replace(containerId, fragment)
    if (addToBackStack) {
        transaction.addToBackStack(fragment::class.java.simpleName)
    } else {
        transaction.addToBackStack(null)
    }
    transaction.commit()
}