package com.rbc.petstore.rest;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.rbc.petstore.model.InventoryStatus;

@Component
public class StringToInventoryStatusConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(final String text) {
        if (StringUtils.isBlank(text)) {
            return;
        }
        String[] split = text.split(",");
        ArrayList<InventoryStatus> values = new ArrayList<>();
        for (String value : split) {
            if (StringUtils.isNotBlank(value)) {
                values.add(InventoryStatus.fromValue(value));
            }
        }

        setValue(values);
    }
}
