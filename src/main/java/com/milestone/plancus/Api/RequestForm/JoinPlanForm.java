package com.milestone.plancus.Api.RequestForm;

import lombok.Data;

import java.util.List;

@Data
public class JoinPlanForm {
    private Long headId;
    private List<UseTimeDTO> availableTimes;

    public JoinPlanForm() {
    }

    @Data
    public static class UseTimeDTO{
        // Error : cannot deserialize from Object value (no delegate- or property-based Creator)
        public UseTimeDTO() {
        }

        public UseTimeDTO(String startTime, String endTime) {
            this.start = startTime;
            this.end = endTime;
        }

        private String start;
        private String end;
    }
}
