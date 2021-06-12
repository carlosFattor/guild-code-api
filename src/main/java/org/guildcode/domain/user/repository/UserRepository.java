package org.guildcode.domain.user.repository;

import io.smallrye.mutiny.Uni;
import org.guildcode.domain.user.User;

import java.util.List;

public interface UserRepository {

    Uni<User> findByEmail(String email);
    Uni<List<User>> findByLocation(Double lat, Double lng, int zoom);

    List<Double> positions = List.of(
            Double.parseDouble("10000"),
            Double.parseDouble("7000"),
            Double.parseDouble("5000"),
            Double.parseDouble("2000"),
            Double.parseDouble("1500"),
            Double.parseDouble("1000"),
            Double.parseDouble("450"),
            Double.parseDouble("210"),
            Double.parseDouble("130"),
            Double.parseDouble("70"),
            Double.parseDouble("40"),
            Double.parseDouble("20"),
            Double.parseDouble("10"),
            Double.parseDouble("5"),
            Double.parseDouble("3"),
            Double.parseDouble("1"),
            Double.parseDouble("0.5"),
            Double.parseDouble("0.4"),
            Double.parseDouble("0.3")
    );

    Uni<Void> update(User user);
}
