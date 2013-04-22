package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shattered Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class ShatteredAngel extends Card
{
	public static final class ShatteredAngelAbility1 extends EventTriggeredAbility
	{
		public ShatteredAngelAbility1(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under an opponent's control, you may gain 3 life.");
			this.addPattern(landfall());

			this.addEffect(youMay(gainLife(You.instance(), 3, "Gain 3 life."), "You may gain 3 life."));
		}
	}

	public ShatteredAngel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever a land enters the battlefield under an opponent's control,
		// you may gain 3 life.
		this.addAbility(new ShatteredAngelAbility1(state));
	}
}
