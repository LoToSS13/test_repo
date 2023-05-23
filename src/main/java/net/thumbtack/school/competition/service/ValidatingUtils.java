package net.thumbtack.school.competition.service;


import net.thumbtack.school.competition.exception.ErrorCode;
import net.thumbtack.school.competition.exception.ServerException;
import net.thumbtack.school.competition.models.User;

public class ValidatingUtils {

    static boolean validatePassword(String password) {
        return password != null && password.length() > 6;
    }
    static boolean validateInput(String login) {
        return login != null && !login.isBlank();
    }

    public static <T> void validateFoundedUser(User user, Class<T> classType) throws ServerException {
        if (user==null){
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }
        if (!user.getClass().equals(classType)){
            throw new ServerException(ErrorCode.WRONG_CLASS);
        }
    }
}
