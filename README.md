![Logo](https://github.com/msusman1/compose-card-stack/blob/master/images/home_banner.png)

# Compose Card Stack

![Platform](http://img.shields.io/badge/platform-android-blue.svg?style=flat)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-23%2B-blue.svg?style=flat)
[![](https://jitpack.io/v/msusman1/compose-card-stack.svg)](https://jitpack.io/#msusman1/compose-card-stack)

# Overview
Timber like card stack library for Compose, motivation from CardStackView for android view(imperative UI).
[CardStackView](https://github.com/yuyakaido/CardStackView)

![Overview](https://github.com/msusman1/compose-card-stack/blob/master/images/overview.gif)

# Installation

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```groovy
dependencies { 
    implementation "com.github.msusman1:compose-card-stack:1.0.1"
}
```

#Setup



```kotlin
    CardStack(
        modifier = Modifier.padding(16.dp),
        stackState = rememberStackState(),
        cardElevation = 10.dp,
        scaleRatio = 0.95f,
        rotationMaxDegree = 15,
        displacementThreshold = 60.dp,
        animationDuration = Duration.NORMAL,
        visibleCount = 3,
        stackDirection = Direction.BottomAndRight,
        swipeDirection = SwipeDirection.FREEDOM,
        swipeMethod = SwipeMethod.AUTOMATIC_AND_MANUAL,
        items = items,
        onSwiped = { index ->
            Log.d(TAG, "onSwiped index:$index ")
        }
    ) {
        MyImageCard(item = it)  // your card Composable
    }

```

# Features

## Stack Direction
Card stack direction [Default: Direction.Bottom]

![Stack Direction](https://github.com/msusman1/compose-card-stack/blob/master/images/stack_direction.png)

## Visibility Count
No of cards to be visible below the top card Range:(from = 0, to = items size ) [Default: 3]

![Visibility Count](https://github.com/msusman1/compose-card-stack/blob/master/images/visibility_count.png)

## Card Elevation
Determine how much portion of underline cards will be displayed [Default: 10]

![Visibility Count](https://github.com/msusman1/compose-card-stack/blob/master/images/elevation.png)

## Card Scale Ratio
Determine how much underline cards be scale down(from 0.0 to 1.0) value fo 0.95f means 90% of original size, [Default: 0.95]

![Visibility Count](https://github.com/msusman1/compose-card-stack/blob/master/images/scale.png)

## Displacement Thresh hold
Displacement thresh hold value in Dp to make the card swipe, [Default: 60.dp]

  
## Animation Duration  
Duration of the card animation [Default:  Duration.NORMAL]

## Maximum Rotation degree
Maximum Degree at which card can rotate [Default: 20]

## Swipe Direction
Direction at which card can move [Default: SwipeDirection.FREEDOM]

## Swipe Method
Set swipe method either manual(through code) or automatic(through swipe) [Default: SwipeMethod.AUTOMATIC_AND_MANUAL]


<br/>

## 👍 How to Contribute
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
4. Push to the branch (git push origin my-new-feature)
5. Create new Pull Request

<br/>

# License

```
Copyright 2023 yuyakaido

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```