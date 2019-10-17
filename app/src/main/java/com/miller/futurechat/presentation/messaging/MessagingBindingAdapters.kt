package com.miller.futurechat.presentation.messaging

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.miller.futurechat.R
import com.miller.futurechat.presentation.model.type.OwnerType
import com.miller.futurechat.presentation.model.type.OwnerType.Me
import com.miller.futurechat.presentation.model.type.RelativePositionType
import com.miller.futurechat.presentation.model.type.RelativePositionType.*

/**
 * Created by Miller on 16/10/2019
 */

@BindingAdapter(value = ["relativePosition", "ownerType"])
fun TextView.setMessageBackground(relativePosition: RelativePositionType?, ownerType: OwnerType?) {
    relativePosition ?: return
    ownerType ?: return
    background = when (relativePosition) {
        is Top -> resources.getDrawable(
            if (ownerType is Me) R.drawable.messaging_bg_message_me_top else R.drawable.messaging_bg_message_other_top,
            null
        )
        is Mid -> resources.getDrawable(
            if (ownerType is Me) R.drawable.messaging_bg_message_me_mid else R.drawable.messaging_bg_message_other_mid,
            null
        )
        is Bot -> resources.getDrawable(
            if (ownerType is Me) R.drawable.messaging_bg_message_me_bot else R.drawable.messaging_bg_message_other_bot,
            null
        )
        is Single -> resources.getDrawable(
            if (ownerType is Me) R.drawable.messaging_bg_message_me_single else R.drawable.messaging_bg_message_other_single,
            null
        )
    }
}