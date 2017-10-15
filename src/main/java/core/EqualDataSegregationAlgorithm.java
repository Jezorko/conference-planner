package core;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;

class EqualDataSegregationAlgorithm implements DataSegregationAlgorithm {
    @Override
    public <T> List<List<T>> segregateBetween(int listsAmount, List<T> data) {
        List<List<T>> tracksTalks = new ArrayList<>();
        range(0, listsAmount).forEach(i -> tracksTalks.add(new ArrayList<>()));

        int j = 0;
        for (T talk : data) {
            tracksTalks.get(j)
                       .add(talk);
            ++j;
            if (j == listsAmount) {
                j = 0;
            }
        }

        return tracksTalks;
    }
}
