package maco.habit_backend.controllers;

import maco.habit_backend.enums.Occurrence;
import maco.habit_backend.enums.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping("/occurrences")
    public List<Occurrence> getOccurrences() {
        return Arrays.asList(Occurrence.values());
    }

    @GetMapping("/types")
    public List<Type> getTypes() {
        return Arrays.asList(Type.values());
    }
}
