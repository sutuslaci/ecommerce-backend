package com.ecommerce.auth.utils;

import java.text.Normalizer;

public class ProductUtil {

    public static String generateHumanReadableURL(String productName) {
        return Normalizer
                .normalize(productName, Normalizer.Form.NFD)
                .replaceAll("\\s+","-")
                .toLowerCase();
    }
}
