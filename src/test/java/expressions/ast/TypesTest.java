package expressions.ast;

import java.math.BigDecimal;
import java.time.LocalDate;
import static java.util.Arrays.asList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class TypesTest {

    private static List<Arguments> typesForClasses() {
        return asList(
                Arguments.of(Boolean.class, Types.BOOLEAN),
                Arguments.of(LocalDate.class, Types.DATE),
                Arguments.of(BigDecimal.class, Types.NUMBER),
                Arguments.of(Integer.class, Types.NUMBER),
                Arguments.of(Set.class, Types.SET),
                Arguments.of(String.class, Types.STRING),
                Arguments.of(Object.class, Types.UNKNOWN)
        );
    }

    @Test
    public void ofClassReturnsTrueIfTypesAreExpected() {
        assertTrue(Types.ofClass(BigDecimal.class, new BigDecimal(1), new BigDecimal(2)));
    }

    @Test
    public void ofClassReturnsFalseIfTypesAreNotExpected() {
        assertFalse(Types.ofClass(BigDecimal.class, new BigDecimal(1), "a"));
    }

    @ParameterizedTest
    @MethodSource("typesForClasses")
    public void forTypeReturnsExpectedValues(Class aClass, Type expectedValue) {
        assertEquals(expectedValue, Types.forClass(aClass));
    }
}
