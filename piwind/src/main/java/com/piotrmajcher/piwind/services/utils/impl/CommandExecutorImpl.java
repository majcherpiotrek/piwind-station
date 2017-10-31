package com.piotrmajcher.piwind.services.utils.impl;

import com.piotrmajcher.piwind.services.utils.exceptions.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.CommandExecutor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

@Component
public class CommandExecutorImpl implements CommandExecutor {

    private static final Logger logger = Logger.getLogger(CommandExecutorImpl.class);

    @Override
    public List<String> executeCommand(String command) throws CommandExecutionException {

        List<String> resultsList = new LinkedList<>();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = stdInput.readLine()) != null) {
                resultsList.add(line);
            }
        } catch (IOException e) {
            logger.error("Error executing the following command: " + command);
            throw new CommandExecutionException(e.toString());
        }
        return resultsList;
    }
}
