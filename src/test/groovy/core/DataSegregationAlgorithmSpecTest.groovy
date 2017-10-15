package core

import org.apache.commons.collections.ListUtils
import spock.lang.Specification
import spock.lang.Unroll

class DataSegregationAlgorithmSpecTest extends Specification {

    static ALGORITHMS_TO_TEST = [new EqualDataSegregationAlgorithm()]

    @Unroll
    "#algorithm.getClass().getSimpleName() should store the same data as the initial list"() {
        given:
        def data = [0, 1, 2, 3, 4, 5]
        def results = []

        when:
        (1..5).each {
            results.add(algorithm.segregateBetween(it, data))
        }

        then:
        results.forEach {
            List result ->
                assert (result.stream().reduce(ListUtils.&union).orElseThrow({ new RuntimeException() }) as List)
                        .containsAll(data)
        }

        where:
        algorithm << ALGORITHMS_TO_TEST
    }

    @Unroll
    "#algorithm.getClass().getSimpleName() should not throw if data is empty and inputs amount is 0"() {
        given:
        List input = []

        when:
        def result = algorithm.segregateBetween(0, input)

        then:
        noExceptionThrown()

        and:
        result.empty

        where:
        algorithm << ALGORITHMS_TO_TEST
    }

    @Unroll
    "#algorithm.getClass().getSimpleName() should throw if input is not empty but lists amount is 0"() {
        given:
        List input = [1, 2, 3, 4, 5]

        when:
        algorithm.segregateBetween(0, input)

        then:
        thrown Exception

        where:
        algorithm << ALGORITHMS_TO_TEST
    }

    @Unroll
    "#algorithm.getClass().getSimpleName() should throw if input is null"() {
        given:
        List input = null

        when:
        algorithm.segregateBetween(1, input)

        then:
        thrown Exception

        where:
        algorithm << ALGORITHMS_TO_TEST
    }
}
