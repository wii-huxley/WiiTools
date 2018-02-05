package com.huxley.wiitools.utils.logger;

public interface LogStrategy {

  void log(int priority, String tag, String message);
}
