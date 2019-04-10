package com.seok.seok.wowsup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.seok.seok.wowsup.retrofit.model.ResponseStore;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{
    private BillingProcessor billingProcessor;
    public static SkuDetails token1000, token2000, token3000;
    private int token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);
        billingProcessor = new BillingProcessor(this, getString(R.string.in_app_license_key), this);
        //구글 플레이 스토어에 이니셜라이징
        billingProcessor.initialize();
    }

    @OnClick(R.id.store_ibtn_1000)
    void buy1000() {
        token = 10;
        purchaseProduct(token1000.productId);
    }

    @OnClick(R.id.store_ibtn_2000)
    void buy2000() {
        token = 30;
        purchaseProduct(token2000.productId);
    }

    @OnClick(R.id.store_ibtn_3000)
    void buy3000() {
        token = 50;
        purchaseProduct(token3000.productId);
    }

    @OnClick(R.id.store_ibtn_back)
    void goBack() {
        finish();
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        ApiUtils.getStoreService().updateToken(GlobalWowSup.getInstance().getId(), token).enqueue(new Callback<ResponseStore>() {
            @Override
            public void onResponse(Call<ResponseStore> call, Response<ResponseStore> response) {
                if(response.isSuccessful()){
                    ResponseStore body = response.body();
                    if(body.getState()==0){
                        Toast.makeText(StoreActivity.this, token + " tokens are charged. Thank you!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseStore> call, Throwable t) {
                Log.d("WowSup_store_HTTP", "http trans Failed");
            }
        });
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {
        token1000 = (SkuDetails) billingProcessor.getPurchaseListingDetails("sup_token_1000");
        token2000 = (SkuDetails) billingProcessor.getPurchaseListingDetails("sup_token_2000");
        token3000 = (SkuDetails) billingProcessor.getPurchaseListingDetails("sup_token_3000");
    }
    //콜백 결과
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //구매 아이디
    public void purchaseProduct(final String productId) {
        if (billingProcessor.isPurchased(productId)) {
            billingProcessor.consumePurchase(productId);
        }
        billingProcessor.purchase(StoreActivity.this, productId); // 결제창 띄움
    }
}
