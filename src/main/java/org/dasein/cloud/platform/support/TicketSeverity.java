package org.dasein.cloud.platform.support;

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

    TicketSeverity(Integer num) {
        this.num = num;
    }

    public static TicketSeverity valueByPriority(Integer value) {
        for (TicketSeverity severity : values()) {
            if (severity.num.equals(value)) {
                return severity;
            }
        }
        return UNKNOWN;//default
    }
}
