package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crumbling Colossus")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class CrumblingColossus extends Card
{
	public static final class CrumblingColossusAbility1 extends EventTriggeredAbility
	{
		public CrumblingColossusAbility1(GameState state)
		{
			super(state, "When Crumbling Colossus attacks, sacrifice it at end of combat.");
			this.addPattern(whenThisAttacks());

			EventFactory sacrificeLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Sacrifice it at end of combat");
			sacrificeLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrificeLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atEndOfCombat()));
			sacrificeLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrificeThis("Crumbling Colossus")));
			this.addEffect(sacrificeLater);
		}
	}

	public CrumblingColossus(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(4);

		// Trample (If this creature would assign enough damage to its blockers
		// to destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// When Crumbling Colossus attacks, sacrifice it at end of combat.
		this.addAbility(new CrumblingColossusAbility1(state));
	}
}
