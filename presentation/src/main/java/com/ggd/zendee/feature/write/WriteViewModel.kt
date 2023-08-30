package com.ggd.zendee.feature.write

import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.map.IssueTag

class WriteViewModel : BaseViewModel() {

    val tagDataSet = mutableListOf<IssueTag>(
        IssueTag.ALERT,
        IssueTag.HOT,
        IssueTag.HAPPY,
        IssueTag.LUCKY,
        IssueTag.NOTICE,
        IssueTag.ACTIVE,
        IssueTag.LOVE
    )

}