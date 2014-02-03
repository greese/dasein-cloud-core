package org.dasein.cloud.network;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * [Class Documentation]
 * <p>Created by George Reese: 2/3/14 11:38 AM</p>
 *
 * @author George Reese
 */
public class FirewallTestCase {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testRuleCreateOptionsWithPrecedence() {
        RuleTarget source = RuleTarget.getCIDR("209.98.98.98/0");
        RuleTarget dest = RuleTarget.getGlobal("1234");
        FirewallRuleCreateOptions options = FirewallRuleCreateOptions.getInstance(Direction.INGRESS, Permission.ALLOW, source, Protocol.TCP, dest, 80, 80, 1);

        assertNotNull("Direction may not be null", options.getDirection());
        assertNotNull("Permission may not be null", options.getPermission());
        assertNotNull("Protocol may not be null", options.getProtocol());
        assertNotNull("Source endpoint may not be null", options.getSourceEndpoint());
        assertNotNull("Destination endpoint may not be null", options.getDestinationEndpoint());
        assertTrue("Port range start value must be greater than -2", options.getPortRangeStart() > -2);
        assertTrue("Port range end value must be greater than or equal to the port range start", options.getPortRangeEnd() >= options.getPortRangeStart());
        assertEquals("Precedence value specified was 1, but got " + options.getPrecedence(), 1, options.getPrecedence());
    }

    @Test
    public void testRuleCreateOptionsWithoutPrecedence() {
        RuleTarget source = RuleTarget.getCIDR("209.98.98.98/0");
        RuleTarget dest = RuleTarget.getGlobal("1234");
        FirewallRuleCreateOptions options = FirewallRuleCreateOptions.getInstance(Direction.INGRESS, Permission.ALLOW, source, Protocol.TCP, dest, 80, 80);

        assertNotNull("Direction may not be null", options.getDirection());
        assertNotNull("Permission may not be null", options.getPermission());
        assertNotNull("Protocol may not be null", options.getProtocol());
        assertNotNull("Source endpoint may not be null", options.getSourceEndpoint());
        assertNotNull("Destination endpoint may not be null", options.getDestinationEndpoint());
        assertTrue("Port range start value must be greater than -2", options.getPortRangeStart() > -2);
        assertTrue("Port range end value must be greater than or equal to the port range start", options.getPortRangeEnd() >= options.getPortRangeStart());
        assertEquals("Precedence value was not specified, but got " + options.getPrecedence(), 0, options.getPrecedence());
    }
}
