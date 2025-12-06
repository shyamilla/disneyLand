package com.pro;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;

import java.math.BigDecimal;

public class RazorpayUtil {

    private static final String KEY = "rzp_test_EUe62RJUHfTuFZ";     // Replace with actual Razorpay test key
    private static final String SECRET = "x285wDpmW6MUMLaI9Rk031qw";        // Replace with actual Razorpay secret

    public static Order createOrder(BigDecimal amount) throws Exception {
        RazorpayClient client = new RazorpayClient(KEY, SECRET);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount.multiply(new BigDecimal(100)).intValue()); // in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        return client.orders.create(orderRequest);
    }
}
