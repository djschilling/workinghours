package de.schilling.workinghours.duration;

import de.schilling.workinghours.user.User;
import de.schilling.workinghours.user.UserService;
import de.schilling.workinghours.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static de.schilling.workinghours.duration.DurationType.WORK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DurationServiceImplTest {

    @Mock
    private DurationRepository durationRepositoryMock;
    @Mock
    private UserService userServiceMock;
    @Mock
    private DurationValidationService durationValidationServiceMock;

    private DurationServiceImpl sut;

    @Before
    public void setUp() throws Exception {
        sut = new DurationServiceImpl(durationRepositoryMock, userServiceMock, durationValidationServiceMock);
    }

    @Test
    public void testCalculateDurationSum() throws Exception {
        List<Duration> durationList = new ArrayList<>();
        durationList.add(new Duration(LocalDateTime.now(), LocalDateTime.now().plusMinutes(7), "Pustekuchen", WORK));
        durationList.add(new Duration(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), "Pustekuchen", WORK));
        durationList.add(new Duration(LocalDateTime.now(), LocalDateTime.now().plusMinutes(12), "Pustekuchen", WORK));
        assertThat(sut.calculateDurationSum(durationList), is(20L));
    }

    @Test
    public void delete() {

        User user = new User("foo", "password", "USER");
        Duration duration = new Duration(null, null, "foo", WORK);

        when(userServiceMock.getCurrentlyLoggedIn()).thenReturn(user);
        when(durationRepositoryMock.findById(1L)).thenReturn(duration);

        sut.delete(1L);

        verify(durationRepositoryMock).delete(1L);
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteAccessDenied() {

        User user = new User("bar", "password", "USER");
        Duration duration = new Duration(null, null, "foo", WORK);

        when(userServiceMock.getCurrentlyLoggedIn()).thenReturn(user);
        when(durationRepositoryMock.findById(1L)).thenReturn(duration);

        sut.delete(1L);

        verify(durationRepositoryMock, times(0)).delete(1L);
    }
}