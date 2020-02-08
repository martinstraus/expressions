package expressions.ast;

import expressions.evaluator.EvaluationException;
import expressions.evaluator.SymbolsTable;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.util.Arrays.asList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class PropertyReference<T> implements Expression<T> {

    private final Expression<?> value;
    private final List<String> properties;

    public PropertyReference(Expression<?> value, List<String> properties) {
        this.value = value;
        this.properties = properties;
    }

    @Override
    public Type type() {
        return Types.UNKNOWN;
    }

    @Override
    public T evaluate(SymbolsTable symbolsTable) {
        return resolveProperties(value.evaluate(symbolsTable));
    }

    private T resolveProperties(Object value) {
        Object current = value;
        Iterator<String> properties = this.properties.iterator();
        while (current != null && properties.hasNext()) {
            current = resolveProperty(current, properties.next());
        }
        return (T) current;
    }

    private Object resolveProperty(Object value, String property) {
        if (value instanceof java.util.Map) {
            return ((java.util.Map) value).get(property);
        } else {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(value.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                Optional<PropertyDescriptor> pd = asList(propertyDescriptors).stream().filter((p) -> p.getName().equals(property)).findFirst();
                if (!pd.isPresent()) {
                    return null;
                }
                Method readMethod = pd.get().getReadMethod();
                return readMethod.invoke(value);
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                throw new EvaluationException(String.format("Property %s not found.", property));
            }
        }
    }

}
