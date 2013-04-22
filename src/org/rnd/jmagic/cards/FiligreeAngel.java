package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Filigree Angel")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.ANGEL})
@ManaCost("5WWU")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class FiligreeAngel extends Card
{
	public static final class GainLife extends EventTriggeredAbility
	{
		public GainLife(GameState state)
		{
			super(state, "When Filigree Angel enters the battlefield, you gain 3 life for each artifact you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator artifactsYouControl = Intersect.instance(HasType.instance(Type.ARTIFACT), youControl);
			SetGenerator amount = Multiply.instance(numberGenerator(3), Count.instance(artifactsYouControl));

			this.addEffect(gainLife(You.instance(), amount, "You gain life 3 life for each artifact you control."));
		}
	}

	public FiligreeAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new GainLife(state));
	}
}
