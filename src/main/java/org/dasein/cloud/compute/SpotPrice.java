package org.dasein.cloud.compute;

import javax.annotation.Nonnull;

public class SpotPrice{
    private long   timeStamp;
    private double price;

    public @Nonnull SpotPrice getInstance(long timeStamp, double price){
        SpotPrice sp = new SpotPrice();
        sp.timeStamp = timeStamp;
        sp.price = price;
        return sp;
    }

    public long getTimeStamp(){
        return timeStamp;
    }

    public double getPrice(){
        return price;
    }
}
