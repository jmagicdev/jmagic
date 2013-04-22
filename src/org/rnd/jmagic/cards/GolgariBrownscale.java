package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Golgari Brownscale")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GolgariBrownscale extends Card
{
	public static final class GainLife extends EventTriggeredAbility
	{
		public GainLife(GameState state)
		{
			super(state, "When Golgari Brownscale is put into your hand from your graveyard, you gain 2 life.");
			this.triggersFromHand();

			SetGenerator owner = OwnerOf.instance(ABILITY_SOURCE_OF_THIS);

			this.addPattern(new SimpleZoneChangePattern(GraveyardOf.instance(owner), HandOf.instance(owner), ABILITY_SOURCE_OF_THIS, false));

			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public GolgariBrownscale(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new GainLife(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 2));
	}
}
