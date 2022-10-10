package com.example.pte_marauders_map;

import androidx.annotation.NonNull;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RadarMapView extends MapView implements OnMapReadyCallback {

    private OnMapReadyCallback mMapReadyCallback;
    private GoogleMap mGoogleMap;
    private Paint mPaintRadar;

    public RadarMapView(@NonNull Context context) {
        super(context);
        init();
    }

    public RadarMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarMapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, Objects.requireNonNull(attrs), defStyleAttr);
        init();
    }

    public RadarMapView(@NonNull Context context, @Nullable GoogleMapOptions options) {
        super(context, options);
        init();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        drawRadarOverTheMap(canvas);
        canvas.restore();
    }

    private void drawRadarOverTheMap(Canvas canvas) {
        if (mGoogleMap == null) {
            return;
        }

        final float centerX = getX() + (getWidth() >> 1);
        final float centerY = getY() + (getHeight() >> 1);

        canvas.drawCircle(centerX, centerY, 150, mPaintRadar);
        canvas.drawCircle(centerX, centerY, 300, mPaintRadar);
        canvas.drawCircle(centerX, centerY, 450, mPaintRadar);
    }

    private void init() {
        setWillNotDraw(false);

        mPaintRadar = new Paint();
        mPaintRadar.setColor(Color.GREEN);
        mPaintRadar.setStyle(Paint.Style.STROKE);
        mPaintRadar.setStrokeWidth(10);
    }

    @Override
    public void getMapAsync(@NonNull OnMapReadyCallback callback) {
        mMapReadyCallback = callback;
        super.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnCameraMoveListener(this::invalidate);
        if (mMapReadyCallback != null)
        {
            mMapReadyCallback.onMapReady(googleMap);
        }
    }

}