package org.dasein.cloud.identity;

import org.dasein.cloud.Capabilities;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * Describes the capabilities of Shell Keys within a cloud for a specific account.
 * Created by Drew Lyall: 10/11/2014 13:26
 * @author Drew Lyall
 * @since 2014.11
 * @version 2014.11
 */
public interface ShellKeyCapabilities extends Capabilities{
    /**
     * Indicates to what degree key importing vs. creation is supported. If importing is not supported, then that
     * means all keys must be created through the cloud provider via {@link ShellKeySupport#createKeypair(String)}. If importing is
     * required, it means all key creation must be done by importing your keys through {@link ShellKeySupport#importKeypair(String, String)}.
     * If optional, then you can either import or create keys. When you have the choice, it is always recommended that
     * you import keys.
     * @return the requirement state of importing key pairs
     * @throws org.dasein.cloud.CloudException an error occurred with the cloud provider in determining support
     * @throws org.dasein.cloud.InternalException a local error occurred while determining support
     */
    public Requirement getKeyImportSupport() throws CloudException, InternalException;

    /**
     * Provides the provider term for an SSH keypair.
     * @param locale the locale into which the term should be translated
     * @return the provider term for the SSH keypair, ideally translated for the specified locale
     */
    public @Nonnull String getProviderTermForKeypair(@Nonnull Locale locale);

    /**
     * Indicates whether the underlying cloud supports the creation of shell key pairs or whether it expects it to already be done and the keys simply provided
     * @return true is cloud does support generation
     * @throws org.dasein.cloud.CloudException an error occurred with the cloud provider in determining support
     * @throws org.dasein.cloud.InternalException a local error occurred while determining support
     */
    public boolean supportsKeyPairGeneration() throws CloudException, InternalException;
}
