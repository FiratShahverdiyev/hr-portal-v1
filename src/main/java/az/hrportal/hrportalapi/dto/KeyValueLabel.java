package az.hrportal.hrportalapi.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KeyValueLabel<K, V, L> {
    @EqualsAndHashCode.Include
    K key;
    V value;
    L label;
}
