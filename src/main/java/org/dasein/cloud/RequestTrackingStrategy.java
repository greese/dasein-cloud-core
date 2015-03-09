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

package org.dasein.cloud;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Drew Lyall: 12/09/2014 16:51
 * @author Drew Lyall
 * @since 2014.08 - Fork
 * @version 2014.08 - Fork
 */
public class RequestTrackingStrategy {

    public static @Nonnull RequestTrackingStrategy getInstance(@Nonnull String requestId){
        return new RequestTrackingStrategy(requestId);
    }

    private String  requestId;
    private String  headerName;
    private boolean sendAsHeader;
    private boolean inAPITrace;

    private RequestTrackingStrategy(){}

    private RequestTrackingStrategy(@Nonnull String requestId){
        this.requestId = requestId;
        this.sendAsHeader = false;
        this.inAPITrace = false;
    }

    public @Nonnull String getRequestId(){
        return this.requestId;
    }

    public boolean getSendAsHeader(){
        return this.sendAsHeader;
    }

    public @Nullable String getHeaderName(){
        return this.headerName;
    }

    public boolean getInAPITrace(){
        return this.inAPITrace;
    }

    public @Nonnull RequestTrackingStrategy sendAsHeader(boolean sendAsHeader, @Nonnull String headerName){
        this.sendAsHeader = sendAsHeader;
        this.headerName = headerName;
        return this;
    }

    public @Nonnull RequestTrackingStrategy includeInAPITrace(boolean inAPITrace){
        this.inAPITrace = inAPITrace;
        return this;
    }
}
