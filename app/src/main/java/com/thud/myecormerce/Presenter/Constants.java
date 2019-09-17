/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thud.myecormerce.Presenter;

import com.google.android.gms.wallet.WalletConstants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class Constants {

  public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;
  public static final String CLIENT_ID = "Af7ZFGvyWDzgtlUxc8CzV4mncaL8ydXgV9TMR1cHdvIGPWhANh_BNPJB16iQ-TH6DTZVyQidR10ANhhE";


  public static final List<String> SUPPORTED_NETWORKS = Arrays.asList(
    "AMEX",
    "DISCOVER",
    "JCB",
    "MASTERCARD",
    "VISA");


  public static final List<String> SUPPORTED_METHODS =
      Arrays.asList(
          "PAN_ONLY",
          "CRYPTOGRAM_3DS");

  public static final String COUNTRY_CODE = "VN";

  public static final String CURRENCY_CODE = "VND";

  public static final List<String> SHIPPING_SUPPORTED_COUNTRIES = Arrays.asList("VND", "US");

  public static final String PAYMENT_GATEWAY_TOKENIZATION_NAME = "example";

  public static final HashMap<String, String> PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS =
      new HashMap<String, String>() {
        {
          put("gateway", PAYMENT_GATEWAY_TOKENIZATION_NAME);
          put("gatewayMerchantId", "exampleGatewayMerchantId");
          // Your processor may require additional parameters.
        }
      };
  public static final String DIRECT_TOKENIZATION_PUBLIC_KEY = "REPLACE_ME";

  public static final HashMap<String, String> DIRECT_TOKENIZATION_PARAMETERS =
      new HashMap<String, String>() {
        {
          put("protocolVersion", "ECv2");
          put("publicKey", DIRECT_TOKENIZATION_PUBLIC_KEY);
        }
      };

  private Constants() {}
}
