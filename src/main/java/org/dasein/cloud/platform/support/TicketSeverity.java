package org.dasein.cloud.platform.support;

import org.dasein.cloud.CloudException;

/**
 * @author Eugene Yaroslavtsev
 * @since 26.08.2014
 */
public enum TicketSeverity {
    LOW(1),
    NORMAL(2),
    HIGH(3),
    URGENT(4),
    CRITICAL(5),
    UNKNOWN(-1);

    private Integer num;

    TicketSeverity( Integer num ) {
        this.num = num;
    }

    public static TicketSeverity valueOf( Object value ) throws CloudException {
        if( value != null && isString(value) ) {
            TicketSeverity validateValue = validateValue(String.valueOf(value));
            if( validateValue != null ) {
                return validateValue;
            }
            else {
                for( TicketSeverity severity : values() ) {
                    if( severity.name().equalsIgnoreCase(String.valueOf(value)) ) {
                        return severity;
                    }
                }
            }
        }
        throw new CloudException("Invalid value for TicketSeverity " + value);
    }

    public static TicketSeverity valueByPriority( Integer value ) {
        for( TicketSeverity severity : values() ) {
            if( severity.num.equals(value) ) {
                return severity;
            }
        }
        return UNKNOWN;//default
    }

    public static boolean isString( Object o ) {
        return o.getClass().equals(String.class);
    }

    public static TicketSeverity validateValue( String value ) {
        try {
            return valueOf(TicketSeverity.class, value);
        } catch( IllegalArgumentException ex ) {
            return null;
        } catch( NullPointerException npe ) {
            return null;
        }
    }
}
