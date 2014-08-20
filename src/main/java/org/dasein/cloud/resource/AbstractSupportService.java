package org.dasein.cloud.resource;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.CloudProvider;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.OperationNotSupportedException;
import org.dasein.cloud.resource.model.*;
import org.dasein.cloud.resource.model.options.*;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
public abstract class AbstractSupportService implements SupportService {

    private CloudProvider provider;

    public AbstractSupportService(CloudProvider cloudProvider) {
        this.provider = cloudProvider;
    }

    @Override
    public Collection<Ticket> listTickets(@Nonnull TicketListOptions options) throws InternalException, CloudException {
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
    public ReplyWithCcEmails listReplies(@Nonnull TicketListRepliesOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for list replies");
    }

    @Override
    public AttachmentData getAttachment(@Nonnull TicketGetAttachmetnOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() + " support is not currently implemented for get attachment");
    }

    @Override
    public Collection<Attachment> listAttachments(@Nonnull TicketListAttachmentsOptions options) throws InternalException, CloudException {
        throw new OperationNotSupportedException(provider.getCloudName() +" support is not currently implemented for list attachments");
    }

    @Override
    public Collection<Service> listServices(@Nonnull TicketListServicesOptions options) throws InternalException, CloudException {
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
