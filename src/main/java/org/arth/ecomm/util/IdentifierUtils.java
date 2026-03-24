package org.arth.ecomm.util;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class IdentifierUtils {

    public static String generateShortOrderId() {
        UUID uuid = UUID.randomUUID();

        // 1. Wrap the UUID into a 16-byte array
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        // 2. Encode to Base64 (using URL-safe encoder to avoid '/' or '+')
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bb.array());
    }
}
