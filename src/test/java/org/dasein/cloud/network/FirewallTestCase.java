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

package org.dasein.cloud.network;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for generic networking classes independent of cloud context.
 * <p>Created by George Reese: 2/3/14 11:38 AM</p>
 * @author George Reese
 * @version 2014.03 initial version
 */
public class FirewallTestCase {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void verifyFirewallReferenceContent() {
        String ownerId = "me";
        String firewallId = "you";

        FirewallReference r = FirewallReference.getInstance(ownerId, firewallId);

        assertNotNull("The firewall reference from the constructor cannot be null", r);
        assertEquals("The owner ID did not match the test value", ownerId, r.getProviderOwnerId());
        assertEquals("The firewall ID did not match the test value", firewallId, r.getProviderFirewallId());
        assertNotNull("The toString() reference cannot be null", r.toString());
    }

    @Test
    public void verifyFirewallReferenceEquals() {
        String ownerId = "me";
        String firewallId = "you";

        FirewallReference r1 = FirewallReference.getInstance(ownerId, firewallId);
        FirewallReference r2 = FirewallReference.getInstance(ownerId, firewallId);

        assertTrue("A firewall reference should be equal to itself", r1.equals(r1));
        assertTrue("The two firewall references should be equal, but they are not", r1.equals(r2));
    }

    @Test
    public void verifyFirewallReferenceNotEquals() {
        String ownerId = "me";
        String firewallId = "you";

        FirewallReference r1 = FirewallReference.getInstance(ownerId, firewallId);

        //noinspection ObjectEqualsNull
        assertFalse("A firewall reference should not equal null", r1.equals(null));

        FirewallReference r2 = FirewallReference.getInstance("other", "firewall");
        assertFalse("The two firewall references should not be equal, but they are", r1.equals(r2));

        r2 = FirewallReference.getInstance(ownerId, "another");
        assertFalse("The two firewall references with the same owner should not be equal, but they are", r1.equals(r2));

        r2 = FirewallReference.getInstance("someoneelse", firewallId);
        assertFalse("The two firewall references with the same ID and different owners should not be equal, but they are", r1.equals(r2));
    }

    @Test
    public void createRuleOptionsWithPrecedence() {
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
    public void createRuleOptionsWithoutPrecedence() {
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

    @Test
    public void createEmptyConstraints() {
        FirewallConstraints c = FirewallConstraints.getInstance();

        assertTrue("Firewall constraints are not open", c.isOpen());
    }

    @Test
    public void createSimpleConstraints() {
        FirewallConstraints c = FirewallConstraints.getInstance();

        c.withConstraint(FirewallConstraints.Constraint.PROTOCOL, FirewallConstraints.Level.REQUIRED);

        assertTrue("Firewall constraints are open", !c.isOpen());
        assertNotNull("Firewall constraint level may not be null for any possible constraint", c.getConstraintLevel(FirewallConstraints.Constraint.DESTINATION));
        assertEquals("Unconstrained field must be designated NOT_CONSTRAINED", FirewallConstraints.Level.NOT_CONSTRAINED, c.getConstraintLevel(FirewallConstraints.Constraint.DIRECTION));
        assertEquals("Protocol value is not set to be constrained", FirewallConstraints.Level.REQUIRED, c.getConstraintLevel(FirewallConstraints.Constraint.PROTOCOL));
    }
}
