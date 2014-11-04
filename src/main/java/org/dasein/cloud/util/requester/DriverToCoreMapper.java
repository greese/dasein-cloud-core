package org.dasein.cloud.util.requester;

/**
 * DriverToCoreMapper
 *
 * @author Vlad Munthiu
 *
 * @param <T> driver model type
 * @param <V> core model type
 * */

 public interface DriverToCoreMapper<T, V> {
    V mapFrom(T entity);
}

