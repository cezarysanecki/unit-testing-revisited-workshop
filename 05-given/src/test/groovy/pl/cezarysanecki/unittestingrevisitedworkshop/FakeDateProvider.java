package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.time.LocalDate;

public class FakeDateProvider implements DateProvider {

    private LocalDate currentDate;

    public FakeDateProvider(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public LocalDate getCurrentDate() {
        return currentDate;
    }
}
