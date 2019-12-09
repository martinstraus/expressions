/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions.ast;

import static java.util.Arrays.asList;
import java.util.Objects;

/**
 *
 * @author martinstraus
 */
public class Types {

    private static final class DefaultType implements Type {

        private final String name;

        public DefaultType(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 17 * hash + Objects.hashCode(this.name);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final DefaultType other = (DefaultType) obj;
            if (!Objects.equals(this.name, other.name)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "DefaultType{" + "name=" + name + '}';
        }

    }
    public static final Type BOOLEAN = new DefaultType("boolean");
    public static final Type NUMBER = new DefaultType("number");
    public static final Type SET = new DefaultType("set");
    public static final Type STRING = new DefaultType("string");
    public static final Type DATE = new DefaultType("date");
    public static final Type DATE_UNIT = new DefaultType("date_unit");
    public static final Type UNKNOWN = new DefaultType("unknown");

    public static boolean ofType(Type type, Expression... expressions) {
        return asList(expressions).stream().anyMatch((e) -> !e.type().equals(type));
    }

    public static boolean ofClass(Class type, Object... values) {
        return asList(values).stream().allMatch((value) -> type.isAssignableFrom(value.getClass()));
    }
}
