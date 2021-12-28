package ca.bertsa.internos.listener;

import ca.bertsa.internos.model.Contract;
import ca.bertsa.internos.service.BeanService;
import ca.bertsa.internos.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PreUpdate;

public class ContractListener {

    private static final Logger logger = LoggerFactory.getLogger(CurriculumListener.class);

    private NotificationService getNotificationService() {
        return BeanService.getBean(NotificationService.class);
    }

    @PreUpdate
    public void preUpdate(Contract contract) {
        try {
            getNotificationService().notifyOfContractSignature(contract);
        } catch (Exception e) {
            logger.debug("Notification Service : Could not commit transaction.");
        }
    }
}
