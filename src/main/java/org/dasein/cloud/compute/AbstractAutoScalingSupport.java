/**
 * Copyright (C) 2009-2015 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud.compute;

import org.dasein.cloud.AbstractProviderService;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.Tag;
import org.dasein.cloud.util.TagUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implementation of auto scaling support
 * User: Eugene Yaroslavtsev
 * Date: 31.07.2014
 */
public abstract class AbstractAutoScalingSupport<T extends CloudProvider> extends AbstractProviderService<T> implements AutoScalingSupport {
    protected AbstractAutoScalingSupport(T provider) {
        super(provider);
    }

    @Override
    public void setTags( @Nonnull String providerScalingGroupId, @Nonnull AutoScalingTag... tags ) throws CloudException, InternalException {
        setTags(new String[]{providerScalingGroupId}, tags);
    }

    @Override
    public void setTags( @Nonnull String[] providerScalingGroupIds, @Nonnull AutoScalingTag... tags ) throws CloudException, InternalException {
        for( String id : providerScalingGroupIds ) {

            AutoScalingTag[] collectionForDelete = getTagsForDelete(getScalingGroup(id).getTags(), tags);

            if( collectionForDelete.length != 0 ) {
                removeTags(new String[]{id}, collectionForDelete);
            }

            updateTags(new String[]{id}, tags);
        }
    }

    static public AutoScalingTag[] getTagsForDelete( AutoScalingTag[] all, Tag[] tags ) {
        Collection<AutoScalingTag> result = new ArrayList<AutoScalingTag>();
        if( all != null ) {
            for( AutoScalingTag tag : all ) {
                if( !TagUtils.isKeyInTags(tag.getKey(), tags) ) {
                    result.add(tag);
                }
            }
        }
        return result.toArray(new AutoScalingTag[result.size()]);
    }

}
