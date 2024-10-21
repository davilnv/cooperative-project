package br.com.davilnv.cooperative.domain.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class StaticsUtils {
    public static final ZoneId ZONE_ID = ZoneId.of("America/Sao_Paulo");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
}
