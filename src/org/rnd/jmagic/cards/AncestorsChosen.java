package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ancestor's Chosen")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("5WW")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class AncestorsChosen extends Card
{
	public static final class GainYard extends EventTriggeredAbility
	{
		public GainYard(GameState state)
		{
			super(state, "When Ancestor's Chosen enters the battlefield, you gain 1 life for each card in your graveyard.");

			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator lifeAmount = Count.instance(InZone.instance(GraveyardOf.instance(You.instance())));
			this.addEffect(gainLife(You.instance(), lifeAmount, "You gain 1 life for each card in your graveyard."));
		}
	}

	public AncestorsChosen(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new GainYard(state));
	}
}
