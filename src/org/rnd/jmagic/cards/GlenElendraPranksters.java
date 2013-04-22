package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Glen Elendra Pranksters")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE, SubType.WIZARD})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class GlenElendraPranksters extends Card
{
	public static final class BounceTrickery extends org.rnd.jmagic.abilityTemplates.WhenYouCastASpellDuringOpponentsTurn
	{
		public BounceTrickery(GameState state)
		{
			super(state, "you may return target creature you control to its owner's hand.");
			Target target = this.addTarget(CREATURES_YOU_CONTROL, "target creature you control");

			EventFactory bounce = bounce(targetedBy(target), "Return target creature you control to its owner's hand.");
			this.addEffect(youMay(bounce, "You may return target creature you control to its owner's hand."));
		}
	}

	public GlenElendraPranksters(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast a spell during an opponent's turn, you may return
		// target creature you control to its owner's hand.
		this.addAbility(new BounceTrickery(state));
	}
}
