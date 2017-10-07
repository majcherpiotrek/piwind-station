package com.piotrmajcher.piwind.services.utils.impl;

import com.piotrmajcher.piwind.services.utils.CommandExecutionException;
import com.piotrmajcher.piwind.services.utils.CommandExecutor;
import com.piotrmajcher.piwind.services.utils.SnapshotReaderException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SnapshotReaderImplTest {

    @InjectMocks
    private SnapshotReaderImpl snapshotReader;

    @Mock
    private CommandExecutor commandExecutor;

    @Before
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = SnapshotReaderException.class)
    public void fetchSnapshotShouldThrowSnapshotReaderExceptionWhenCommandExecutorThrowsException() throws Exception{
        Mockito.when(commandExecutor.executeCommand(Mockito.anyString())).thenThrow(CommandExecutionException.class);
        snapshotReader.fetchSnapshot();
    }

}