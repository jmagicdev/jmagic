package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Hellrider")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Hellrider extends Card
{
	public static final class HellriderAbility1 extends EventTriggeredAbility
	{
		public HellriderAbility1(GameState state)
		{
			super(state, "Whenever a creature you control attacks, Hellrider deals 1 damage to defending player.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			this.addPattern(pattern);

			SetGenerator attacker = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			this.addEffect(permanentDealDamage(1, DefendingPlayer.instance(attacker), "Hellrider deals 1 damage to defending player."));
		}
	}

	public Hellrider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever a creature you control attacks, Hellrider deals 1 damage to
		// defending player.
		this.addAbility(new HellriderAbility1(state));
	}
}
