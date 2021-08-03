package com.example.licenta.smartdoctor.utils.common;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class AnimationHelper {
    private static final float TRANSPARENT = 0f;
    private static final float OPAQUE = 1f;

    public static void crossFadeAnimation(final View fadeInTarget, final View fadeOutTarget, long duration){
        AnimatorSet mAnimationSet = new AnimatorSet();
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(fadeOutTarget, View.ALPHA,  OPAQUE, TRANSPARENT);
        fadeOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fadeOutTarget.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        fadeOut.setInterpolator(new LinearInterpolator());

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(fadeInTarget, View.ALPHA, TRANSPARENT, OPAQUE);
        fadeIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                fadeInTarget.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        fadeIn.setInterpolator(new LinearInterpolator());
        mAnimationSet.setDuration(duration);
        mAnimationSet.playTogether(fadeOut, fadeIn);
        mAnimationSet.start();
    }

    public static void fadeOutAnimation(final View fadeOutTarget, long duration) {
        AnimatorSet mAnimationSet = new AnimatorSet();
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(fadeOutTarget, View.ALPHA,  OPAQUE, TRANSPARENT);
        fadeOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fadeOutTarget.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        fadeOut.setInterpolator(new LinearInterpolator());
        fadeOut.setDuration(duration);
        fadeOut.start();
    }

    public static void fadeInAnimation(final View fadeInTarget, long duration) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(fadeInTarget, View.ALPHA, TRANSPARENT, OPAQUE);
        fadeIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                fadeInTarget.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {}

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        fadeIn.setInterpolator(new LinearInterpolator());
        fadeIn.setDuration(duration);
        fadeIn.start();
    }
}
