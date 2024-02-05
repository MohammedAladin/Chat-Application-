package org.Server.RepoInterfaces;

import org.Server.ServerModels.ServerEntities.Attachment;

public interface AttachmentRepoInterface extends Repository<Attachment, Integer> {
    Attachment findByMessageId(Integer messageId) ;

}
