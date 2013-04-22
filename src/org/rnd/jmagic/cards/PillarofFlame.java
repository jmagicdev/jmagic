package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pillar of Flame")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class PillarofFlame extends Card
{
	public PillarofFlame(GameState state)
	{
		super(state);

		// Pillar of Flame deals 2 damage to target creature or player. If a
		// creature dealt damage this way would die this turn, exile it instead.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

		EventFactory damage = spellDealDamage(2, target, "Pillar of Flame deals 2 damage to target creature or player.");
		this.addEffect(damage);

		ZoneChangeReplacementEffect exileItInstead = new ZoneChangeReplacementEffect(state.game, "If a creature dealt damage this way would die this turn, exile it instead.");
		exileItInstead.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), TakerOfDamage.instance(EventDamage.instance(EffectEvent.instance(damage))), true));
		exileItInstead.changeDestination(ExileZone.instance());

		this.addEffect(createFloatingReplacement(exileItInstead, "If a creature dealt damage this way would die this turn, exile it instead."));
	}
}
