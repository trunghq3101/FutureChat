package com.miller.futurechat.presentation.conversations

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.miller.futurechat.presentation.conversations.topicfollowing.TopicFollowingFragment

class TopicPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TopicFollowingFragment.newInstance()
            else -> TopicFollowingFragment.newInstance()
        }
    }

    override fun getCount(): Int = NUM_ITEMS

    companion object {
        const val NUM_ITEMS = 1
    }

}