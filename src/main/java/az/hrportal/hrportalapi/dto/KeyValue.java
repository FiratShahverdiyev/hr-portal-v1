package az.hrportal.hrportalapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KeyValue<K, V> {
    @EqualsAndHashCode.Include
    K key;
    V value;
}
