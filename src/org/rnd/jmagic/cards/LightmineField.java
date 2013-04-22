package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lightmine Field")
@Types({Type.ENCHANTMENT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class LightmineField extends Card
{
	public static final class DeathInNumbers extends EventTriggeredAbility
	{
		public DeathInNumbers(GameState state)
		{
			super(state, "Whenever one or more creatures attack, Lightmine Field deals damage to each of those creatures equal to the number of attacking creatures.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ATTACKERS);
			pattern.withResult(SetPattern.EVERYTHING);
			this.addPattern(pattern);

			SetGenerator attackers = EventResult.instance(TriggerEvent.instance(This.instance()));

			this.addEffect(permanentDealDamage(Count.instance(attackers), attackers, "Lightmine Field deals damage to each of those creatures equal to the number of attacking creatures."));
		}
	}

	public LightmineField(GameState state)
	{
		super(state);

		// Whenever one or more creatures attack, Lightmine Field deals damage
		// to each of those creatures equal to the number of attacking
		// creatures.
		this.addAbility(new DeathInNumbers(state));
	}
}
