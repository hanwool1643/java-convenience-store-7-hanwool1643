package store.domain;

import java.time.LocalDate;
import java.util.Date;

public class Promotion {
    private final String name;
    private final Long buy;
    private final Long get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, Long buy, Long get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public String getName() {
        return name;
    }

    public Long getBuy() {
        return buy;
    }

    public Long getGet() {
        return get;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
