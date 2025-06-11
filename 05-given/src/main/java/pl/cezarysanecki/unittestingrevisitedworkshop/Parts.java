package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.Collection;
import java.util.stream.Stream;

public enum Parts {
    ENGINE, GEARBOX, SUSPENSION, PAINT;

    public static boolean isAll(Collection<Parts> parts) {
        return Stream.of(Parts.values()).allMatch(parts::contains);
    }
}
