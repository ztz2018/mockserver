package org.mockserver.logging;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockserver.log.model.MessageLogEntry;
import org.mockserver.mock.HttpStateHandler;
import org.mockserver.model.HttpRequest;
import org.slf4j.Logger;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.mockserver.character.Character.NEW_LINE;
import static org.mockserver.model.HttpRequest.request;

public class LogFormatterTest {

    @Test
    public void shouldFormatInfoLogMessagesForRequest() {
        // given
        Logger mockLogger = mock(Logger.class);
        HttpStateHandler mockHttpStateHandler = mock(HttpStateHandler.class);
        LoggingFormatter logFormatter = new LoggingFormatter(mockLogger, mockHttpStateHandler);
        when(mockLogger.isInfoEnabled()).thenReturn(true);
        HttpRequest request = request("some_path");

        // when
        logFormatter.infoLog(
            request,
            "some random message with {} and {}",
            "some" + NEW_LINE + "multi-line" + NEW_LINE + "object",
            "another" + NEW_LINE + "multi-line" + NEW_LINE + "object"
        );

        // then
        String message = "some random message with " + NEW_LINE +
            "" + NEW_LINE +
            "\tsome" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE +
            " and " + NEW_LINE +
            "" + NEW_LINE +
            "\tanother" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE;
        verify(mockLogger).info(message);
        ArgumentCaptor<MessageLogEntry> captor = ArgumentCaptor.forClass(MessageLogEntry.class);
        verify(mockHttpStateHandler, times(1)).log(captor.capture());

        MessageLogEntry messageLogEntry = captor.getValue();
        assertThat(messageLogEntry.getHttpRequest(), is(request));
        assertThat(messageLogEntry.getMessage(), containsString(message));
    }

    @Test
    public void shouldFormatInfoLogMessagesForRequestList() {
        // given
        Logger mockLogger = mock(Logger.class);
        HttpStateHandler mockHttpStateHandler = mock(HttpStateHandler.class);
        LoggingFormatter logFormatter = new LoggingFormatter(mockLogger, mockHttpStateHandler);
        when(mockLogger.isInfoEnabled()).thenReturn(true);
        HttpRequest request = request("some_path");

        // when
        logFormatter.infoLog(
            Arrays.asList(request, request),
            "some random message with {} and {}",
            "some" + NEW_LINE + "multi-line" + NEW_LINE + "object",
            "another" + NEW_LINE + "multi-line" + NEW_LINE + "object"
        );

        // then
        String message = "some random message with " + NEW_LINE +
            "" + NEW_LINE +
            "\tsome" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE +
            " and " + NEW_LINE +
            "" + NEW_LINE +
            "\tanother" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE;
        verify(mockLogger).info(message);
        ArgumentCaptor<MessageLogEntry> captor = ArgumentCaptor.forClass(MessageLogEntry.class);
        verify(mockHttpStateHandler, times(2)).log(captor.capture());

        for (MessageLogEntry messageLogEntry : captor.getAllValues()) {
            assertThat(messageLogEntry.getHttpRequest(), is(request));
            assertThat(messageLogEntry.getMessage(), containsString(message));
        }
    }

    @Test
    public void shouldFormatErrorLogMessagesForRequest() {
        // given
        Logger mockLogger = mock(Logger.class);
        HttpStateHandler mockHttpStateHandler = mock(HttpStateHandler.class);
        LoggingFormatter logFormatter = new LoggingFormatter(mockLogger, mockHttpStateHandler);
        when(mockLogger.isInfoEnabled()).thenReturn(true);
        HttpRequest request = request("some_path");

        // when
        logFormatter.errorLog(
            request,
            "some random message with {} and {}",
            "some" + NEW_LINE + "multi-line" + NEW_LINE + "object",
            "another" + NEW_LINE + "multi-line" + NEW_LINE + "object"
        );

        // then
        String message = "some random message with " + NEW_LINE +
            "" + NEW_LINE +
            "\tsome" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE +
            " and " + NEW_LINE +
            "" + NEW_LINE +
            "\tanother" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE;
        verify(mockLogger).error(message);

        ArgumentCaptor<MessageLogEntry> captor = ArgumentCaptor.forClass(MessageLogEntry.class);
        verify(mockHttpStateHandler, times(1)).log(captor.capture());

        MessageLogEntry messageLogEntry = captor.getValue();
        assertThat(messageLogEntry.getHttpRequest(), is(request));
        assertThat(messageLogEntry.getMessage(), containsString(message));
    }

    @Test
    public void shouldFormatExceptionErrorLogMessagesForRequest() {
        // given
        Logger mockLogger = mock(Logger.class);
        HttpStateHandler mockHttpStateHandler = mock(HttpStateHandler.class);
        LoggingFormatter logFormatter = new LoggingFormatter(mockLogger, mockHttpStateHandler);
        when(mockLogger.isInfoEnabled()).thenReturn(true);
        HttpRequest request = request("some_path");
        RuntimeException exception = new RuntimeException("TEST EXCEPTION");

        // when
        logFormatter.errorLog(
            request,
            exception,
            "some random message with {} and {}",
            "some" + NEW_LINE + "multi-line" + NEW_LINE + "object",
            "another" + NEW_LINE + "multi-line" + NEW_LINE + "object"
        );

        // then
        String message = "some random message with " + NEW_LINE +
            "" + NEW_LINE +
            "\tsome" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE +
            " and " + NEW_LINE +
            "" + NEW_LINE +
            "\tanother" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE;
        verify(mockLogger).error(message, exception);

        ArgumentCaptor<MessageLogEntry> captor = ArgumentCaptor.forClass(MessageLogEntry.class);
        verify(mockHttpStateHandler, times(1)).log(captor.capture());

        MessageLogEntry messageLogEntry = captor.getValue();
        assertThat(messageLogEntry.getHttpRequest(), is(request));
        assertThat(messageLogEntry.getMessage(), containsString(message));
    }

    @Test
    public void shouldFormatExceptionErrorLogMessagesForRequestList() {
        // given
        Logger mockLogger = mock(Logger.class);
        HttpStateHandler mockHttpStateHandler = mock(HttpStateHandler.class);
        LoggingFormatter logFormatter = new LoggingFormatter(mockLogger, mockHttpStateHandler);
        when(mockLogger.isInfoEnabled()).thenReturn(true);
        HttpRequest request = request("some_path");
        RuntimeException exception = new RuntimeException("TEST EXCEPTION");

        // when
        logFormatter.errorLog(
            Arrays.asList(request, request),
            exception,
            "some random message with {} and {}",
            "some" + NEW_LINE + "multi-line" + NEW_LINE + "object",
            "another" + NEW_LINE + "multi-line" + NEW_LINE + "object"
        );

        // then
        String message = "some random message with " + NEW_LINE +
            "" + NEW_LINE +
            "\tsome" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE +
            " and " + NEW_LINE +
            "" + NEW_LINE +
            "\tanother" + NEW_LINE +
            "\tmulti-line" + NEW_LINE +
            "\tobject" + NEW_LINE;
        verify(mockLogger).error(message, exception);

        ArgumentCaptor<MessageLogEntry> captor = ArgumentCaptor.forClass(MessageLogEntry.class);
        verify(mockHttpStateHandler, times(2)).log(captor.capture());

        for (MessageLogEntry messageLogEntry : captor.getAllValues()) {
            assertThat(messageLogEntry.getHttpRequest(), is(request));
            assertThat(messageLogEntry.getMessage(), containsString(message));
        }
    }

}
