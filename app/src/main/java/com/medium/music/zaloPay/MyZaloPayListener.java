package com.medium.music.zaloPay;


import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class MyZaloPayListener implements PayOrderListener {
    @Override
    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
        // Xử lý khi thanh toán thành công
    }

    @Override
    public void onPaymentCanceled(String zpTransToken, String appTransID) {
        // Xử lý khi người dùng hủy thanh toán
    }

    @Override
    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
        // Xử lý khi có lỗi xảy ra
    }
}
