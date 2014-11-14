package org.dasein.cloud.compute;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class SpotPrice {
    private long   timestamp;
    private float price;

    public static @Nonnull SpotPrice getInstance(@Nonnegative long timestamp, @Nonnegative float price){
        SpotPrice sp = new SpotPrice();
        sp.timestamp = timestamp;
        sp.price = price;
        return sp;
    }

    public @Nonnegative long getTimestamp(){
        return timestamp;
    }

    public @Nonnegative float getPrice(){
        return price;
    }
}
