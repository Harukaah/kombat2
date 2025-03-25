package com.kombat2.kombat2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfig {
    @Value("${game.spawn_cost}")
    public long spawnCost;

    @Value("${game.hex_purchase_cost}")
    public long hexPurchaseCost;

    @Value("${game.init_budget}")
    public long initBudget;

    @Value("${game.init_hp}")
    public long initHP;

    @Value("${game.turn_budget}")
    public long turnBudget;

    @Value("${game.max_budget}")
    public long maxBudget;

    @Value("${game.interest_pct}")
    public long interestPct;

    @Value("${game.max_turns}")
    public long maxTurns;

    @Value("${game.max_spawns}")
    public long maxSpawns;
}
