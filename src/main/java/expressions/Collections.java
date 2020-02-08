package expressions;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author Martín Gaspar Straus <martinstraus@gmail.com>
 */
public class Collections {

    public static <T, U> List<U> foldList(Collection<T> list, Function<T, U> function) {
        return list.stream().map(function).collect(toList());
    }

    public static <T, U> Set<U> foldSet(Collection<T> set, Function<T, U> function) {
        return set.stream().map(function).collect(toSet());
    }
}
