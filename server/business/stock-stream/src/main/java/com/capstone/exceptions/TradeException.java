package com.capstone.exceptions;

public class TradeException extends RuntimeException {
    public TradeException(String message) {
        super(message);
    }

    public static class TradeNotFoundException extends TradeException {
        public TradeNotFoundException(String message) {
            super(message);
        }
    }

    public static class TradeAlreadyExistsException extends TradeException {
        public TradeAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class TradeValidationException extends TradeException {
        public TradeValidationException(String message) {
            super(message);
        }
    }

    public static class TradeExecutionException extends TradeException {
        public TradeExecutionException(String message) {
            super(message);
        }
    }
}
