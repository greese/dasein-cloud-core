package org.dasein.cloud.compute;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Tag;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation of auto scaling support
 * User: Eugene Yaroslavtsev
 * Date: 31.07.2014
 */
public abstract class AbstractAutoScalingSupport implements AutoScalingSupport {
    private CloudProvider provider;

    public AbstractAutoScalingSupport(@Nonnull CloudProvider provider) {
        this.provider = provider;
    }

    @Override
    public void setTags(@Nonnull String providerScalingGroupId, @Nonnull AutoScalingTag... tags) throws CloudException, InternalException {
        setTags(new String[]{providerScalingGroupId}, tags);
    }

    @Override
    public void setTags(@Nonnull String[] providerScalingGroupIds, @Nonnull AutoScalingTag... tags) throws CloudException, InternalException {
        for (String vmId : providerScalingGroupIds) {
            Collection<AutoScalingTag> collectionForDelete = getTagsForDelete(getTags(vmId), tags);

            if (collectionForDelete != null) {
                removeTags(new String[]{vmId}, collectionForDelete.toArray(new AutoScalingTag[collectionForDelete.size()]));
            }

            updateTags(new String[]{vmId}, tags);
        }
    }

    private Collection<AutoScalingTag> getTagsForDelete(Collection<AutoScalingTag> all, Tag[] tags ) {
        Collection<AutoScalingTag> result = null;
        if (all != null) {
            result = new ArrayList<AutoScalingTag>();
            for (AutoScalingTag tag : all) {
                if (!isTagInTags(tag, tags)) {
                    result.add(tag);
                }
            }
        }
        return result;
    }

    static public boolean isTagInTags(Tag tag, Tag[] tags) {
        for (Tag t : tags) {
            if (t.getKey().equals(tag.getKey())) {
                return true;
            }
        }
        return false;
    }

}
