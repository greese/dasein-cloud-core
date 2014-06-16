package org.dasein.cloud.compute;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class SpotPrice {
    private long   timestamp;
    private String price;

    public static @Nonnull SpotPrice getInstance(@Nonnegative long timestamp, @Nonnull String price){
        SpotPrice sp = new SpotPrice();
        sp.timestamp = timestamp;
        sp.price = price;
        return sp;
    }

    public @Nonnegative long getTimestamp(){
        return timestamp;
    }

    public @Nonnull String getPrice(){
        return price;
    }
}
