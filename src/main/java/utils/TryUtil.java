package utils;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class TryUtil {
    public <T> Optional<T> optionalTry(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (Throwable e) {
            //TODO:一些语句在cfg中缺失，因为此处会出现异常
            return Optional.empty();
        }
    }
}
