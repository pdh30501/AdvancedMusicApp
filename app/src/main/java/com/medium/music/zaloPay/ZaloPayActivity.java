package com.medium.music.zaloPay;

import static com.medium.music.constant.Constant.FIREBASE_URL;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.medium.music.MyApplication;
import com.medium.music.R;
import com.medium.music.activity.MainActivity;
import com.medium.music.model.User;
import com.medium.music.prefs.DataStoreManager;
import com.medium.music.zaloPay.api.CreateOrder;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ZaloPayActivity extends AppCompatActivity {
    TextView lblZpTransToken, txtToken;
    Button btnCreateOrder, btnPay;
    EditText txtAmount;

    private void BindView() {
        txtToken = findViewById(R.id.txtToken);
        lblZpTransToken = findViewById(R.id.lblZpTransToken);
        btnCreateOrder = findViewById(R.id.btnCreateOrder);
        txtAmount = findViewById(R.id.txtAmount);
        btnPay = findViewById(R.id.btnPay);
        IsLoading();
    }

    private void IsLoading() {
        lblZpTransToken.setVisibility(View.INVISIBLE);
        txtToken.setVisibility(View.INVISIBLE);
        btnPay.setVisibility(View.INVISIBLE);
    }

    private void IsDone() {
        lblZpTransToken.setVisibility(View.VISIBLE);
        txtToken.setVisibility(View.VISIBLE);
        btnPay.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zalo);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(553, Environment.SANDBOX);
        // bind components with ids
        BindView();
        // handle CreateOrder
        btnCreateOrder.setOnClickListener(v -> {
            CreateOrder orderApi = new CreateOrder();

            try {
                JSONObject data = orderApi.createOrder(txtAmount.getText().toString());
                Log.d("Amount", txtAmount.getText().toString());
                lblZpTransToken.setVisibility(View.VISIBLE);
                String code = data.getString("returncode");

                if (code.equals("1")) {
                    lblZpTransToken.setText("zptranstoken");
                    txtToken.setText(data.getString("zptranstoken"));
                    IsDone();

                    setPremiumAccount(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        btnPay.setOnClickListener(v -> {
            String token = txtToken.getText().toString();
            ZaloPaySDK.getInstance().payOrder(this, token, "musiccapp://app", new PayOrderListener() {
                @Override
                public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(ZaloPayActivity.this)
                                    .setTitle("Payment Success")
                                    .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // thanh cong
                                            // 1. Đặt tài khoản thành premium
//                                            setPremiumAccount(true);

                                        // 2. Điều hướng người dùng
                                            Intent intent = new Intent(ZaloPayActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Cancel", null).show();
                        }

                    });
//                    IsLoading();
                }

                @Override
                public void onPaymentCanceled(String zpTransToken, String appTransID) {
                    new AlertDialog.Builder(ZaloPayActivity.this)
                            .setTitle("User Cancel Payment")
                            .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                            .setPositiveButton("OK", (dialog, which) -> {
                            })
                            .setNegativeButton("Cancel", null).show();

                    Intent intent = new Intent(ZaloPayActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                    new AlertDialog.Builder(ZaloPayActivity.this)
                            .setTitle("Payment Fail")
                            .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                            .setPositiveButton("OK", (dialog, which) -> {
                            })
                            .setNegativeButton("Cancel", null).show();

                    Intent intent = new Intent(ZaloPayActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        });
    }

    private void setPremiumAccount(boolean isPremium) {
        if (isPremium) {
            String userEmail = DataStoreManager.getUser().getEmail();
            String userEmail_ = userEmail.replace(".", "_");
            FirebaseDatabase.getInstance(FIREBASE_URL).getReference("premiumUsers")
                    .child(userEmail_)
                    .setValue(true)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("ZaloPayActivity", "setPremiumAccount: success");
                            } else {
                                Log.d("ZaloPayActivity", "setPremiumAccount: failed");
                            }
                        }
                    });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}