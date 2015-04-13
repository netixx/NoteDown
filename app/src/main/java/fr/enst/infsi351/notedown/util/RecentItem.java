package fr.enst.infsi351.notedown.util;

import java.util.Date;

/**
 * Created by francois on 13/04/15.
 */
public class RecentItem {

    public final Date date;
    public final String session;

    public RecentItem(String session,Date date) {
        this.date = date;
        this.session = session;
    }
}
