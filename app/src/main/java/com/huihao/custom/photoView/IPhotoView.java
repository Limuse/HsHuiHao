/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.huihao.custom.photoView;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;


public interface IPhotoView {
    /**
     * Returns true if the PhotoView is set to allow zooming of Photos.
     *
     * @return true if the PhotoView allows zooming.
     */
    boolean canZoom();

    /**
     * Gets the Display Rectangle of the currently displayed Drawable. The
     * Rectangle is relative to this View and includes all scaling and
     * translations.
     *
     * @return - RectF of Displayed Drawable
     */
    RectF getDisplayRect();

    /**
     * Sets the Display Matrix of the currently displayed Drawable. The
     * Rectangle is considered relative to this View and includes all scaling and
     * translations.
     *
     * @return - true if rectangle was applied successfully
     */
    boolean setDisplayMatrix(Matrix finalMatrix);

    /**
     * Gets the Display Matrix of the currently displayed Drawable. The
     * Rectangle is considered relative to this View and includes all scaling and
     * translations.
     *
     * @return - true if rectangle was applied successfully
     */
    Matrix getDisplayMatrix();

    /**
     * Use {@link #getMinimumScale()} instead, this will be removed in future release
     *
     * @return The current minimum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    @Deprecated
    float getMinScale();

    /**
     * @return The current minimum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    float getMinimumScale();

    /**
     * Use {@link #getMediumScale()} instead, this will be removed in future release
     *
     * @return The current middle scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    @Deprecated
    float getMidScale();

    /**
     * @return The current medium scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    float getMediumScale();

    /**
     * Use {@link #getMaximumScale()} instead, this will be removed in future release
     *
     * @return The current maximum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    @Deprecated
    float getMaxScale();

    /**
     * @return The current maximum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    float getMaximumScale();

    /**
     * Returns the current scale value
     *
     * @return float - current scale value
     */
    float getScale();

    /**
     * Return the current scale type in use by the ImageView.
     */
    ImageView.ScaleType getScaleType();

    /**
     * Whether to allow the ImageView's parent to intercept the touch event when the photo is scroll to it's horizontal edge.
     */
    void setAllowParentInterceptOnEdge(boolean allow);

    /**
     * Use {@link #setMinimumScale(float minimumScale)} instead, this will be removed in future release
     * <p/>
     * Sets the minimum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    @Deprecated
    void setMinScale(float minScale);

    /**
     * Sets the minimum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    void setMinimumScale(float minimumScale);

    /**
     * Use {@link #setMediumScale(float mediumScale)} instead, this will be removed in future release
     * <p/>
     * Sets the middle scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    @Deprecated
    void setMidScale(float midScale);

    /*
     * Sets the medium scale level. What this value represents depends on the current {@link android.widget.ImageView.ScaleType}.
     */
    void setMediumScale(float mediumScale);

    /**
     * Use {@link #setMaximumScale(float maximumScale)} instead, this will be removed in future release
     * <p/>
     * Sets the maximum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    @Deprecated
    void setMaxScale(float maxScale);

    /**
     * Sets the maximum scale level. What this value represents depends on the current {@link ImageView.ScaleType}.
     */
    void setMaximumScale(float maximumScale);

    /**
     * Register a callback to be invoked when the Photo displayed by this view is long-pressed.
     *
     * @param listener - Listener to be registered.
     */
    void setOnLongClickListener(View.OnLongClickListener listener);

    /**
     * Register a callback to be invoked when the Matrix has changed for this
     * View. An example would be the user panning or scaling the Photo.
     *
     * @param listener - Listener to be registered.
     */
    void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener);

    /**
     * Register a callback to be invoked when the Photo displayed by this View
     * is tapped with a single tap.
     *
     * @param listener - Listener to be registered.
     */
    void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener);

    /**
     * Register a callback to be invoked when the View is tapped with a single
     * tap.
     *
     * @param listener - Listener to be registered.
     */
    void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener);

    /**
     * Changes the current scale to the specified value.
     *
     * @param scale - Value to scale to
     */
    void setScale(float scale);

    /**
     * Changes the current scale to the specified value.
     *
     * @param scale   - Value to scale to
     * @param animate - Whether to animate the scale
     */
    void setScale(float scale, boolean animate);

    /**
     * Changes the current scale to the specified value, around the given focal point.
     *
     * @param scale   - Value to scale to
     * @param focalX  - X Focus Point
     * @param focalY  - Y Focus Point
     * @param animate - Whether to animate the scale
     */
    void setScale(float scale, float focalX, float focalY, boolean animate);

    /**
     * Controls how the image should be resized or moved to match the size of
     * the ImageView. Any scaling or panning will happen within the confines of
     * this {@link ImageView.ScaleType}.
     *
     * @param scaleType - The desired scaling mode.
     */
    void setScaleType(ImageView.ScaleType scaleType);

    /**
     * Allows you to enable/disable the zoom functionality on the ImageView.
     * When disable the ImageView reverts to using the FIT_CENTER matrix.
     *
     * @param zoomable - Whether the zoom functionality is enabled.
     */
    void setZoomable(boolean zoomable);

    /**
     * Enables rotation via PhotoView internal functions.
     * Name is chosen so it won't collide with View.setRotation(float) in API since 11
     *
     * @param rotationDegree - Degree to rotate PhotoView by, should be in range 0 to 360
     */
    void setPhotoViewRotation(float rotationDegree);

}
