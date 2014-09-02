package org.dasein.cloud.platform.support;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.platform.support.model.*;
import org.dasein.cloud.platform.support.model.options.*;

import javax.annotation.Nonnull;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
public abstract class AbstractTicketService implements TicketService {

    private CloudProvider provider;

    public AbstractTicketService(CloudProvider cloudProvider) {
        this.provider = cloudProvider;
    }

    @Override
    public Iterable<Ticket> listTickets(@Nonnull TicketListOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for list tickets");
    }

    @Override
    public void closeTicket(@Nonnull TicketCloseOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for close ticket");
    }

    @Override
    public String createTicket(@Nonnull TicketCreateOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() + " support is not currently implemented for create ticket");
    }

    @Override
    public Ticket getTicket(@Nonnull TicketGetOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for get ticket");
    }

    @Override
    public Iterable<TicketReply> listReplies(@Nonnull TicketListRepliesOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for list replies");
    }

    @Override
    public TicketAttachmentData getAttachment(@Nonnull TicketGetAttachmentOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() + " support is not currently implemented for get attachment");
    }

    @Override
    public Iterable<TicketAttachment> listAttachments(@Nonnull TicketListAttachmentsOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for list attachments");
    }

    @Override
    public Iterable<org.dasein.cloud.platform.support.model.TicketService> listServices(@Nonnull TicketListServicesOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for list services");
    }

    @Override
    public void createReply(@Nonnull TicketCreateReplyOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for create reply");
    }

    @Override
    public String createAttachments(@Nonnull TicketCreateAttachmentsOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() + " support is not currently implemented for create attachments");
    }

}
