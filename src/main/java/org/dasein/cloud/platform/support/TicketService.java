package org.dasein.cloud.platform.support;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.platform.support.model.*;
import org.dasein.cloud.platform.support.model.options.*;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
public interface TicketService {

    /**
     * Gets the list of tickets having the specified options
     *
     * @param options the specified options
     * @return the list of tickets
     * @throws InternalException
     * @throws CloudException
     */
    public Collection<Ticket> listTickets(@Nonnull TicketListOptions options) throws InternalException, CloudException;

    /**
     * Closes the ticket having the specified options
     *
     * @param options the specified options
     * @throws InternalException
     * @throws CloudException
     */
    public void closeTicket(@Nonnull TicketCloseOptions options) throws InternalException, CloudException;

    /**
     * Creates the ticket with parameters specified in options
     *
     * @param options the specified options
     * @return the id of new ticket
     * @throws InternalException
     * @throws CloudException
     */
    public String createTicket(@Nonnull TicketCreateOptions options) throws InternalException, CloudException;

    /**
     * Gets the single ticket having the specified options
     *
     * @param options the specified options
     * @return the ticket
     * @throws InternalException
     * @throws CloudException
     */
    public Ticket getTicket(@Nonnull TicketGetOptions options) throws InternalException, CloudException;

    /**
     * Gets the list of replies in tickets having the specified options
     *
     * @param options the specified options
     * @return the replies
     * @throws InternalException
     * @throws CloudException
     */
    public Collection<TicketReply> listReplies(@Nonnull TicketListRepliesOptions options) throws InternalException, CloudException;

    /**
     * Gets the attachment having the specified options
     *
     * @param options the specified options
     * @return the attachment
     * @throws InternalException
     * @throws CloudException
     */
    public TicketAttachmentData getAttachment(@Nonnull TicketGetAttachmetnOptions options) throws InternalException, CloudException;

    /**
     * Gets the list of attachments having the specified options
     *
     * @param options the specified options
     * @return the list of attachments
     * @throws InternalException
     * @throws CloudException
     */
    public Collection<TicketAttachment> listAttachments(@Nonnull TicketListAttachmentsOptions options) throws InternalException, CloudException;

    /**
     * Gets list of services having the specified options
     *
     * @param options the specified options
     * @return the list of services
     * @throws InternalException
     * @throws CloudException
     */
    public Collection<org.dasein.cloud.platform.support.model.TicketService> listServices(@Nonnull TicketListServicesOptions options) throws InternalException, CloudException;

    /**
     * Creates the reply having the specified options
     *
     * @param options the specified options
     * @throws InternalException
     * @throws CloudException
     */
    public void createReply(@Nonnull TicketCreateReplyOptions options) throws InternalException, CloudException;

    /**
     * Creates the attachment having the specified options
     *
     * @param options the specified options
     * @return the id of new attachment
     * @throws InternalException
     * @throws CloudException
     */
    public String createAttachments(@Nonnull TicketCreateAttachmentsOptions options) throws InternalException, CloudException;

}
