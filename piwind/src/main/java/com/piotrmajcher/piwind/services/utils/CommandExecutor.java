package com.piotrmajcher.piwind.services.utils;

import com.piotrmajcher.piwind.services.utils.exceptions.CommandExecutionException;

import java.util.List;

public interface CommandExecutor {

    List<String> executeCommand(String command) throws CommandExecutionException;
}
