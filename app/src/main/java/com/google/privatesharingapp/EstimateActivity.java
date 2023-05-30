package com.google.privatesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.pay.alipay.AliPay;
import com.android.pay.alipay.OnAliPayListener;
import com.android.pay.uupay.UUPay;
import com.android.pay.wechat.OnWeChatPayListener;
import com.android.pay.wechat.WeChatConstants;
import com.android.pay.wechat.WeChatPay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.google.privatesharingapp.bean.DeliveryBean;
import com.google.privatesharingapp.widget.DrivingRouteOverlay;
import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.luck.picture.lib.utils.ToastUtils;

public class EstimateActivity extends AppCompatActivity {
    private TextView pickUpLo, dropOffLo, fare, time;
    private Button bookNow, callDriver;
    private MapView mapView;
    RoutePlanSearch mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate);
        // Get the delivery details from the intent
        DeliveryBean deliveryBean = (DeliveryBean) getIntent().getSerializableExtra("delivery");
        // Find and initialize views
        pickUpLo = findViewById(R.id.pickUpLo);
        pickUpLo.setText("Pick up location：" + deliveryBean.getPickLocation());
        dropOffLo = findViewById(R.id.dropOffLo);
        dropOffLo.setText("Drop off location：" + deliveryBean.getDropLocation());
        fare = findViewById(R.id.fare);
        fare.setText("Approx.Fare：$80");
        time = findViewById(R.id.time);
        time.setText("Approx.Travel time：" + "40min");

        bookNow = findViewById(R.id.bookNow);
        // Set click listener for the Book Now button
        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomDialog.show(new OnBindView<BottomDialog>(R.layout.item_pay) {
                    @Override
                    public void onBind(BottomDialog dialog, View v) {
                        TextView wechat = v.findViewById(R.id.wechat);
                        // Set click listener for WeChat payment option
                        wechat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build the WeChatPay request with necessary parameters
                                WeChatPay.Builder builder = new WeChatPay.Builder(EstimateActivity.this);
                                builder.appId("xxxx");
                                builder.partnerId("xxx");
                                builder.prepayId("xxx");
                                builder.nonceStr("xxxx");
                                builder.timeStamp("xxxx");
                                builder.packageValue("Sign=WXPay");
                                builder.sign("xxxx");
                                builder.listener(new OnWeChatPayListener() {
                                    @Override
                                    public void onWeChatPay(int code, String msg) {
                                        if (code == WeChatConstants.SUCCEED) {//Paid successfully
                                            ToastUtils.showToast(EstimateActivity.this, "Paid successfully");
                                        }
                                        if (code == WeChatConstants.CANCEL) {//User canceled to pay
                                            ToastUtils.showToast(EstimateActivity.this, "Canceled payment");
                                        }
                                        if (code == WeChatConstants.FAILED) {//Pay failed
                                            ToastUtils.showToast(EstimateActivity.this, "Failed to pay");
                                        }
                                    }
                                });
                                builder.extData("Please pay");//Payment reminder text
                                builder.build();

                            }
                        });
                        TextView alipay = v.findViewById(R.id.alipay);
                        alipay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AliPay.Builder builder = new AliPay.Builder(EstimateActivity.this);
                                builder.orderInfo("xxxx");
                                builder.listener(new OnAliPayListener() {

                                    /**
                                     * Parameter Explanation
                                     *
                                     * @param status is the result code(type: string)。
                                     *       9000	Order paid successfully
                                     *       8000	Processing payment, payment result unknown (possibly already paid successfully), please check the payment status of the order in the merchant's order list
                                     *       4000	Order paid failed
                                     *       5000	Repeat request
                                     *       6001	User canceled
                                     *       6002	Network connection error
                                     *       6004	Payment result unknown (possibly already paid successfully), please check the payment status of the order in the merchant's order list
                                     *       Others	Other payment errors
                                     */
                                    @Override
                                    public void onAliPay(String status, String json, String description) {
                                        if (status.equals("9000")) {//Successful
                                            ToastUtils.showToast(EstimateActivity.this, "Paid successfully");
                                        } else if (status.equals("6001")) {//User canceled to pay
                                            ToastUtils.showToast(EstimateActivity.this, "Canceled payment");
                                        } else {//Pay failed
                                            ToastUtils.showToast(EstimateActivity.this, "Failed to pay");
                                        }
                                    }
                                });
                                builder.loading(true);
                                builder.build();

                            }
                        });
                        TextView union = v.findViewById(R.id.union);
                        union.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UUPay uuPay = new UUPay(EstimateActivity.this);
                                uuPay.pay("tn", UUPay.PayMode.FORM);

                            }
                        });
                    }
                });
            }
        });
        callDriver = findViewById(R.id.callDriver);
        callDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();        // Create an Intent
                intent.setAction(Intent.ACTION_CALL);        // Specify the action as make a call
                intent.setData(Uri.parse("tel:" + "0377778888"));    // Specify the calling number
                startActivity(intent);    // Perform this action
            }
        });

        mapView = findViewById(R.id.mapView);
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);

        PlanNode stNode = PlanNode.withLocation(new LatLng(Double.parseDouble(deliveryBean.getPickLocationLat()), Double.parseDouble(deliveryBean.getPickLocationLon())));
        PlanNode enNode = PlanNode.withLocation(new LatLng(Double.parseDouble(deliveryBean.getDropLocationLat()), Double.parseDouble(deliveryBean.getDropLocationLon())));

        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Execute mMapView. onPause () when activity executes onPause, to achieve map life cycle management
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Execute mMapView.onDestroy() when activity executes onDestroy, to achieve map life cycle management
        mapView.onDestroy();
        mapView.getMap().setMyLocationEnabled(false);
        mSearch.destroy();
    }

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            DrivingRouteOverlay overlay = new DrivingRouteOverlay(mapView.getMap());
            if (drivingRouteResult.getRouteLines().size() > 0) {
                // Get the route mapping data, (take the first route returned as an example)
                // Set data for DrivingRouteOverlay instance
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                // Draw DrivingRouteOverlay on the map
                overlay.addToMap();
                int hours = drivingRouteResult.getRouteLines().get(0).getDuration() / 3600;
                int minutes = (drivingRouteResult.getRouteLines().get(0).getDuration() % 3600) / 60;
                @SuppressLint("DefaultLocale") String timeString = String.format("%02d hours and %02d minutes", hours, minutes);
                time.setText("Approx.Travel time：" + timeString);
            }
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };
}