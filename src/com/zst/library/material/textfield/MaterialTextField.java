package com.zst.library.material.textfield;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;

public class MaterialTextField extends EditText {
	
	private boolean isAnimationRunning;
	
	// Customization
	private int mThemeColor = 0xFF1a237e; // Material Design Indigo Color
	private int mAnimDurationBar = 400;
	private int mAnimDurationFlash = 250;
	
	// Animation Variables
	private NinePatchDrawable bgDrawable;
	private NinePatchDrawable barDrawable;
	private int barLeft, barWidth;
	private Paint flashPaint;
	int flashLeft, flashRight;
	
	
	public MaterialTextField(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		String theme_color_attr = attrs.getAttributeValue(null, "themeColor");
		if (theme_color_attr != null) {
			mThemeColor = Color.parseColor(theme_color_attr);
		}
		String bar_anim_dur_attr = attrs.getAttributeValue(null, "barAnimDuration");
		if (bar_anim_dur_attr != null) {
			mAnimDurationBar = Integer.parseInt(bar_anim_dur_attr);
		}
		String flash_anim_dur_attr = attrs.getAttributeValue(null, "flashAnimDuration");
		if (flash_anim_dur_attr != null) {
			mAnimDurationBar = Integer.parseInt(flash_anim_dur_attr);
		}
		
		setup();
	}
	public MaterialTextField(Context context) {
		super(context);
		setup();
	}
	
	private void setup() {
		flashPaint = new Paint();
		flashPaint.setColor(mThemeColor);
		flashPaint.setAlpha((int) (0.85f * 255));
		
		setBackgroundResource(0);
		bgDrawable = (NinePatchDrawable) getResources().getDrawable(R.drawable.textfield_normal);
		barDrawable = (NinePatchDrawable) getResources().getDrawable(R.drawable.textfield_activated);
		barDrawable.setColorFilter(mThemeColor, PorterDuff.Mode.SRC_ATOP);
		
		setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					isAnimationRunning = true;
					
					// Animate Bar
					final ObjectAnimator bar_animator = ObjectAnimator.ofFloat(
							MaterialTextField.this, "barPosition", 0f, 1.02f);
					bar_animator.setInterpolator(new DecelerateInterpolator());
					bar_animator.setDuration(mAnimDurationBar);
					bar_animator.start();
					
					// Animate Flash
					final ObjectAnimator flash_animator = ObjectAnimator.ofFloat(
							MaterialTextField.this, "flashPosition", 0f, 1f);
					flash_animator.setInterpolator(new DecelerateInterpolator());
					flash_animator.setStartDelay((int) (mAnimDurationBar * 0.35f));
					flash_animator.setDuration(mAnimDurationFlash);
					flash_animator.start();
				} else {
					isAnimationRunning = false;
					// Remove animation items
					setBarPosition(0);
					setFlashPosition(0);
				}
			}
		});
	}
	
	/**
	 * Set variables for drawing the bottom bar
	 */
	private void setBarPosition(final float percentage) {
		if (percentage == 0) {
			barLeft = 0;
			barWidth = 0;
		} else {
			int width = getWidth();
			barWidth = Math.round(percentage * width);
			barLeft = Math.round((1 - percentage) * width);
		}
		invalidate();
	}
	
	/**
	 * Set variables for drawing the flash
	 */
	private void setFlashPosition(final float percentage) {
		if (percentage == 0) {
			flashLeft = 0;
			flashRight = 0;
		} else {
			float width = getWidth() * 0.4f;
			
			float right_pos = (1 - percentage) * width;
			
			float left_percentage = Math.min(percentage / 0.8f, 1f);
			float left_pos = (1 - left_percentage) * width * 0.5f;
			
			flashLeft = Math.round(left_pos);
			flashRight = Math.round(right_pos);
		}
		invalidate();
	}
	
	
	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);
		
		// Always draw default bar
		bgDrawable.setBounds(0, 0, (int) (getWidth() * 1.02f), getHeight());
		bgDrawable.draw(canvas);
		
		// Draw the animated bottom bar
		if (barWidth != 0 && isAnimationRunning) {
			barDrawable.setBounds(barLeft, 0, barWidth, getHeight());
			barDrawable.draw(canvas);
		}
		
		// Draw the flash
		if (flashRight - flashLeft > 0 && isAnimationRunning) {
			canvas.drawRect(
					getCompoundPaddingLeft() + flashLeft, // Left
					getCompoundPaddingTop(), // Top
					getCompoundPaddingLeft() + flashRight, // Right
					getHeight() - getCompoundPaddingBottom(), // Bottom
					flashPaint);
		}
	}
	
	
	/**
	 * Change the color of the flash and bar animation.
	 * @param color
	 */
	public void setThemeColor(int color) {
		if (color != mThemeColor) {
			mThemeColor = color;
			barDrawable.setColorFilter(mThemeColor, PorterDuff.Mode.SRC_ATOP);
			flashPaint.setColor(mThemeColor);
			invalidate();
		}
	}
	
	/**
	 * Change the duration of the bar animation when text field is focused
	 * @param duration in milliseconds
	 */
	public void setBarAnimationDuration(int duration) {
		if (duration > 0) {
			mAnimDurationBar = duration;
		}
	}
	
	/**
	 * Change the duration of the flash animation when text field is focused
	 * @param duration in milliseconds
	 */
	public void setFlashAnimationDuration(int duration) {
		if (duration > 0) {
			mAnimDurationFlash = duration;
		}
	}
}
