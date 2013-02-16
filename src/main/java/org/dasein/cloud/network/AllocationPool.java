package org.dasein.cloud.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a block of IP addresses that may be allocated individually.
 * <p>Created by George Reese: 2/16/13 1:11 PM</p>
 * @author George Reese
 * @version 2013.04 initial version
 * @since 2013.04
 */
public class AllocationPool {
    static public AllocationPool getInstance(@Nonnull RawAddress ipStart, @Nonnull RawAddress ipEnd) {
        AllocationPool pool = new AllocationPool();

        pool.ipEnd = ipEnd;
        pool.ipStart = ipStart;
        return pool;
    }

    private RawAddress ipEnd;
    private RawAddress ipStart;

    private AllocationPool() { }

    @Override
    public boolean equals(@Nullable Object other) {
        if( other == null ) {
            return false;
        }
        if( other == this ) {
            return true;
        }
        if( !getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        AllocationPool pool = (AllocationPool)other;

        return (pool.ipStart.equals(ipStart) && pool.ipEnd.equals(ipEnd));
    }

    public RawAddress getIpEnd() {
        return ipEnd;
    }

    public RawAddress getIpStart() {
        return ipStart;
    }

    @Override
    public int hashCode() {
        return (ipStart.toString() + "<>" + ipEnd.toString()).hashCode();
    }

    @Override
    public String toString() {
        return ("[" + ipStart + " - " + ipEnd + "]");
    }
}
