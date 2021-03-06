package com.reinventiva.sticket.ui.newticketsuper

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.PlaceViewModel

private val TAB_TITLES = arrayOf(R.string.tab_text_1, R.string.tab_text_2)

class NewTicketSuperPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position == 0)
            return NewTicketSuperMapTabFragment()
        else
            return NewTicketSuperListTabFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = TAB_TITLES.size
}