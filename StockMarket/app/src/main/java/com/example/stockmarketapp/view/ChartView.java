package com.example.stockmarketapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.stockmarketapp.stock.Chart;

import java.text.DecimalFormat;
import java.util.List;

public class ChartView extends View {
    List<Chart> data;
    float width, height, maxPrice, minPrice;
    Paint paint = new Paint();
    Paint strokePaint = new Paint();
    Paint stroke2Paint = new Paint();
    Paint textPaint = new Paint();
    float textHeight;
    Rect textBounds = new Rect();

    public ChartView(Context context, List<Chart> chartList) {
        super(context);
        this.data = chartList;
        updateMaxAndMin();
        strokePaint.setColor(Color.WHITE);
        stroke2Paint.setColor(Color.GRAY);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40f);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.getTextBounds("0", 0, 1, textBounds);
        textHeight = textBounds.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        float chartWidth = width - textPaint.measureText("1000");
        float rectWidth = chartWidth / data.size();
        strokePaint.setStrokeWidth(rectWidth / 6);
        float left = 0;
        float bottom, top;

        for (int i = 0; i < data.size(); i++) {
            Chart stockData = data.get(i);
            if (stockData.getClose() >= stockData.getOpen()) {
                paint.setColor(Color.GREEN);
                top = stockData.getClose();
                bottom = stockData.getOpen();
            }
            else {
                paint.setColor(Color.RED);
                top = stockData.getOpen();
                bottom = stockData.getClose();
            }
            canvas.drawLine(left + rectWidth / 2, getYPosition(stockData.getHigh()), left + rectWidth / 2, getYPosition(stockData.getLow()), strokePaint);
            canvas.drawRect(left, getYPosition(top), left + rectWidth, getYPosition(bottom), paint);
            left += rectWidth;
        }

        float iIncr = getIncrement(minPrice, maxPrice);
        System.out.println("I INCREMENT: " + iIncr);
        boolean showLabel = false;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);

        for (float i = minPrice; i < maxPrice; i += iIncr) {
            strokePaint.setStrokeWidth(1);
            if (showLabel) {
                canvas.drawText( df.format(i) + "", width, getYPosition(i) + textHeight / 2, textPaint);
                canvas.drawLine(0, getYPosition(i), chartWidth, getYPosition(i), strokePaint);   // Draws graph lines
            }
            else {
                canvas.drawLine(0, getYPosition(i), chartWidth, getYPosition(i), stroke2Paint);
            }
            showLabel = !showLabel;

        }
    }

    private float getYPosition(float price) {
        float scaleFactorY = ((price - minPrice) / (maxPrice - minPrice));
        return height - height * scaleFactorY; // Subtracted since 0, 0 on canvas starts in top left corner
    }

    private void updateMaxAndMin() {
        maxPrice = -1f;
        minPrice = 999999999f;
        for (Chart dayData: data) {
            if (dayData.getHigh() > maxPrice) {
                maxPrice = dayData.getHigh();
            }
            if (dayData.getLow() < minPrice) {
                minPrice = dayData.getLow();
            }
        }
    }

    private float getIncrement(float minPrice, float maxPrice) {
        float diff = maxPrice - minPrice;

        if (diff < 1) {
            return .20f;
        }
        else if (diff < 5) {
            return .25f;
        }
        else {
            return Math.round((maxPrice - minPrice) / 10);
        }
    }
}
