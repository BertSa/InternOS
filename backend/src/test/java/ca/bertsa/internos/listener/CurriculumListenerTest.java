package ca.bertsa.internos.listener;

import ca.bertsa.internos.exception.IdDoesNotExistException;
import ca.bertsa.internos.model.Curriculum;
import ca.bertsa.internos.service.BeanService;
import ca.bertsa.internos.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurriculumListenerTest {

    @InjectMocks
    private CurriculumListener curriculumListener;

    @Mock
    private BeanService beanService;

    @Mock
    private NotificationService notificationService;

    @Test
    void preUpdateTest() throws Exception {
        doNothing().when(notificationService).notifyOfCurriculumValidation(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            curriculumListener.preUpdate(new Curriculum());

            verify(notificationService, times(1)).notifyOfCurriculumValidation(any());
        }
    }

    @Test
    void preUpdateTest_catchesException() throws Exception {
        Mockito.doThrow(IdDoesNotExistException.class).when(notificationService).notifyOfCurriculumValidation(any());
        try (MockedStatic<BeanService> classMock = mockStatic(BeanService.class)) {
            classMock.when(() -> BeanService.getBean((Class<?>) any(Class.class)))
                    .thenReturn(notificationService);

            assertDoesNotThrow(() -> curriculumListener.preUpdate(new Curriculum()));
        }
    }

}
