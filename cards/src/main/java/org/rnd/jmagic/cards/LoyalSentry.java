package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Loyal Sentry")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Starter1999.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class LoyalSentry extends Card
{
	public static final class Suicide extends EventTriggeredAbility
	{
		public Suicide(GameState state)
		{
			super(state, "When Loyal Sentry blocks a creature, destroy that creature and Loyal Sentry.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			pattern.put(EventType.Parameter.OBJECT, thisCard);
			this.addPattern(pattern);

			SetGenerator attackerAndMe = Union.instance(thisCard, EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.ATTACKER));
			this.addEffect(destroy(attackerAndMe, "Destroy that creature and Loyal Sentry."));
		}
	}

	public LoyalSentry(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Suicide(state));
	}
}
