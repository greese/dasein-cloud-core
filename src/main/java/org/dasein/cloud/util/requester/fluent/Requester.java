package org.dasein.cloud.util.requester.fluent;

import org.dasein.cloud.CloudException;

/**
 * Created by Vlad_Munthiu on 10/20/2014.
 */
public interface Requester<T>{
    T execute() throws CloudException;
}
