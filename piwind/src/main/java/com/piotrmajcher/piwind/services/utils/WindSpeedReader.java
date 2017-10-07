package com.piotrmajcher.piwind.services.utils;

import com.piotrmajcher.piwind.domain.WindSpeed;
import com.piotrmajcher.piwind.services.scheduledtasks.ScheduledTaskException;

public interface WindSpeedReader {
    WindSpeed fetchWindSpeed() throws WindSpeedReaderException;
}
