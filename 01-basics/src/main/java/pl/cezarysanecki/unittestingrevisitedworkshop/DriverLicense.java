package pl.cezarysanecki.unittestingrevisitedworkshop;

public class DriverLicense {

    private static final String DRIVER_LICENSE_REGEX = "^[A-Z]{5}\\d{2}$";

    private final String driverLicense;

    private DriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public static DriverLicense withLicense(String driverLicense) {
        if (driverLicense == null || driverLicense.isEmpty() || !driverLicense.matches(DRIVER_LICENSE_REGEX)) {
            throw new IllegalArgumentException("Illegal license no = " + driverLicense);
        }
        return new DriverLicense(driverLicense);
    }

    public static DriverLicense withoutValidation(String driverLicense) {
        return new DriverLicense(driverLicense);
    }

    public String print() {
        return driverLicense;
    }
}