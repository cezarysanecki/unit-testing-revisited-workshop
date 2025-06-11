package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostelParadiseMatcher {

    public static Map<Man, Woman> findMatches(List<Man> men, List<Woman> women) {
        if (men.size() != women.size()) {
            throw new IllegalArgumentException("Men and women lists must have the same size.");
        }

        Map<Man, Woman> matchMap = new HashMap<>();

        for (Man man : men) {
            Woman bestMatch = null;
            double maxScore = -1;

            for (Woman woman : women) {
                double currentScore = calculateMatchScore(man, woman);

                if (currentScore > maxScore) {
                    maxScore = currentScore;
                    bestMatch = woman;
                }
            }

            matchMap.put(man, bestMatch);
            women.remove(bestMatch);
        }

        return matchMap;
    }

    static double calculateMatchScore(Man man, Woman woman) {
        double score = 0;

        int ageDifference = Math.abs(man.age() - woman.age());
        if (ageDifference <= 2) {
            score += 20;
        } else if (ageDifference <= 5) {
            score += 10;
        }

        if (man.city().equalsIgnoreCase(woman.city())) {
            score += 30;
        }
        if (man.hobby().equalsIgnoreCase(woman.hobby())) {
            score += 15;
        }

        double idealBenchPressForCosmetics = woman.cosmeticSpendingPerMonth() * 0.5;
        double diffBenchPress = Math.abs(man.benchPressMaxKg() - idealBenchPressForCosmetics);

        if (diffBenchPress < 100) {
            score += 0;
        } else if (diffBenchPress < 250) {
            score += 20;
        } else if (diffBenchPress < 500) {
            score += 50;
        } else {
            score += 100;
        }

        return score;
    }

}
