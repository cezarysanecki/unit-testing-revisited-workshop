package pl.cezarysanecki.unittestingrevisitedworkshop.helper;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.Provide;
import net.jqwik.api.arbitraries.DoubleArbitrary;
import net.jqwik.api.arbitraries.IntegerArbitrary;
import net.jqwik.api.arbitraries.StringArbitrary;
import pl.cezarysanecki.unittestingrevisitedworkshop.Man;
import pl.cezarysanecki.unittestingrevisitedworkshop.Woman;

import java.util.List;

public class PbtProviders {

    @Provide
    Arbitrary<Man> manProvider() {
        StringArbitrary names = Arbitraries.strings().withCharRange('A', 'Z').ofMinLength(3).ofMaxLength(10);
        IntegerArbitrary ages = Arbitraries.integers().between(18, 60);
        StringArbitrary cities = Arbitraries.strings().withCharRange('a', 'z').ofMinLength(3).ofMaxLength(10);
        StringArbitrary hobbies = Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(10);
        IntegerArbitrary benchPress = Arbitraries.integers().between(10, 200);

        return Combinators.combine(names, ages, cities, hobbies, benchPress)
                .as(Man::new);
    }

    @Provide
    Arbitrary<Woman> womanProvider() {
        StringArbitrary names = Arbitraries.strings().withCharRange('A', 'Z').ofMinLength(3).ofMaxLength(10);
        IntegerArbitrary ages = Arbitraries.integers().between(18, 60);
        StringArbitrary cities = Arbitraries.strings().withCharRange('a', 'z').ofMinLength(3).ofMaxLength(10);
        StringArbitrary hobbies = Arbitraries.strings().withCharRange('a', 'z').ofMinLength(3).ofMaxLength(10);
        DoubleArbitrary cosmeticSpending = Arbitraries.doubles().between(10.0, 100.0);

        return Combinators.combine(names, ages, cities, hobbies, cosmeticSpending)
                .as(Woman::new);
    }

    @Provide
    Arbitrary<List<Man>> menProvider() {
        return manProvider().list().ofMinSize(5).ofMaxSize(10);
    }

    @Provide
    Arbitrary<List<Woman>> womenProvider() {
        return womanProvider().list().ofMinSize(5).ofMaxSize(10);
    }

}
