package com.piotrmajcher.piwind.services.utils;

import java.util.List;

public interface CommandExecutor {

    List<String> executeCommand(String command) throws CommandExecutionException;
}
