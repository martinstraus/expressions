/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author martinstraus
 */
public class TypesTest {

    @Test
    public void ofClassReturnsTrueIfTypesAreExpected() {
        assertTrue(Types.ofClass(BigDecimal.class, new BigDecimal(1), new BigDecimal(2)));
    }

    @Test
    public void ofClassReturnsFalseIfTypesAreNotExpected() {
        assertFalse(Types.ofClass(BigDecimal.class, new BigDecimal(1), "a"));
    }
}
