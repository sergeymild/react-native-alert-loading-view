/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reactnativealertloading;

import android.content.Context;
import android.view.animation.Animation;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Private class created to work around issues with AnimationListeners being
 * called before the animation is actually complete and support shadows on older
 * platforms.
 */
class CircleImageView extends AppCompatImageView {
    CircleImageView(Context context) {
        super(context);
    }
}
