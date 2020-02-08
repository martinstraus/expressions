package expressions;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class TestObject<T> {

    private final T value;
    private final TestObject<T> nested;

    public TestObject(T value) {
        this(value, null);
    }

    public TestObject(TestObject nested) {
        this(null, nested);
    }

    public TestObject(T value, TestObject<T> nested) {
        this.value = value;
        this.nested = nested;
    }

    public T getValue() {
        return value;
    }

    public TestObject<T> getNested() {
        return nested;
    }

}
