package store.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import store.common.constants.NumberConstants;
import store.common.constants.StringConstants;


public class Promotion {
    private final String name;
    private final Long buy;
    private final Long get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String[] promotionsInfo) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(StringConstants.DATE_FORMAT);

        String name = promotionsInfo[NumberConstants.PROMOTION_NAME_INDEX];
        long buy = Long.parseLong(promotionsInfo[NumberConstants.PROMOTION_BUY_INDEX]);
        long get = Long.parseLong(promotionsInfo[NumberConstants.PROMOTION_GET_INDEX]);
        LocalDate startDate = LocalDate.parse(promotionsInfo[NumberConstants.PROMOTION_START_DATE_INDEX],
                dateFormatter);
        LocalDate endDate = LocalDate.parse(promotionsInfo[NumberConstants.PROMOTION_END_DATE_INDEX],
                dateFormatter);

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

    public boolean checkPromotionPeriod(LocalDate now) {
        return (now.isEqual(startDate) || now.isAfter(startDate)) &&
                (now.isEqual(endDate) || now.isBefore(endDate));

    }
}
