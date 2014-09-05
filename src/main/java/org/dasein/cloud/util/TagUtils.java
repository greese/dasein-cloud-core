package org.dasein.cloud.util;

import org.dasein.cloud.Tag;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * User: Eugene Yaroslavtsev
 * Date: 31.07.2014
 */
public class TagUtils {

    static public @Nonnull Tag[] getTagsForDelete( Map<String, String> all, Tag[] tags ) {
        Collection<Tag> result = new ArrayList<Tag>();
        if( all != null ) {
            for( Map.Entry<String, String> entry : all.entrySet() ) {
                if( !isKeyInTags(entry.getKey(), tags) ) {
                    result.add(new Tag(entry.getKey(), entry.getValue()));
                }
            }
        }
        return result.toArray(new Tag[result.size()]);
    }

    static public boolean isKeyInTags( String key, Tag[] tags ) {
        for( Tag tag : tags ) {
            if( tag.getKey().equals(key) ) {
                return true;
            }
        }
        return false;
    }

}
