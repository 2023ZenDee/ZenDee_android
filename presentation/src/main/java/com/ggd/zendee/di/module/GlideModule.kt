package com.ggd.zendee.di.module

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule: AppGlideModule() {
    // 이게 있어야 특정 에러?가 안 뜸
}