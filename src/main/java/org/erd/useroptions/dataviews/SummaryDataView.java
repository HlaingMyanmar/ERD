package org.erd.useroptions.dataviews;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDataView {

    private int totalUser;
    private int activeUser;
    private int notActiveUser;
    private int roleCount;

    public SummaryDataView(int totalUser, int activeUser, int notActiveUser) {
        this.totalUser = totalUser;
        this.activeUser = activeUser;
        this.notActiveUser = notActiveUser;
    }
}
