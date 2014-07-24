package org.dasein.cloud.compute;

/**
 * Describes the types of VM lifecycle
 * <p>Created by Stas Maksimov: 20/06/2014 22:56</p>
 *
 * @author Stas Maksimov
 * @version 2014.07 initial version
 * @since 2014.07
 */
public enum VirtualMachineLifecycle {

    /**
     * VM started via launch or launchMany methods
     */
    NORMAL,

    /**
     * VM started via a spot VM request
     */
    SPOT,

}
