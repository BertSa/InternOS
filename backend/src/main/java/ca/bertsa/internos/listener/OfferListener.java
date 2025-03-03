package ca.bertsa.internos.listener;

import ca.bertsa.internos.model.Offer;
import ca.bertsa.internos.service.BeanService;
import ca.bertsa.internos.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;

public class OfferListener {

    private static final Logger logger = LoggerFactory.getLogger(OfferApplicationListener.class);

    @PostPersist
    public void postPersist(Offer offer) {
        try {
            getNotificationService().notifyOfNewOffer(offer);
        } catch (Exception e) {
            logger.debug("Notification Service : Could not commit transaction.");
        }
    }

    @PreUpdate
    public void preUpdate(Offer offer) {
        try {
            getNotificationService().notifyOfOfferValidation(offer);
        } catch (Exception e) {
            logger.debug("Notification Service : Could not commit transaction.");
        }
    }

    private NotificationService getNotificationService() {
        return BeanService.getBean(NotificationService.class);
    }
}
