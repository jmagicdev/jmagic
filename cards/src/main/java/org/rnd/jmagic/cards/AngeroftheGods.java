package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Anger of the Gods")
@Types({Type.SORCERY})
@ManaCost("1RR")
@ColorIdentity({Color.RED})
public final class AngeroftheGods extends Card
{
	public AngeroftheGods(GameState state)
	{
		super(state);

		// Anger of the Gods deals 3 damage to each creature.
		EventFactory damage = spellDealDamage(3, CreaturePermanents.instance(), "Anger of the Gods deals 3 damage to each creature.");
		this.addEffect(damage);

		// If a creature dealt damage this way would die this turn, exile it
		// instead.
		ZoneChangeReplacementEffect exileItInstead = new ZoneChangeReplacementEffect(state.game, "If a creature dealt damage this way would die this turn, exile it instead.");
		exileItInstead.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), TakerOfDamage.instance(EventDamage.instance(EffectEvent.instance(damage))), true));
		exileItInstead.changeDestination(ExileZone.instance());

		this.addEffect(createFloatingReplacement(exileItInstead, "If a creature dealt damage this way would die this turn, exile it instead."));

	}
}
