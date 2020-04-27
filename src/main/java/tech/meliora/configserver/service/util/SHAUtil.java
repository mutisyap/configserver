package tech.meliora.configserver.service.util;

import org.slf4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SHAUtil {
    private final String hashAlgorithm;
    private final String hashSecret;

    public SHAUtil(String hashAlgorithm, String hashSecret) {
        this.hashAlgorithm = hashAlgorithm;
        this.hashSecret = hashSecret;
    }

    private String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public boolean validate(String data, String signature, Logger logger) {
        try {
            String signatureValid = sign(data);
            return signatureValid.equals(signature);
        }catch (Exception ex){
            logger.warn("Unable to validate data", ex);
            return true;
        }
    }


    public String sign(String data) throws Exception {

        Mac mac;
        String result;

        byte[] byteKey = this.hashSecret.getBytes(StandardCharsets.UTF_8);

        mac = Mac.getInstance(this.hashAlgorithm);
        SecretKeySpec keySpec = new SecretKeySpec(byteKey, this.hashAlgorithm);
        mac.init(keySpec);
        byte[] mac_data = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        result = bytesToHex(mac_data);

        return result;
    }
}
