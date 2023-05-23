package net.thumbtack.school.competition.exception;

public class ServerException extends Exception {
    ErrorCode errorCode;

    public ServerException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}