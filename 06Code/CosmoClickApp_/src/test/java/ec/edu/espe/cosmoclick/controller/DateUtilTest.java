package ec.edu.espe.cosmoclick.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    public void parseAndFormat_roundTrip() {
        assertEquals("2026-03-07", DateUtil.format(DateUtil.parse("2026-03-07")));
    }

    @Test
    public void parse_invalidDate_returnsNull() {
        assertNull(DateUtil.parse("2026-02-30"));
        assertNull(DateUtil.parse("hola"));
        assertNull(DateUtil.parse("2026-13-01"));
        assertNull(DateUtil.parse(null));
    }

    @Test
    public void format_null_returnsEmptyText() {
        assertEquals("", DateUtil.format(null));
    }
}
