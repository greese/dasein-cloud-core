package org.dasein.cloud.resource;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.resource.model.*;
import org.dasein.cloud.resource.model.options.*;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
public interface SupportService {

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
     * @return the replies with cc emails
     * @throws InternalException
     * @throws CloudException
     */
    public ReplyWithCcEmails listReplies(@Nonnull TicketListRepliesOptions options) throws InternalException, CloudException;

    /**
     * Gets the attachment having the specified options
     *
     * @param options the specified options
     * @return the attachment
     * @throws InternalException
     * @throws CloudException
     */
    public AttachmentData getAttachment(@Nonnull TicketGetAttachmetnOptions options) throws InternalException, CloudException;

    /**
     * Gets the list of attachments having the specified options
     *
     * @param options the specified options
     * @return the list of attachments
     * @throws InternalException
     * @throws CloudException
     */
    public Collection<Attachment> listAttachments(@Nonnull TicketListAttachmentsOptions options) throws InternalException, CloudException;

    /**
     * Gets list of services having the specified options
     *
     * @param options the specified options
     * @return the list of services
     * @throws InternalException
     * @throws CloudException
     */
    public Collection<Service> listServices(@Nonnull TicketListServicesOptions options) throws InternalException, CloudException;

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
