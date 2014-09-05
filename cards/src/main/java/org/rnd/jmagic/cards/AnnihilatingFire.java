package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Annihilating Fire")
@Types({Type.INSTANT})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class AnnihilatingFire extends Card
{
	public AnnihilatingFire(GameState state)
	{
		super(state);

		// Annihilating Fire deals 3 damage to target creature or player. If a
		// creature dealt damage this way would die this turn, exile it instead.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

		EventFactory damage = spellDealDamage(3, target, "Annihilating Fire deals 3 damage to target creature or player.");
		this.addEffect(damage);

		ZoneChangeReplacementEffect exileItInstead = new ZoneChangeReplacementEffect(state.game, "If a creature dealt damage this way would die this turn, exile it instead.");
		exileItInstead.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), TakerOfDamage.instance(EventDamage.instance(EffectEvent.instance(damage))), true));
		exileItInstead.changeDestination(ExileZone.instance());

		this.addEffect(createFloatingReplacement(exileItInstead, "If a creature dealt damage this way would die this turn, exile it instead."));
	}
}
