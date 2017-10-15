package core;

import java.util.List;

interface DataSegregationAlgorithm {
    <T> List<List<T>> segregateBetween(int listsAmount, List<T> data);
}
