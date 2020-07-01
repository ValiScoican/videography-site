package springboot.controller.calendar;

import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.domain.entity.CalendarEntry;
import springboot.domain.entity.User;
import springboot.repository.CalendarRepository;
import springboot.repository.UserRepository;
import springboot.service.email.EmailService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CalendarCtrl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CalendarRepository calendarRepository;

    @GetMapping(path = "/aprove/{id}")
    public String aprove(@PathVariable("id") Long id) {
        Optional<CalendarEntry> entry = calendarRepository.findById(id);
        entry.ifPresent(e -> {
            e.setValidated(true);
            calendarRepository.save(e);
        });
        return "DONE!";
    }

    @PostMapping(path = "/decline/{id}")
    public void decline(@PathVariable("id") Long id) {
        calendarRepository.deleteById(id);
    }

    @GetMapping(path = "/events/invalid")
    public List<CalendarEntry> getAllInvalidated() {
        List<CalendarEntry> list = calendarRepository.findAll();
        return list.stream().filter(entry -> !entry.isValidated()).collect(Collectors.toList());
    }

    @GetMapping(path = "/events")
    public List<CalendarEntry> getAll() {
        List<CalendarEntry> list = calendarRepository.findAll();
        return list.stream().filter(entry -> entry.isValidated()).collect(Collectors.toList());
    }

    @PostMapping(path = "/add/{name}/{number}/{date}/{eventType}")
    public void addEvent(@PathVariable("name") String name,
                         @PathVariable("number") String number,
                         @PathVariable("date") String date,
                         @PathVariable("eventType") String eventType,
                         HttpServletRequest request) {
        User user = userRepository.findUserByRole("ADMIN");
        CalendarEntry entry = new CalendarEntry(name, number, date, false, eventType);
        CalendarEntry res = calendarRepository.save(entry);
        emailService.sendNewCalendarEntry(name, number, user.getEmail(), eventType, res.getId());
    }

}
