package ca.bertsa.internos.listener;


import ca.bertsa.internos.model.Curriculum;
import ca.bertsa.internos.service.BeanService;
import ca.bertsa.internos.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PreUpdate;

public class CurriculumListener {

    private static final Logger logger = LoggerFactory.getLogger(CurriculumListener.class);

    @PreUpdate
    public void preUpdate(Curriculum curriculum) {
        try {
            getNotificationService().notifyOfCurriculumValidation(curriculum);
        } catch (Exception e) {
            logger.debug("Notification Service : Could not commit transaction.");
        }
    }

    private NotificationService getNotificationService() {
        return BeanService.getBean(NotificationService.class);
    }
}
