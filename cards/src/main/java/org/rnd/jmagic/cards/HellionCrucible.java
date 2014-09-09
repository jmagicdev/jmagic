package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hellion Crucible")
@Types({Type.LAND})
@ColorIdentity({Color.RED})
public final class HellionCrucible extends Card
{
	public static final class HellionCrucibleAbility1 extends ActivatedAbility
	{
		public HellionCrucibleAbility1(GameState state)
		{
			super(state, "(1)(R), (T): Put a pressure counter on Hellion Crucible.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.costsTap = true;

			this.addEffect(putCounters(1, Counter.CounterType.PRESSURE, ABILITY_SOURCE_OF_THIS, "Put a pressure counter on Hellion Crucible."));
		}
	}

	public static final class HellionCrucibleAbility2 extends ActivatedAbility
	{
		public HellionCrucibleAbility2(GameState state)
		{
			super(state, "(1)(R), (T), Remove two pressure counters from Hellion Crucible and sacrifice it: Put a 4/4 red Hellion creature token with haste onto the battlefield.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.costsTap = true;
			// Remove two pressure counters from Hellion Crucible and sacrifice
			// it
			this.addCost(removeCounters(2, Counter.CounterType.PRESSURE, ABILITY_SOURCE_OF_THIS, "Remove two pressure counters from Hellion Crucible"));
			this.addCost(sacrificeSpecificPermanents(You.instance(), ABILITY_SOURCE_OF_THIS, "Sacrifice it"));

			CreateTokensFactory factory = new CreateTokensFactory(1, 4, 4, "Put a 4/4 red Hellion creature token with haste onto the battlefield.");
			factory.setColors(Color.RED);
			factory.setSubTypes(SubType.HELLION);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public HellionCrucible(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1)(R), (T): Put a pressure counter on Hellion Crucible.
		this.addAbility(new HellionCrucibleAbility1(state));

		// (1)(R), (T), Remove two pressure counters from Hellion Crucible and
		// sacrifice it: Put a 4/4 red Hellion creature token with haste onto
		// the battlefield. (It can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new HellionCrucibleAbility2(state));
	}
}
