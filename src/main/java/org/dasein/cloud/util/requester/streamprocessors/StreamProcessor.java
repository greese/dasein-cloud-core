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

package org.dasein.cloud.util.requester.streamprocessors;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;

 /**
 * @author Vlad Munthiu
 */
public interface StreamProcessor<T> {
    @Nullable T read(InputStream inputStream, Class<T> classType) throws IOException;
    @Nullable String write(T object);
}
