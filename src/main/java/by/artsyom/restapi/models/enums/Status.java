package by.artsyom.restapi.models.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    created("Создан"), accepted("Принят"), sent("Отправлен"), Cancelled("Аннулирован"), Received("Получен");
    private String name;


    public String getName() {
        return name;
    }
}
