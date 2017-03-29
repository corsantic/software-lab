package entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Patient
{
    Map<AttributeName, String> attributes = new HashMap<>();

    public String getAttributeValue(AttributeName attrName)
    {
        return attributes.get(attrName);
    }

    public Map<AttributeName, String> getAttributes()
    {
        return attributes;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
