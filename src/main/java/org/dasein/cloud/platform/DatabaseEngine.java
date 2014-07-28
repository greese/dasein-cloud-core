/**
 * Copyright (C) 2009-2014 Dell, Inc.
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

package org.dasein.cloud.platform;

/**
 * Database Engine
 *
 * @author Stas Maksimov
 * @version 2014.07 broken down engines into editions as per AWS model
 * @since ?
 */
public enum DatabaseEngine {

    SQLSERVER_EE, // sql server enterprise edition
    SQLSERVER_EX, // sql server express edition
    SQLSERVER_SE, // sql server standard edition
    SQLSERVER_WEB, // sql server web edition

    MYSQL, // mysql

    ORACLE_EE, // oracle enterprise edition
    ORACLE_SE, // oracle standard edition
    ORACLE_SE1, // oracle standard edition one

    POSTGRES; // postgres

//    public String getVersion() {
//        switch( this ) {
//            case MYSQL: return "5.5";
//            case MSSQL: return "10 (standard)";
//            case ORACLE11G: return "11g (standard)";
//            case ORACLE11GX: return "11g (standard, byol)";
//            case ORACLE11GEX: return "11g (enterprise, byol)";
//            case POSTGRES: return "9.3";
//        }
//        return null;
//    }
//

    public String toString() {
        switch( this ) {
            case SQLSERVER_SE:
                return "SQL Server Standard";
            case SQLSERVER_EE:
                return "SQL Server Enterprise";
            case SQLSERVER_EX:
                return "SQL Server Express";
            case SQLSERVER_WEB:
                return "SQL Server Web";
            case MYSQL:
                return "MySQL";
            case ORACLE_SE:
                return "Oracle Standard";
            case ORACLE_SE1:
                return "Oracle Standard One";
            case ORACLE_EE:
                return "Oracle Enterprise";
            case POSTGRES:
                return "PostgreSQL";
        }
        return super.toString();
    }
}
