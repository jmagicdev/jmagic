package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Magma Spray")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MagmaSpray extends Card
{
	public MagmaSpray(GameState state)
	{
		super(state);

		// Magma Spray deals 2 damage to target creature.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(spellDealDamage(2, targetedBy(target), "Magma Spray deals 2 damage to target creature."));

		// If that creature would be put into a graveyard this turn, exile it
		// instead.
		ZoneChangeReplacementEffect exileItInstead = new ZoneChangeReplacementEffect(state.game, "If that creature would be put into a graveyard this turn, exile it instead");
		exileItInstead.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), targetedBy(target), true));
		exileItInstead.changeDestination(ExileZone.instance());

		this.addEffect(createFloatingReplacement(exileItInstead, "If that creature would be put into a graveyard this turn, exile it instead."));
	}
}
