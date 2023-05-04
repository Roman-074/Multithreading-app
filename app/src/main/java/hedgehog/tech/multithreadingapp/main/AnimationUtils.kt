package hedgehog.tech.multithreadingapp.main

import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

object AnimationUtils {
    fun setupAnimation(view: LottieAnimationView) {
        view.apply {
            setAnimation("infinity_animation.json")
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
    }
}
