package net.cefeon.wordquiz.config;

import org.hibernate.dialect.MariaDB10Dialect;

public class MariaDB10CICollation extends MariaDB10Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
    }
}
