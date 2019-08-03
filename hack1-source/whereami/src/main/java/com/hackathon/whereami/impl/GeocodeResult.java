package com.hackathon.whereami.impl;

import java.util.List;
import java.util.Map;

class GeocodeResult {

    private PlusCode plus_code;
    private List<Map<String, Object>> results;

    public PlusCode getPlus_code() {
        return plus_code;
    }

    public List<Map<String, Object>> getResults() {
        return results;
    }

    static class PlusCode {
        private String compound_code;
        private String global_code;

        public String getCompound_code() {
            return compound_code;
        }

        public String getGlobal_code() {
            return global_code;
        }

        @Override
        public String toString() {
            return "PlusCode{" +
                    "compound_code='" + compound_code + '\'' +
                    ", global_code='" + global_code + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GeocodeResult{" +
                "plus_code=" + plus_code +
                ", results=" + results +
                '}';
    }
}
