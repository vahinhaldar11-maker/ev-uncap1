package com.vahinhaldar.cobbleverseevcapmod

import com.cobblemon.mod.common.api.events.pokemon.PokemonEVChangeEvent
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.Event

object CobbleverseEvCapMod : ModInitializer {
    private const val MAX_EV_PER_STAT = 999
    private const val MAX_TOTAL_EV = 7000

    override fun onInitialize() {
        // Listen for EV change events
        PokemonEVChangeEvent.EVENT.register(Event { event ->
            // Clamp the EVs for the stat being changed
            if (event.ev > MAX_EV_PER_STAT) {
                event.ev = MAX_EV_PER_STAT
            }

            // Clamp the total EVs
            val totalEVs = event.pokemon.evs.sum()
            if (totalEVs > MAX_TOTAL_EV) {
                val excess = totalEVs - MAX_TOTAL_EV
                event.ev = (event.ev - excess).coerceAtLeast(0)
            }
        })
    }
}
