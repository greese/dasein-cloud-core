package org.dasein.cloud.identity;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Requirement;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * An abstract implementation of the Dasein Cloud Shell Key Support. Implementation classes override selected
 * methods to bootstrap concrete implementations.
 * @author Stas Maksimov (stas.maksimov@enstratius.com)
 * @since 2015.05
 * @version 2015.05 - initial version
 */
public abstract class AbstractShellKeySupport<T extends CloudProvider> extends AbstractProviderService<T> implements ShellKeySupport {

    protected AbstractShellKeySupport(T provider) {
        super(provider);
    }

    @Deprecated
    @Override
    public @Nonnull String getProviderTermForKeypair(@Nonnull Locale locale) {
        try {
            return getCapabilities().getProviderTermForKeypair(locale);
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Deprecated
    @Override
    public Requirement getKeyImportSupport() throws CloudException, InternalException {
        return getCapabilities().identifyKeyImportRequirement();
    }
}
