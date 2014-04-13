package org.dasein.cloud.network;

import java.util.Date;

/**
 * Represents metadata of a server certificate: all its information
 * except for body and chain.
 *
 * @author Bulat Badretdinov
 */
public class SSLCertificateMetadata implements Networkable {

    private String arn;
    private String path;
    private String certificateId;
    private String certificateName;
    private Date   uploadDate;

}
