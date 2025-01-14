package ru.basher.joiner.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JoinValue {

    private final String id;
    private final String permission;
    private final List<String> message;

}
