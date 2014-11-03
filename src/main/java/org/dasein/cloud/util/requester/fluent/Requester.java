package org.dasein.cloud.util.requester.fluent;

import org.dasein.cloud.CloudException;

 /**
 * @author Vlad Munthiu
 */
public interface Requester<T>{
    T execute() throws CloudException;
}
