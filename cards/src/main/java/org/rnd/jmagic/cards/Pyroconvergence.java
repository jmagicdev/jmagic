package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Pyroconvergence")
@Types({Type.ENCHANTMENT})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Pyroconvergence extends Card
{
	public static final class PyroconvergenceAbility0 extends EventTriggeredAbility
	{
		public PyroconvergenceAbility0(GameState state)
		{
			super(state, "Whenever you cast a multicolored spell, Pyroconvergence deals 2 damage to target creature or player.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.put(EventType.Parameter.OBJECT, Multicolored.instance());
			this.addPattern(pattern);

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Pyroconvergence deals 2 damage to target creature or player."));
		}
	}

	public Pyroconvergence(GameState state)
	{
		super(state);

		// Whenever you cast a multicolored spell, Pyroconvergence deals 2
		// damage to target creature or player.
		this.addAbility(new PyroconvergenceAbility0(state));
	}
}
