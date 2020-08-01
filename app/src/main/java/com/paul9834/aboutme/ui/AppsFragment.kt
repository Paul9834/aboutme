package com.paul9834.aboutme.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.billingclient.api.*
import com.paul9834.aboutme.R
import kotlinx.android.synthetic.main.fragment_apps.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AppsFragment : Fragment(), PurchasesUpdatedListener {


    private lateinit var billingClient: BillingClient

    private val skuList = listOf("test_product_one", "test_product_two")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    fun setupBilling () {
        billingClient = BillingClient.newBuilder(requireContext()).setListener(this).build()
        billingClient.startConnection(object :BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult:BillingResult){
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready.
                    // You can query purchases here.
                }
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    suspend fun querySkuDetails() {
        val skuList = ArrayList<String>()
        skuList.add("premium_loot_box")
        skuList.add("premium_item")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        val skuDetailsResult = withContext(Dispatchers.IO) {
            billingClient.querySkuDetails(params.build())
        }
        // Process the result.
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {


    }




}