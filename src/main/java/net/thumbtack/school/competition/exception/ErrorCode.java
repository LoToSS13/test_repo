package net.thumbtack.school.competition.exception;


public enum ErrorCode {
    WRONG_JSON("Неправильный формат JSON"),
    WRONG_REQUEST("Неправильный формат запроса"),
    USER_ALREADY_EXIST("Данный пользователь уже существует"),
    WRONG_PROFILES("Неправильный список профилей"),
    WRONG_LOGIN("Неправильный логин"),
    WRONG_PASSWORD("Неправильный  пароль"),
    WRONG_APPLICATION_NAME("Неправильное имя заявки"),
    WRONG_NAME("Неправильное имя"),
    WRONG_COMPANY_NAME("Неправильное название компании"),
    WRONG_APPLICATION_DESCRIPTION("Неправильное описание заявки"),
    USER_NOT_FOUND("Пользователь не найден"),
    WRONG_APPLICATION_PROFILES("Неправильные направления"),
    USER_NOT_LOGIN("Пользователь не вошел в систему"),
    WRONG_APPLICATION_SUM("Неправильная сумма гранта"),
    WRONG_APPLICATION_MARK("Неправильная оценка заявки"),
    WRONG_CLASS("Ошибка корректности класса"),
    APPLICATION_NOT_FOUND("Данная заявка не найдена"),
    WRONG_CRITICAL_MARK("Неверная критическая отметка"),
    EMPTY_PROFILES("Список профилей пуст"),
    WRONG_PROFILE("Неверное указание направления"),
    WRONG_PARENTAL_ID("Неверный id направления"),
    WRONG_FUND_SUM("Неверная сумма фонда");

    private final String description;

    ErrorCode(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


