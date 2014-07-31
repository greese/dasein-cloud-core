package org.dasein.cloud.util;

import org.dasein.cloud.Tag;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Eugene Yaroslavtsev
 * Date: 31.07.2014
 */
public class TagUtils {

    static public Collection<Tag> getTagsForDelete(Collection<? extends Tag> all, Tag[] tags) {
        Collection<Tag> result = null;
        if (all != null) {
            result = new ArrayList<Tag>();
            for (Tag tag : all) {
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
