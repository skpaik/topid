package io.goribco.core.models.optionitems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionItem {
    private String key;
    private OptionItemValue detail;

    public OptionItem(byte value, String label) {
        key = label.replace(" ", "_").toUpperCase();
        detail = new OptionItemValue(value, label);
    }

    @Data
    @AllArgsConstructor
    public static class OptionItemValue {
        private byte value;
        private String label;
    }
}
