package com.miller.futurechat.presentation.conversations

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.miller.futurechat.R
import com.miller.futurechat.presentation.conversations.topicfollowing.TopicFollowingFragment
import com.miller.futurechat.presentation.conversations.topicnewest.TopicNewestFragment

class TopicPagerAdapter(
    private val ctx: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TopicFollowingFragment.newInstance()
            1 -> TopicNewestFragment.newInstance()
            else -> TopicFollowingFragment.newInstance()
        }
    }

    override fun getCount(): Int = NUM_ITEMS

    override fun getPageTitle(position: Int): CharSequence? {
        val titles = ctx.resources.getStringArray(R.array.conversation_pages)
        return titles[position]
    }

    companion object {
        const val NUM_ITEMS = 2
    }

}