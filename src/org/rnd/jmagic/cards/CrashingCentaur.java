package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crashing Centaur")
@Types({Type.CREATURE})
@SubTypes({SubType.CENTAUR})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CrashingCentaur extends Card
{
	public static final class PitchTrample extends ActivatedAbility
	{
		public PitchTrample(GameState state)
		{
			super(state, "(G), Discard a card: Crashing Centaur gains trample until end of turn.");

			this.setManaCost(new ManaPool("G"));

			this.addCost(discardCards(You.instance(), 1, "Discard a card"));

			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Trample.class, "Crashing Centaur gains trample until end of turn."));
		}
	}

	public static final class ThresholdBigShroudy extends StaticAbility
	{
		public ThresholdBigShroudy(GameState state)
		{
			super(state, "As long as seven or more cards are in your graveyard, Crashing Centaur gets +2/+2 and has shroud.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Shroud.class));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}
	}

	public CrashingCentaur(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		this.addAbility(new PitchTrample(state));

		this.addAbility(new ThresholdBigShroudy(state));
	}
}
