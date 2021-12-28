package ca.bertsa.internos.listener;

import ca.bertsa.internos.model.OfferApplication;
import ca.bertsa.internos.service.BeanService;
import ca.bertsa.internos.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;

public class OfferApplicationListener {

    private static final Logger logger = LoggerFactory.getLogger(OfferApplicationListener.class);

    @PostPersist
    public void postPersist(OfferApplication offerApplication) {
        try {
            getNotificationService().notifyOfNewApplicant(offerApplication);
        } catch (Exception e) {
            logger.debug("Notification Service : Could not commit transaction.");
        }
    }

    @PreUpdate
    public void preUpdate(OfferApplication offerApplication) {
        try {
            getNotificationService().notifyOfOfferAppStatusSetToStageTrouve(offerApplication);
            getNotificationService().notifyOfOfferAppInterviewSet(offerApplication);
        } catch (Exception e) {
            logger.debug("Notification Service : Could not commit transaction.");
        }
    }

    private NotificationService getNotificationService() {
        return BeanService.getBean(NotificationService.class);
    }
}
