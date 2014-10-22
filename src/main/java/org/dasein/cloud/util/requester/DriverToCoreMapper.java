package org.dasein.cloud.util.requester;

/**
 * Created by Vlad_Munthiu on 10/17/2014.
 */
public interface DriverToCoreMapper<T, V> {
    V mapFrom(T entity);
}

