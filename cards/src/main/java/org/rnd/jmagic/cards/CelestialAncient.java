package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Celestial Ancient")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Dissension.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class CelestialAncient extends Card
{

	public static final class EnchantmentPump extends EventTriggeredAbility
	{
		public EnchantmentPump(GameState state)
		{
			super(state, "Whenever you cast an enchantment spell, put a +1/+1 counter on each creature you control.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(Intersect.instance(HasType.instance(Type.ENCHANTMENT), Spells.instance()));
			this.addPattern(pattern);

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, CREATURES_YOU_CONTROL, "Put a +1/+1 counter on each creature you control."));
		}
	}

	public CelestialAncient(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast an enchantment spell, put a +1/+1 counter on each
		// creature you control.
		this.addAbility(new EnchantmentPump(state));
	}
}
