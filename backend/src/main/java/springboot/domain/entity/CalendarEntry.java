package springboot.domain.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dates")
public class CalendarEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;

    @Basic(optional = false)
    @Column(name = "name", length = 240, nullable = false, unique = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "number", length = 240, nullable = false, unique = false)
    private String number;

    @Basic(optional = false)
    @Column(name = "date", length = 240, nullable = false, unique = false)
    private String date;

    @Basic(optional = false)
    @Column(name = "validated")
    private boolean validated;

    @Basic(optional = false)
    @Column(name = "event_type", length = 240, nullable = false, unique = false)
    private String eventType;

    public CalendarEntry(long id, String name, String number, String date, boolean validated, String eventType) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.date = date;
        this.validated = validated;
        this.eventType = eventType;
    }

    public CalendarEntry() {
    }

    public CalendarEntry(String name, String number, String date, boolean validated, String eventType) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.validated = validated;
        this.eventType = eventType;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarEntry that = (CalendarEntry) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(number, that.number) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, date);
    }

    @Override
    public String toString() {
        return "CalendarEntry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
